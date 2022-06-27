package com.vmware.tanzulabs.app.adapter.out.persistence;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Table( name = "person" )
class PersonEntity {

    @Id
    @Column( name = "id" )
    @GeneratedValue( generator = "system-uuid" )
    @GenericGenerator( name = "system-uuid", strategy = "uuid2" )
    private UUID id;

    @Column( name = "first_name", length = 50, nullable = false )
    @NotEmpty
    private String firstName;

    @Column( name = "last_name", length = 50, nullable = false )
    @NotEmpty
    private String lastName;

    @Column( name = "customer_id", length = 250, nullable = false )
    @NotEmpty
    private String customerId;

    public UUID getId() {

        return id;
    }

    public void setId( UUID id ) {

        this.id = id;

    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName( String firstName ) {

        this.firstName = firstName;

    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName( String lastName ) {

        this.lastName = lastName;

    }

    public String getCustomerId() {

        return customerId;
    }

    public void setCustomerId( String customerId ) {

        this.customerId = customerId;

    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }

}
