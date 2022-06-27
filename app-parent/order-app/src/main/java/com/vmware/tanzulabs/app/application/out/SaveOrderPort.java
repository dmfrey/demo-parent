package com.vmware.tanzulabs.app.application.out;

import com.vmware.tanzulabs.app.domain.Order;

public interface SaveOrderPort {

    void save( Order order );
}
