package com.vmware.tanzulabs.app.adapter.out.persistence;

import com.vmware.tanzulabs.app.application.out.DeleteAllOrdersPort;
import com.vmware.tanzulabs.app.application.out.FindOrdersByCustomerIdPort;
import com.vmware.tanzulabs.app.application.out.SaveOrderPort;
import com.vmware.tanzulabs.app.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
@Transactional
class OrderPersistenceAdapter implements FindOrdersByCustomerIdPort, SaveOrderPort, DeleteAllOrdersPort {

    private static final Logger log = LoggerFactory.getLogger( OrderPersistenceAdapter.class );

    private final OrderRepository orderRepository;

    OrderPersistenceAdapter( final OrderRepository orderRepository ) {

        this.orderRepository = orderRepository;

    }

    @Override
    @Transactional( readOnly = true )
    public List<Order> findByCustomerId( String customerId ) {

//        customerId = customerId.replace( "@", "\\@" );

        return this.orderRepository.findByCustomerId( customerId ).stream()
                .map( entity -> new Order( entity.getId(), entity.getCustomerId() ) )
                .collect( toList() );
    }

    @Override
    public void save( final Order order ) {

        var entity = new OrderEntity();
        entity.setCustomerId( order.customerId() );

        var saved = this.orderRepository.save( entity );
        log.debug( "save : orderEntity saved [{}]", saved );

    }

    @Override
    public void deleteAll() {

        this.orderRepository.deleteAll();

    }

}
