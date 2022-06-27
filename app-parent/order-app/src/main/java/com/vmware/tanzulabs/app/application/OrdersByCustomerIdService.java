package com.vmware.tanzulabs.app.application;

import com.vmware.tanzulabs.app.application.in.OrdersByCustomerIdUseCase;
import com.vmware.tanzulabs.app.application.out.FindOrdersByCustomerIdPort;
import com.vmware.tanzulabs.app.domain.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class OrdersByCustomerIdService implements OrdersByCustomerIdUseCase {

    private final FindOrdersByCustomerIdPort findOrdersByCustomerIdPort;

    OrdersByCustomerIdService( final FindOrdersByCustomerIdPort findOrdersByCustomerIdPort ) {

        this.findOrdersByCustomerIdPort = findOrdersByCustomerIdPort;

    }

    @Override
    public List<Order> execute( final OrdersByCustomerIdCommand command ) {

        return this.findOrdersByCustomerIdPort.findByCustomerId( command.customerId() );
    }

}
