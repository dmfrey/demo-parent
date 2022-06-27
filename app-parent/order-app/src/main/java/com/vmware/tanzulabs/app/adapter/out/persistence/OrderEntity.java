package com.vmware.tanzulabs.app.adapter.out.persistence;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Table( name = "orders" )
class OrderEntity {

    @Id
    @Column( name = "id" )
    @GeneratedValue( generator = "system-uuid" )
    @GenericGenerator( name = "system-uuid", strategy = "uuid2" )
    private UUID id;

    @Column( name = "customer_id", length = 250, nullable = false )
    @NotEmpty
    private String customerId;

    public UUID getId() {

        return id;
    }

    public void setId( UUID id ) {

        this.id = id;

    }

    public String getCustomerId() {

        return customerId;
    }

    public void setCustomerId( String customerId ) {

        this.customerId = customerId;

    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                '}';
    }

}
