package com.vmware.tanzulabs.app.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record Person( UUID id, String firstName, String lastName, String customerId, Optional<List<Order>> orders ) {

}
