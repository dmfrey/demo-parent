package com.vmware.tanzulabs.app.adapter.in.endpoint;

import com.vmware.tanzulabs.app.application.in.OrdersByCustomerIdUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
class OrdersByCustomerIdEndpoint {

    private final OrdersByCustomerIdUseCase useCase;

    OrdersByCustomerIdEndpoint( final OrdersByCustomerIdUseCase useCase ) {

        this.useCase = useCase;

    }

    @GetMapping( "/orders/{customerId}" )
    List<OrderResponse> ordersByCustomerId( @PathVariable String customerId ) {

        return this.useCase.execute( new OrdersByCustomerIdUseCase.OrdersByCustomerIdCommand( customerId ) ).stream()
                .map( order -> new OrderResponse( order.orderId(), order.customerId() ) )
                .collect( toList() );
    }

}
