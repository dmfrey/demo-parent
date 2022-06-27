package com.vmware.tanzulabs.app.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

interface OrderRepository extends CrudRepository<OrderEntity, UUID> {

//    @Query( value = "SELECT o FROM order o WHERE o.customer_id = :customerId", nativeQuery = true )
    List<OrderEntity> findByCustomerId( String customerId );

}
