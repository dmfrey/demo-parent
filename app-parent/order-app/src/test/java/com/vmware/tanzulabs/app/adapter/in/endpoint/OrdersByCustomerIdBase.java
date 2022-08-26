package com.vmware.tanzulabs.app.adapter.in.endpoint;

import com.vmware.tanzulabs.app.application.in.OrdersByCustomerIdUseCase;
import com.vmware.tanzulabs.app.domain.Order;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrdersByCustomerIdBase {

    OrdersByCustomerIdUseCase mockOrdersByCustomerIdUseCase = mock( OrdersByCustomerIdUseCase.class );

    private final String fakeCustomerId = "1";
    private final String fakeCustomerIdNotFound = "2";
    private final UUID fakeOrderId = UUID.fromString( "f803c8fc-38ee-4949-8829-2c03d364d3ac" );

    @BeforeEach
    void setup() {

        when( this.mockOrdersByCustomerIdUseCase.execute( new OrdersByCustomerIdUseCase.OrdersByCustomerIdCommand( fakeCustomerId ) ) )
                .thenReturn( List.of( new Order( fakeOrderId, fakeCustomerId ) ) );

        when( this.mockOrdersByCustomerIdUseCase.execute( new OrdersByCustomerIdUseCase.OrdersByCustomerIdCommand( fakeCustomerIdNotFound ) ) )
                .thenReturn( Collections.emptyList() );

        RestAssuredMockMvc.standaloneSetup( new OrdersByCustomerIdEndpoint( this.mockOrdersByCustomerIdUseCase ) );

    }

}
