package com.vmware.tanzulabs.app.adapter.in.endpoint;

import com.vmware.tanzulabs.app.annotations.endpoint.EndpointAdapter;
import com.vmware.tanzulabs.app.application.in.OrdersByCustomerIdUseCase;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@EndpointAdapter
@OpenAPIDefinition(
        info = @Info(
                title = "Orders Endpoint",
                description = "Gets all orders for a customer"
        )
)
class OrdersByCustomerIdEndpoint {

    private final OrdersByCustomerIdUseCase useCase;

    OrdersByCustomerIdEndpoint( final OrdersByCustomerIdUseCase useCase ) {

        this.useCase = useCase;

    }

    @GetMapping( "/orders/{customerId}" )
    @CrossOrigin
    List<OrderResponse> ordersByCustomerId( @PathVariable( "customerId" ) String customerId ) {

        return this.useCase.execute( new OrdersByCustomerIdUseCase.OrdersByCustomerIdCommand( customerId ) ).stream()
                .map( order -> new OrderResponse( order.orderId(), order.customerId() ) )
                .toList();
    }

}
