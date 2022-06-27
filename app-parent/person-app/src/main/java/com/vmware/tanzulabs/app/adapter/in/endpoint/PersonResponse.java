package com.vmware.tanzulabs.app.adapter.in.endpoint;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record PersonResponse( UUID id, String firstName, String lastName, String customerId, Optional<List<OrderResponse>> orders ) {
}
