package com.vmware.tanzulabs.app.adapter.in.endpoint;

import java.util.UUID;

record OrderResponse(UUID orderId, String customerId ) {
}
