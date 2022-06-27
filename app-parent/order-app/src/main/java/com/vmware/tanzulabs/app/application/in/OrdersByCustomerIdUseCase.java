package com.vmware.tanzulabs.app.application.in;

import com.vmware.tanzulabs.app.domain.Order;

import java.util.List;

public interface OrdersByCustomerIdUseCase {

    List<Order> execute( OrdersByCustomerIdCommand command );

    record OrdersByCustomerIdCommand( String customerId ) { }

}
