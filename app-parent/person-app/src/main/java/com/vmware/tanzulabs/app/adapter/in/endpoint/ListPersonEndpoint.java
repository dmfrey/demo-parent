package com.vmware.tanzulabs.app.adapter.in.endpoint;

import com.vmware.tanzulabs.app.application.in.ListPersonUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
class ListPersonEndpoint {

    private final ListPersonUseCase listPersonUseCase;

    ListPersonEndpoint( final ListPersonUseCase listPersonUseCase ) {

        this.listPersonUseCase = listPersonUseCase;

    }

    @GetMapping( "/persons" )
    List<PersonResponse> listPersons() {

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
                .collect( toList() );
    }

}
