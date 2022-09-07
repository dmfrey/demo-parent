package com.vmware.tanzulabs.app.application;

import com.vmware.tanzulabs.app.application.in.OrdersByCustomerIdUseCase;
import com.vmware.tanzulabs.app.application.out.FindOrdersByCustomerIdPort;
import com.vmware.tanzulabs.app.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrdersByCustomerIdServiceTests {

    OrdersByCustomerIdService subject;

    FindOrdersByCustomerIdPort mockFindOrdersByCustomerIdPort = mock( FindOrdersByCustomerIdPort.class );

    private final UUID fakeOrderId = UUID.randomUUID();
    private final String fakeCustomerId = "1";

    @BeforeEach
    void setup() {


        this.subject = new OrdersByCustomerIdService( this.mockFindOrdersByCustomerIdPort );

    }

    @Test
    void testUseCase() {

        when( this.mockFindOrdersByCustomerIdPort.findByCustomerId( fakeCustomerId ) ).thenReturn( List.of( new Order( fakeOrderId, fakeCustomerId ) ) );
        var actual = this.subject.execute( new OrdersByCustomerIdUseCase.OrdersByCustomerIdCommand( fakeCustomerId ) );

        var expected = new Order( fakeOrderId, fakeCustomerId );
        assertThat( actual ).contains( expected );

        verify( this.mockFindOrdersByCustomerIdPort ).findByCustomerId( fakeCustomerId );
        verifyNoMoreInteractions( this.mockFindOrdersByCustomerIdPort );

    }

}
