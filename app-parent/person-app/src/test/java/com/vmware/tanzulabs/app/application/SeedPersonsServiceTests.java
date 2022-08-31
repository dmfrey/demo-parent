package com.vmware.tanzulabs.app.application;

import com.vmware.tanzulabs.app.application.out.DeleteAllPersonsPort;
import com.vmware.tanzulabs.app.application.out.SavePersonPort;
import com.vmware.tanzulabs.app.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class SeedPersonsServiceTests {

    SeedPersonsService subject;

    SavePersonPort mockSavePersonPort = mock( SavePersonPort.class );
    DeleteAllPersonsPort mockDeleteAllPersonsPort = mock( DeleteAllPersonsPort.class );

    @BeforeEach
    void setup() {

        this.subject = new SeedPersonsService( this.mockSavePersonPort, this.mockDeleteAllPersonsPort );

    }

    @Test
    void testUseCase() {

        this.subject.execute();

        verify( this.mockDeleteAllPersonsPort ).deleteAll();
        verify( this.mockSavePersonPort, times( 5 ) ).save( any( Person.class ) );
        verifyNoMoreInteractions( this.mockDeleteAllPersonsPort, this.mockSavePersonPort );

    }

}
