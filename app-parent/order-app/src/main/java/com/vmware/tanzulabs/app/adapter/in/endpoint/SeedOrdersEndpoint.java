package com.vmware.tanzulabs.app.adapter.in.endpoint;

import com.vmware.tanzulabs.app.application.in.SeedOrdersUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
class SeedOrdersEndpoint {

    private final SeedOrdersUseCase seedOrdersUseCase;

    SeedOrdersEndpoint( final SeedOrdersUseCase seedOrdersUseCase ) {

        this.seedOrdersUseCase = seedOrdersUseCase;

    }

    @PostMapping( "/seed" )
    @ResponseStatus( ACCEPTED )
    void seed() {

        this.seedOrdersUseCase.execute();

    }

}
