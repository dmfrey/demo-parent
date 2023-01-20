package com.vmware.tanzulabs.app.adapter.in.endpoint;

import com.vmware.tanzulabs.app.annotations.endpoint.EndpointAdapter;
import com.vmware.tanzulabs.app.application.in.ListPersonUseCase;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EndpointAdapter
@OpenAPIDefinition(
        info = @Info(
                title = "Persons Endpoint",
                description = "Lists all persons"
        )
)
class ListPersonEndpoint {

    private static final Logger log = LoggerFactory.getLogger( ListPersonEndpoint.class );

    private final ListPersonUseCase listPersonUseCase;

    ListPersonEndpoint( final ListPersonUseCase listPersonUseCase ) {

        this.listPersonUseCase = listPersonUseCase;

    }

    @GetMapping( "/persons" )
    @CrossOrigin
    List<PersonResponse> listPersons() {
        log.info( "listPersons : enter" );

        return this.listPersonUseCase.execute().stream()
                .map( person -> {

                    List<OrderResponse> orders = new ArrayList<>();

                    if( person.orders().isPresent() ) {

                        orders =
                                person.orders()
                                        .get().stream()
                                        .map( order -> new OrderResponse( order.orderId(), order.customerId() ) )
                                        .toList();

                    }

                    return new PersonResponse( person.id(), person.firstName(), person.lastName(), person.customerId(), Optional.of( orders ) );
                } )
                .toList();
    }

}
