package com.vmware.tanzulabs.app.application.out;

import com.vmware.tanzulabs.app.domain.Order;

import java.util.UUID;

public interface SaveOrderPort {

    UUID save(Order order );
}
