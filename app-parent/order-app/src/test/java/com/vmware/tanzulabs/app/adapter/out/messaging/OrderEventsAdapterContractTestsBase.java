package com.vmware.tanzulabs.app.adapter.out.messaging;

import com.vmware.tanzulabs.app.Application;
import com.vmware.tanzulabs.demo.events.OrderPlaced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ApplicationEventPublisher;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(
        classes = Application.class,
        webEnvironment = NONE,
        properties = {
                "management.tracing.enabled=false",
                "jdbc.datasource-proxy.enabled=false",
                "spring.cloud.stream.output-bindings=orderplaced"
        }
)
@AutoConfigureMessageVerifier
@ImportAutoConfiguration( TestChannelBinderConfiguration.class )
public abstract class OrderEventsAdapterContractTestsBase {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    UUID fakeOrderId = UUID.fromString( "505c50a7-0c26-4582-b9ae-bc1c0f65fec1" );
    String fakeCustomerId = "fakeCustomer@example.com";
    Instant fakeTimestamp = Instant.parse( "2023-01-30T15:11:00.000Z" );

    public void sendOrderPlacedEvent() throws IOException {

        var fakeEvent = new OrderPlaced();
        fakeEvent.setOrderId( fakeOrderId );
        fakeEvent.setCustomerId( fakeCustomerId );
        fakeEvent.setOrderCreated( fakeTimestamp );

//        var writer = new SpecificDatumWriter<>( OrderPlaced.class );
//        var stream = new ByteArrayOutputStream();
//        var data = new byte[0];
//        Encoder jsonEncoder = null;
//        try {
//            jsonEncoder = EncoderFactory.get().binaryEncoder( stream, null );
//            writer.write( fakeEvent, jsonEncoder );
//            jsonEncoder.flush();
//            data = stream.toByteArray();
//            Files.write( new File( "orderplaced-event.bin").toPath(), data );
//        } catch( IOException e ) {
//
//        }

        this.applicationEventPublisher.publishEvent( fakeEvent );

    }

}
