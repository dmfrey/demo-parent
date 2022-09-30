package com.vmware.tanzulabs.app.adapter.in.endpoint;

import com.vmware.tanzulabs.app.annotations.endpoint.EndpointAdapter;
import com.vmware.tanzulabs.app.application.in.SeedPersonsUseCase;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;

@EndpointAdapter
@OpenAPIDefinition(
        info = @Info(
                title = "Seed Persons Endpoint",
                description = "Seeds persons db with test data"
        )
)
class SeedPersonsEndpoint {

    private final SeedPersonsUseCase seedPersonsUseCase;

    SeedPersonsEndpoint( final SeedPersonsUseCase seedPersonsUseCase ) {

        this.seedPersonsUseCase = seedPersonsUseCase;

    }

    @PostMapping( "/seed" )
    @ResponseStatus( ACCEPTED )
    @CrossOrigin
    void seed() {

        this.seedPersonsUseCase.execute();

    }

}
