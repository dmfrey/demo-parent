package com.vmware.tanzulabs.app.adapter.out.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.vmware.tanzulabs.demo.events.OrderPlaced;

@Component
class OrderEventsAdapter {

    private static final Logger log = LoggerFactory.getLogger( OrderEventsAdapter.class );

    private final StreamBridge streamBridge;

    OrderEventsAdapter( final StreamBridge streamBridge ) {

        this.streamBridge = streamBridge;

    }

    @EventListener
    void sendOrderPlacedEvent( OrderPlaced event ) {

        this.streamBridge.send( "order-placed-out-0", event );
        log.info( "sendOrderPlacedEvent : order placed event [{}] sent!", event );

    }

}
