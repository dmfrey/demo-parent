package com.vmware.tanzulabs.app.adapter.out.persistence;

import com.vmware.tanzulabs.app.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration( classes = { OrderPersistenceAdapter.class } )
@TestPropertySource( properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
class OrderPersistenceAdapterTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderPersistenceAdapter subject;

    private static final UUID fakeOrderId = UUID.fromString( "4949ca4c-f9fa-4094-9b22-2c2b4761fcd2" );
    private static final String fakeCustomerId = "1";

    @Test
    void testInjectedComponentsAreNotNull() {

        assertThat( this.dataSource ).isNotNull();
        assertThat( this.jdbcTemplate ).isNotNull();
        assertThat( this.entityManager ).isNotNull();
        assertThat( this.orderRepository ).isNotNull();
        assertThat( this.subject ).isNotNull();

    }

    @Test
    @Sql( "createOrder.sql" )
    void testFindByCustomerId() {

        var actual = this.subject.findByCustomerId( fakeCustomerId );

        var expected = new Order( fakeOrderId, fakeCustomerId );

        assertThat( actual ).contains( expected );

    }

    @Test
    void testFindByCustomerIdNotFound() {

        var actual = this.subject.findByCustomerId( fakeCustomerId );

        assertThat( actual ).isEmpty();

    }

    @Test
    void testSaveOrder() {

        var fakeOrder = new Order( null, fakeCustomerId );
        UUID created = this.subject.save( fakeOrder );

        var actual = this.subject.findByCustomerId( fakeCustomerId );

        var expected = new Order( created, fakeCustomerId );

        assertThat( actual ).contains( expected );

    }

    @Test
    @Sql( "createOrder.sql" )
    void testDeleteAll() {

        var populated = this.subject.findByCustomerId( fakeCustomerId );
        assertThat( populated ).hasSize( 1 );

        this.subject.deleteAll();
        var expected = this.subject.findByCustomerId( fakeCustomerId );
        assertThat( expected ).isEmpty();

    }

}
