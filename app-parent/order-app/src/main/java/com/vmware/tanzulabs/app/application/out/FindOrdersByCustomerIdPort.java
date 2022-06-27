package com.vmware.tanzulabs.app.application.out;

import com.vmware.tanzulabs.app.domain.Order;

import java.util.List;

public interface FindOrdersByCustomerIdPort {

    List<Order> findByCustomerId(String customerId );

}
