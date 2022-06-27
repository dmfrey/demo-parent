package com.vmware.tanzulabs.app.application.out;

import com.vmware.tanzulabs.app.domain.Order;

import java.util.List;
import java.util.Optional;

public interface LookupOrdersByCustomerIdPort {

    Optional<List<Order>> lookup( String customerId );

}
