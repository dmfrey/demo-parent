package com.vmware.tanzulabs.app.service;

import com.vmware.tanzulabs.app.persistence.PersonPersistenceAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ListPersonService implements ListPersonUseCase {

    private final PersonPersistenceAdapter personPersistenceAdapter;

    ListPersonService( final PersonPersistenceAdapter personPersistenceAdapter ) {

        this.personPersistenceAdapter = personPersistenceAdapter;

    }

    @Override
    public List<Person> listAll() {

        return this.personPersistenceAdapter.findAll();
    }

}
