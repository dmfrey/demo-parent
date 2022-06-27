package com.vmware.tanzulabs.app.application.out;

import com.vmware.tanzulabs.app.domain.Person;

import java.util.List;

public interface FindAllPersonsPort {

    List<Person> findAll();

}
