package com.vmware.tanzulabs.app.adapter.out.resource;

import com.vmware.tanzulabs.app.domain.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerExtension;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

import java.util.*;

import static com.vmware.tanzulabs.app.adapter.SpringCloudContractHelper.repoRoot;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = {
                OrderResourceConfiguration.class,
                OrderResourceAdapter.class
        },
        properties = {
                "webservice.order-resource-url=http://localhost:8099"
        }
)
public class OrderResourceAdapterTests {

    static Map<String, String> contractProperties() {
        Map<String, String> map = new HashMap<>();
        map.put("stubs.find-producer", "true");
        return map;
    }

    @RegisterExtension
    static StubRunnerExtension stubRunnerExtension = new StubRunnerExtension()
            .downloadStub("com.vmware.tanzu-labs", "order-app", "0.0.1-SNAPSHOT" ).withPort( 8099 )
            .repoRoot( repoRoot() )
            .stubsMode( StubRunnerProperties.StubsMode.REMOTE )
            .withMappingsOutputFolder( "target/outputmappings" )
            .withProperties( contractProperties() );

    @Autowired
    OrderResourceAdapter subject;

    private final String fakeCustomerId = "1";
    private final String fakeCustomerIdNotFound = "2";
    private final UUID fakeOrderId = UUID.fromString( "f803c8fc-38ee-4949-8829-2c03d364d3ac" );

    @Test
    void when_customerId_one_verify_orders() {

        var actual = this.subject.lookup( fakeCustomerId );

        var expected = Optional.of( List.of( new Order( fakeOrderId, fakeCustomerId ) ) );

        assertThat( actual ).isEqualTo( expected );

    }

    @Test
    void when_customerId_two_verify_no_orders() {

        var actual = this.subject.lookup( fakeCustomerIdNotFound );

        var expected = Optional.of( Collections.emptyList() );

        assertThat( actual ).isEqualTo( expected );

    }

}
