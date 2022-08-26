package com.vmware.tanzulabs.app.adapter.in.endpoint;

import com.vmware.tanzulabs.app.application.in.SeedPersonsUseCase;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class SeedPersonsBase {

    SeedPersonsUseCase mockSeedPersonsUseCase = mock( SeedPersonsUseCase.class );

    @BeforeEach
    void setup() {

        RestAssuredMockMvc.standaloneSetup( new SeedPersonsEndpoint( this.mockSeedPersonsUseCase ) );

    }

}
