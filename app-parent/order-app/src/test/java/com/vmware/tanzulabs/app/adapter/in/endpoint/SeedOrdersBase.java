package com.vmware.tanzulabs.app.adapter.in.endpoint;

import com.vmware.tanzulabs.app.application.in.SeedOrdersUseCase;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;

public class SeedOrdersBase {

    SeedOrdersUseCase mockSeedOrdersUseCase = mock( SeedOrdersUseCase.class );

    @BeforeEach
    void setup() {

        RestAssuredMockMvc.standaloneSetup( new SeedOrdersEndpoint( this.mockSeedOrdersUseCase ) );

    }

}
