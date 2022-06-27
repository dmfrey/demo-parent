package com.vmware.tanzulabs.app.application;

import com.vmware.tanzulabs.app.application.in.ListPersonUseCase;
import com.vmware.tanzulabs.app.application.out.FindAllPersonsPort;
import com.vmware.tanzulabs.app.application.out.LookupOrdersByCustomerIdPort;
import com.vmware.tanzulabs.app.domain.Order;
import com.vmware.tanzulabs.app.domain.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
class ListPersonService implements ListPersonUseCase {

    private final FindAllPersonsPort findAllPersonsPort;
    private final LookupOrdersByCustomerIdPort lookupOrdersByCustomerIdPort;

    ListPersonService(
            final FindAllPersonsPort findAllPersonsPort,
            final LookupOrdersByCustomerIdPort lookupOrdersByCustomerIdPort
    ) {

        this.findAllPersonsPort = findAllPersonsPort;
        this.lookupOrdersByCustomerIdPort = lookupOrdersByCustomerIdPort;
    }

    @Override
    public List<Person> execute() {

        return this.findAllPersonsPort.findAll().stream()
                .map( person -> {

                    Optional<List<Order>> orders = this.lookupOrdersByCustomerIdPort.lookup( person.customerId() );


                    return new Person( person.id(), person.firstName(), person.lastName(), person.customerId(), orders );
                })
                .collect( toList() );
    }

}
