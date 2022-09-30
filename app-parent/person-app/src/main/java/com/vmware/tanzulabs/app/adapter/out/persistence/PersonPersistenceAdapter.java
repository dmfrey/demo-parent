package com.vmware.tanzulabs.app.adapter.out.persistence;

import com.vmware.tanzulabs.app.annotations.persistence.PersistenceAdapter;
import com.vmware.tanzulabs.app.application.out.DeleteAllPersonsPort;
import com.vmware.tanzulabs.app.application.out.FindAllPersonsPort;
import com.vmware.tanzulabs.app.application.out.SavePersonPort;
import com.vmware.tanzulabs.app.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@PersistenceAdapter
@Transactional
class PersonPersistenceAdapter implements FindAllPersonsPort, SavePersonPort, DeleteAllPersonsPort {

    private static final Logger log = LoggerFactory.getLogger( PersonPersistenceAdapter.class );
    private final PersonRepository personRepository;

    PersonPersistenceAdapter( final PersonRepository personRepository ) {

        this.personRepository = personRepository;

    }


    @Override
    @Transactional( readOnly = true )
    public List<Person> findAll() {

        return StreamSupport.stream( this.personRepository.findAll().spliterator(), false )
                .map( entity -> new Person( entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getCustomerId(), Optional.empty() ) )
                .toList();
    }

    @Override
    public UUID save( final Person person ) {

        var entity = new PersonEntity();
        entity.setFirstName( person.firstName() );
        entity.setLastName( person.lastName() );
        entity.setCustomerId( person.customerId() );

        var saved = this.personRepository.save( entity );
        log.debug( "save : saved person entity [{}]", saved );

        return saved.getId();
    }

    @Override
    public void deleteAll() {

        this.personRepository.deleteAll();

    }

}
