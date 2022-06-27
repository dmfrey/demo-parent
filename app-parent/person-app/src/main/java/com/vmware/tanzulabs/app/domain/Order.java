package com.vmware.tanzulabs.app.domain;

import java.util.UUID;

public record Order( UUID orderId, String customerId ) {

}
