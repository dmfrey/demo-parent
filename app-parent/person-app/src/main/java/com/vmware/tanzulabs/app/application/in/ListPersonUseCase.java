package com.vmware.tanzulabs.app.application.in;

import com.vmware.tanzulabs.app.domain.Person;

import java.util.List;

public interface ListPersonUseCase {

    List<Person> execute();

}
