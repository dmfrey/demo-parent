package com.vmware.tanzulabs.app.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface PersonRepository extends CrudRepository<PersonEntity, UUID> {

}
