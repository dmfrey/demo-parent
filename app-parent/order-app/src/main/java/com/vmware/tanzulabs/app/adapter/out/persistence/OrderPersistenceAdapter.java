package com.vmware.tanzulabs.app.adapter.out.persistence;

import com.vmware.tanzulabs.app.annotations.persistence.PersistenceAdapter;
import com.vmware.tanzulabs.app.application.out.DeleteAllOrdersPort;
import com.vmware.tanzulabs.app.application.out.FindOrdersByCustomerIdPort;
import com.vmware.tanzulabs.app.application.out.SaveOrderPort;
import com.vmware.tanzulabs.app.domain.Order;
import com.vmware.tanzulabs.demo.events.OrderPlaced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@Transactional
class OrderPersistenceAdapter implements FindOrdersByCustomerIdPort, SaveOrderPort, DeleteAllOrdersPort {

    private static final Logger log = LoggerFactory.getLogger( OrderPersistenceAdapter.class );

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    OrderPersistenceAdapter( final OrderRepository orderRepository, final ApplicationEventPublisher eventPublisher ) {

        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;

    }

    @Override
    @Transactional( readOnly = true )
    public List<Order> findByCustomerId( String customerId ) {

        return this.orderRepository.findByCustomerId( customerId ).stream()
                .map( entity -> new Order( entity.getId(), entity.getCustomerId() ) )
                .toList();
    }

    @Override
    public UUID save( final Order order ) {

        var entity = new OrderEntity();
        entity.setCustomerId( order.customerId() );

        var saved = this.orderRepository.save( entity );
        log.debug( "save : orderEntity saved [{}]", saved );

        var event = new OrderPlaced();
        event.setOrderId( saved.getId() );
        event.setCustomerId( saved.getCustomerId() );
        event.setOrderCreated( LocalDateTime.now().toInstant( ZoneOffset.UTC ) );
        this.eventPublisher.publishEvent( event );

        return saved.getId();
    }

    @Override
    public void deleteAll() {

        this.orderRepository.deleteAll();

    }

}
