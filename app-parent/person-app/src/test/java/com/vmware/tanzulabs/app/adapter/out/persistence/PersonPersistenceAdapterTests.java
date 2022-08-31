package com.vmware.tanzulabs.app.adapter.out.persistence;

import com.vmware.tanzulabs.app.domain.Person;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration( classes = { PersonPersistenceAdapter.class } )
@TestPropertySource( properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
public class PersonPersistenceAdapterTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EntityManager entityManager;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonPersistenceAdapter subject;

    private static final UUID fakePersonId = UUID.fromString( "4949ca4c-f9fa-4094-9b22-2c2b4761fcd2" );
    private static final String fakeFirstName = "test";
    private static final String fakeLastName = "user";
    private static final String fakeCustomerId = "1";

    @Test
    void testInjectedComponentsAreNotNull() {

        assertThat( this.dataSource ).isNotNull();
        assertThat( this.jdbcTemplate ).isNotNull();
        assertThat( this.entityManager ).isNotNull();
        assertThat( this.personRepository ).isNotNull();
        assertThat( this.subject ).isNotNull();

    }

    @Test
    @Sql( "createPerson.sql" )
    void testFindAll() {

        var actual = this.subject.findAll();

        var expected = new Person( fakePersonId, fakeFirstName, fakeLastName, fakeCustomerId, Optional.empty() );

        assertThat( actual ).contains( expected );

    }

    @Test
    void testFindAllEmpty() {

        var actual = this.subject.findAll();

        assertThat( actual ).isEmpty();

    }

    @Test
    void testSavePerson() {

        var fakePerson = new Person( null, fakeFirstName, fakeLastName, fakeCustomerId, Optional.empty() );
        UUID created = this.subject.save( fakePerson );

        var actual = this.subject.findAll();

        var expected = new Person( created, fakeFirstName, fakeLastName, fakeCustomerId, Optional.empty() );

        assertThat( actual ).contains( expected );

    }

    @Test
    @Sql( "createPerson.sql" )
    void testDeleteAll() {

        var populated = this.subject.findAll();
        assertThat( populated ).hasSize( 1 );

        this.subject.deleteAll();
        var expected = this.subject.findAll();
        assertThat( expected ).isEmpty();

    }

}
