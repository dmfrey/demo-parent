package com.vmware.tanzulabs.app.persistence;

import com.vmware.tanzulabs.app.service.Person;

import java.util.List;

public interface PersonPersistenceAdapter {

    List<Person> findAll();

}
