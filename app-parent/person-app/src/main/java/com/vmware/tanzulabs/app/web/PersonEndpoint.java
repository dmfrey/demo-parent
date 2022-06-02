package com.vmware.tanzulabs.app.web;

import com.vmware.tanzulabs.app.service.ListPersonUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class PersonEndpoint {

    private final ListPersonUseCase listPersonUseCase;

    PersonEndpoint( final ListPersonUseCase listPersonUseCase ) {

        this.listPersonUseCase = listPersonUseCase;

    }

    @GetMapping( "/persons" )
    List<PersonResponse> listPersons() {

        return this.listPersonUseCase.listAll().stream()
                .map( person -> new PersonResponse( person.id(), person.firstName(), person.lastName() ) )
                .collect( toList() );
    }

}
