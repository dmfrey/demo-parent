package com.vmware.tanzulabs.app.application;

import com.vmware.tanzulabs.app.application.out.DeleteAllOrdersPort;
import com.vmware.tanzulabs.app.application.out.SaveOrderPort;
import com.vmware.tanzulabs.app.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class SeedOrdersServiceTests {

    SeedOrdersService subject;

    SaveOrderPort mockSaveOrderPort = mock( SaveOrderPort.class );
    DeleteAllOrdersPort mockDeleteAllOrdersPort = mock( DeleteAllOrdersPort.class );

    @BeforeEach
    void setup() {

        this.subject = new SeedOrdersService( this.mockSaveOrderPort, this.mockDeleteAllOrdersPort );

    }

    @Test
    void testUseCase() {

        this.subject.execute();

        verify( this.mockDeleteAllOrdersPort ).deleteAll();
        verify( this.mockSaveOrderPort, times( 17 ) ).save( any( Order.class ) );
        verifyNoMoreInteractions( this.mockDeleteAllOrdersPort, this.mockSaveOrderPort );

    }

}
