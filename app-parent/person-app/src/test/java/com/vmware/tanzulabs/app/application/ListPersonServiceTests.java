package com.vmware.tanzulabs.app.application;

import com.vmware.tanzulabs.app.application.out.FindAllPersonsPort;
import com.vmware.tanzulabs.app.application.out.LookupOrdersByCustomerIdPort;
import com.vmware.tanzulabs.app.domain.Order;
import com.vmware.tanzulabs.app.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ListPersonServiceTests {

    ListPersonService subject;

    private FindAllPersonsPort mockFindAllPersonsPort;
    private LookupOrdersByCustomerIdPort mockLookupOrdersByCustomerIdPort;

    private final UUID fakeId = UUID.fromString( "f803c8fc-38ee-4949-8829-2c03d364d3ac" );
    private final String fakeFirstName = "firstName";
    private final String fakeLastName = "lastName";
    private final String fakeCustomerId = "1";

    private final UUID fakeOrderId = UUID.fromString( "f803c8fc-38ee-4949-8829-2c03d364d3ac" );

    @BeforeEach
    void setup() {

        this.mockFindAllPersonsPort = mock( FindAllPersonsPort.class );
        this.mockLookupOrdersByCustomerIdPort = mock( LookupOrdersByCustomerIdPort.class );

        this.subject = new ListPersonService( this.mockFindAllPersonsPort, this.mockLookupOrdersByCustomerIdPort );

    }

    @Test
    void testListPersons() {

        var fakePerson = new Person( fakeId, fakeFirstName, fakeLastName, fakeCustomerId, Optional.empty() );
        when( this.mockFindAllPersonsPort.findAll() ).thenReturn( List.of( fakePerson ) );

        var actual = this.subject.execute();

        var expected = List.of( new Person( fakeId, fakeFirstName, fakeLastName, fakeCustomerId, Optional.empty() ) );

        assertThat( actual ).isEqualTo( expected );
        verify( this.mockFindAllPersonsPort ).findAll();
        verify( this.mockLookupOrdersByCustomerIdPort ).lookup( fakeCustomerId );
        verifyNoMoreInteractions( this.mockFindAllPersonsPort, this.mockLookupOrdersByCustomerIdPort );

    }

    @Test
    void testListPersonsWithCallToOrdersService() {

        var fakeOrder = new Order( fakeOrderId, fakeCustomerId );
        var fakePerson = new Person( fakeId, fakeFirstName, fakeLastName, fakeCustomerId, Optional.of( List.of( fakeOrder ) ) );

        when( this.mockLookupOrdersByCustomerIdPort.lookup( fakeCustomerId ) ).thenReturn( Optional.of( fakePerson.orders().get() ) );
        when( this.mockFindAllPersonsPort.findAll() ).thenReturn( List.of( fakePerson ) );

        var actual = this.subject.execute();

        var expected = List.of( new Person( fakeId, fakeFirstName, fakeLastName, fakeCustomerId, Optional.of( List.of( new Order( fakeOrderId, fakeCustomerId ) ) ) ) );

        assertThat( actual ).isEqualTo( expected );
        verify( this.mockFindAllPersonsPort ).findAll();
        verify( this.mockLookupOrdersByCustomerIdPort ).lookup( fakeCustomerId );
        verifyNoMoreInteractions( this.mockFindAllPersonsPort, this.mockLookupOrdersByCustomerIdPort );

    }

}
