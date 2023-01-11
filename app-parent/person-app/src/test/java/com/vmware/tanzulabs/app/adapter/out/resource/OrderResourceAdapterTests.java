package com.vmware.tanzulabs.app.adapter.out.resource;

import com.vmware.tanzulabs.app.domain.Order;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigurationProperties;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerExtension;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.*;

import static com.vmware.tanzulabs.app.adapter.SpringCloudContractHelper.repoRoot;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

class OrderResourceAdapterTests {

    static Resilience4JCircuitBreakerFactory circuitBreakerFactory = spy(
            new Resilience4JCircuitBreakerFactory(
                    CircuitBreakerRegistry.ofDefaults(),
                    TimeLimiterRegistry.ofDefaults(),
                    null,
                    new Resilience4JConfigurationProperties()
            ));

    static Map<String, String> contractProperties() {

        Map<String, String> map = new HashMap<>();
        map.put( "stubs.find-producer", "true" );

        return map;
    }

    @RegisterExtension
    static StubRunnerExtension stubRunnerExtension = new StubRunnerExtension()
            .downloadStub("com.vmware.tanzu-labs", "order-app" ).withPort( 8099 )
            .repoRoot( repoRoot() )
            .stubsMode( StubRunnerProperties.StubsMode.REMOTE )
            .withMappingsOutputFolder( "target/outputmappings" )
            .withProperties( contractProperties() );

    private final String fakeCustomerId = "1";
    private final String fakeCustomerIdNotFound = "2";
    private final UUID fakeOrderId = UUID.fromString( "f803c8fc-38ee-4949-8829-2c03d364d3ac" );

    @Test
    void when_customerId_one_verify_orders() {

        try( ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .web( WebApplicationType.NONE )
                .sources( TestConfiguration.class )
                .run(
                        "--webservice.order-resource-url=http://localhost:8099"
                )
        ) {

            var subject = context.getBean( OrderResourceAdapter.class );

            var actual = subject.lookup( fakeCustomerId );

            var expected = Optional.of( List.of( new Order( fakeOrderId, fakeCustomerId ) ) );

            assertThat( actual ).isEqualTo( expected );

        }

    }

    @Test
    void when_customerId_two_verify_no_orders() {

        try( ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .web( WebApplicationType.NONE )
                .sources( TestConfiguration.class )
                .run(
                        "--webservice.order-resource-url=http://localhost:8099"
                )
        ) {

            var subject = context.getBean( OrderResourceAdapter.class );

            var actual = subject.lookup( fakeCustomerIdNotFound );

            var expected = Optional.of( Collections.emptyList() );

            assertThat( actual ).isEqualTo( expected );

        }

    }

    @Test
    void when_order_app_down_verify_no_orders() {

        try( ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .web( WebApplicationType.NONE )
                .sources( TestConfiguration.class )
                .run(
                        "--webservice.order-resource-url=http://localhost:9999"
                )
        ) {

            var subject = context.getBean( OrderResourceAdapter.class );

            var actual = subject.lookup( fakeCustomerIdNotFound );

            var expected = Optional.of( Collections.emptyList() );

            assertThat( actual ).isEqualTo( expected );

        }

    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @Import({ OrderResourceConfiguration.class, OrderResourceAdapter.class })
    protected static class TestConfiguration {

        @Bean
        Resilience4JCircuitBreakerFactory circuitBreakerFactory() {

            return circuitBreakerFactory;
        }

    }

}
