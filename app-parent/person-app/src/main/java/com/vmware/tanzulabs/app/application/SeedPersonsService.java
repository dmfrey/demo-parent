package com.vmware.tanzulabs.app.application;

import com.vmware.tanzulabs.app.annotations.usecase.UseCase;
import com.vmware.tanzulabs.app.application.in.SeedPersonsUseCase;
import com.vmware.tanzulabs.app.application.out.DeleteAllPersonsPort;
import com.vmware.tanzulabs.app.application.out.SavePersonPort;
import com.vmware.tanzulabs.app.domain.Person;

import java.util.List;
import java.util.Optional;

@UseCase
class SeedPersonsService implements SeedPersonsUseCase {

    private final SavePersonPort savePersonPort;
    private final DeleteAllPersonsPort deleteAllPersonsPort;

    SeedPersonsService(
            final SavePersonPort savePersonPort,
            final DeleteAllPersonsPort deleteAllPersonsPort
    ) {

        this.savePersonPort = savePersonPort;
        this.deleteAllPersonsPort = deleteAllPersonsPort;

    }

    @Override
    public void execute() {

        this.deleteAllPersonsPort.deleteAll();

        List.of(
                new Person( null, "Daniel", "Frey", "dfrey@vmware.com", Optional.empty() ),
                new Person( null, "Grant", "Allen", "gallen@vmware.com", Optional.empty() ),
                new Person( null, "Frank", "Tyler", "ftyler@vmware.com", Optional.empty() ),
                new Person( null, "Ian", "Bautista", "ibautista@vmware.com", Optional.empty() ),
                new Person( null, "Saurin", "Shah", "sshah@vmware.com", Optional.empty() )
        ).forEach( this.savePersonPort::save );

    }

}
