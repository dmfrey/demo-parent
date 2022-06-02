package com.vmware.tanzulabs.app.persistence;

import com.vmware.tanzulabs.app.service.Person;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Repository
@Transactional( readOnly = true )
class PersonPersistenceService implements PersonPersistenceAdapter {

    private final PersonRepository personRepository;

    PersonPersistenceService( final PersonRepository personRepository ) {

        this.personRepository = personRepository;

    }


    @Override
    public List<Person> findAll() {

        return StreamSupport.stream( this.personRepository.findAll().spliterator(), false )
                .map( entity -> new Person( entity.getId(), entity.getFirstName(), entity.getLastName()) )
                .collect( toList() );
    }

}
