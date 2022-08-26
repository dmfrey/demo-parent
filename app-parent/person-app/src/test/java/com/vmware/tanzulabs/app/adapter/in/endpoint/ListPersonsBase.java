package com.vmware.tanzulabs.app.adapter.in.endpoint;

import com.vmware.tanzulabs.app.application.in.ListPersonUseCase;
import com.vmware.tanzulabs.app.domain.Order;
import com.vmware.tanzulabs.app.domain.Person;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListPersonsBase {

    ListPersonUseCase mockListPersonUseCase = mock( ListPersonUseCase.class );

    private final UUID fakeId = UUID.fromString( "f803c8fc-38ee-4949-8829-2c03d364d3ac" );
    private final String fakeFirstName = "firstName";
    private final String fakeLastName = "lastName";
    private final String fakeCustomerId = "1";
    private final UUID fakeOrderId = UUID.fromString( "f803c8fc-38ee-4949-8829-2c03d364d3ac" );

    @BeforeEach
    void setup() {

        var person = new Person( fakeId, fakeFirstName, fakeLastName, fakeCustomerId, Optional.of( List.of( new Order( fakeOrderId, fakeCustomerId ) ) ) );
        when( this.mockListPersonUseCase.execute() ).thenReturn( List.of( person ) );

        RestAssuredMockMvc.standaloneSetup( new ListPersonEndpoint( this.mockListPersonUseCase ) );

    }

}
