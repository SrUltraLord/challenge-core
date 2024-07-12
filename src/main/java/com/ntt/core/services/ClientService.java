package com.ntt.core.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntt.core.dto.ClientDTO;
import com.ntt.core.exceptions.ClientNotFoundException;
import com.ntt.core.exceptions.InformationConsolidationException;
import com.ntt.core.messaging.AwaitableMessageSubscriber;
import com.ntt.core.messaging.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private static final String REQUEST_CHANNEL = "clients_channel";

    private final ObjectMapper objectMapper;
    private final MessagePublisher publisher;
    private final AwaitableMessageSubscriber subscriber;

    public ClientDTO findClientById(Long clientId) {
        var correlationId = UUID.randomUUID().toString(); // Delegate this id generation.

        var requestPayload = String.format("clients:%s:%s", correlationId, clientId);
        publisher.publish(REQUEST_CHANNEL, requestPayload);

        try {
            var responseChannel = "response:" + correlationId;
            subscriber.subscribe(responseChannel);
            var payload = subscriber.awaitForMessage();

            if (payload == null || payload.isBlank()) {
                throw new ClientNotFoundException("Could not find client with id " + clientId);
            }

            return objectMapper.readValue(payload, ClientDTO.class);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InformationConsolidationException("Could not retrieve client information.");
        } catch (JsonProcessingException e) {
            throw new InformationConsolidationException("Could not retrieve client information.");
        }
    }
}
