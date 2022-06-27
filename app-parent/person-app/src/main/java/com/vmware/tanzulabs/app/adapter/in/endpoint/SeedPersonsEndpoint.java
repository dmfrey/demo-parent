package com.vmware.tanzulabs.app.adapter.in.endpoint;

import com.vmware.tanzulabs.app.application.in.SeedPersonsUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
class SeedPersonsEndpoint {

    private final SeedPersonsUseCase seedPersonsUseCase;

    SeedPersonsEndpoint( final SeedPersonsUseCase seedPersonsUseCase ) {

        this.seedPersonsUseCase = seedPersonsUseCase;

    }

    @PostMapping( "/seed" )
    @ResponseStatus( ACCEPTED )
    void seed() {

        this.seedPersonsUseCase.execute();

    }

}
