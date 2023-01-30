package com.vmware.tanzulabs.app.adapter.out.messaging;

import com.vmware.tanzulabs.app.Application;
import com.vmware.tanzulabs.demo.events.OrderPlaced;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderEventsAdapterTests {

    UUID fakeOrderId = UUID.randomUUID();
    String fakeCustomerId = "fakeCustomer";
    Instant fakeTimestamp = Instant.now();

    @Test
    void test() throws IOException {

        try( ConfigurableApplicationContext context =
                     new SpringApplicationBuilder(
                             TestChannelBinderConfiguration
                                     .getCompleteConfiguration( Application.class ) )
                             .web( WebApplicationType.NONE )
                             .run(
                             "--management.tracing.enabled=false",
                                     "--jdbc.datasource-proxy.enabled=false",
                                     "--spring.cloud.stream.output-bindings=orderplaced" //, "--debug=true"
                     )
        ) {

            var fakeEvent = new OrderPlaced();
            fakeEvent.setOrderId( fakeOrderId );
            fakeEvent.setCustomerId( fakeCustomerId );
            fakeEvent.setOrderCreated( fakeTimestamp );

            context.publishEvent( fakeEvent );

            var expectedEvent = new OrderPlaced();
            expectedEvent.setOrderId( fakeOrderId );
            expectedEvent.setCustomerId( fakeCustomerId );
            expectedEvent.setOrderCreated( fakeTimestamp );

            var outputDestination = context.getBean( OutputDestination.class );
            var message = outputDestination.receive( 10_000L, "order-placed-topic.destination" );

            var decoder = DecoderFactory.get().binaryDecoder( message.getPayload(), null );
            var reader = new SpecificDatumReader<OrderPlaced>( OrderPlaced.getClassSchema() );
            var actual = reader.read(null , decoder );

            assertThat( actual.getOrderId() ).isEqualTo( expectedEvent.getOrderId() );
            assertThat( actual.getCustomerId().toString() ).isEqualTo( expectedEvent.getCustomerId() );
            assertThat( actual.getOrderCreated().toEpochMilli() ).isEqualTo( expectedEvent.getOrderCreated().toEpochMilli() );

        }

    }

}
