package com.vmware.tanzulabs.app.adapter.in.endpoint;

import com.vmware.tanzulabs.app.annotations.endpoint.EndpointAdapter;
import com.vmware.tanzulabs.app.application.in.SeedOrdersUseCase;
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
                title = "Seed Orders Endpoint",
                description = "Seeds orders db with test data"
        )
)
class SeedOrdersEndpoint {

    private final SeedOrdersUseCase seedOrdersUseCase;

    SeedOrdersEndpoint( final SeedOrdersUseCase seedOrdersUseCase ) {

        this.seedOrdersUseCase = seedOrdersUseCase;

    }

    @PostMapping( "/seed" )
    @ResponseStatus( ACCEPTED )
    @CrossOrigin
    void seed() {

        this.seedOrdersUseCase.execute();

    }

}
