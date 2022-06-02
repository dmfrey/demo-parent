package com.vmware.tanzulabs.app.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface PersonRepository extends CrudRepository<PersonEntity, UUID> {

}
