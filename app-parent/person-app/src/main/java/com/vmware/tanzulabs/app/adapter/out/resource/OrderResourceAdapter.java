package com.vmware.tanzulabs.app.adapter.out.resource;

import com.vmware.tanzulabs.app.application.out.LookupOrdersByCustomerIdPort;
import com.vmware.tanzulabs.app.domain.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Component
class OrderResourceAdapter implements LookupOrdersByCustomerIdPort {

    private final RestTemplate restTemplate;
    private final OrderResourceConfigurationProperties configurationProperties;

    OrderResourceAdapter(
            final RestTemplate restTemplate,
            final OrderResourceConfigurationProperties configurationProperties
    ) {

        this.restTemplate = restTemplate;
        this.configurationProperties = configurationProperties;

    }

    @Override
    public Optional<List<Order>> lookup( String customerId ) {

        ResponseEntity<OrderResource[]> resourceResponseEntity = this.restTemplate.getForEntity( configurationProperties.orderResourceUrl() + "/orders/{customerId}", OrderResource[].class, customerId );

        List<Order> orders = Stream.of( Objects.requireNonNull(resourceResponseEntity.getBody() ) )
                .map( orderResource -> new Order( orderResource.orderId(), orderResource.customerId() ) )
                .toList();

        return Optional.of( orders );

    }

}
