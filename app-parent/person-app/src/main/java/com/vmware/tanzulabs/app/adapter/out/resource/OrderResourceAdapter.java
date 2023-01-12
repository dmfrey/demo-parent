package com.vmware.tanzulabs.app.adapter.out.resource;

import com.vmware.tanzulabs.app.application.out.LookupOrdersByCustomerIdPort;
import com.vmware.tanzulabs.app.domain.Order;
import io.micrometer.observation.annotation.Observed;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
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
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final OrderResourceConfigurationProperties configurationProperties;

    OrderResourceAdapter(
            final RestTemplate restTemplate,
            final CircuitBreakerFactory circuitBreakerFactory,
            final OrderResourceConfigurationProperties configurationProperties
    ) {

        this.restTemplate = restTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.configurationProperties = configurationProperties;

    }

    @Override
    @Observed( name = "orders", contextualName = "orders-by-customer-id" )
    public Optional<List<Order>> lookup( String customerId ) {

        var resourceResponseEntity =
                this.circuitBreakerFactory.create( "order-app" )
                        .run(
                            () -> this.restTemplate.getForEntity( configurationProperties.orderResourceUrl() + "/orders/{customerId}", OrderResource[].class, customerId ),
                            throwable -> new ResponseEntity<>( new OrderResource[] {}, HttpStatus.GATEWAY_TIMEOUT )
                        );

        var orders = Stream.of( Objects.requireNonNull( resourceResponseEntity.getBody() ) )
                .map( orderResource -> new Order( orderResource.orderId(), orderResource.customerId() ) )
                .toList();

        return Optional.of( orders );

    }

}
