package com.vmware.tanzulabs.app.application;

import com.vmware.tanzulabs.app.application.in.SeedOrdersUseCase;
import com.vmware.tanzulabs.app.application.out.DeleteAllOrdersPort;
import com.vmware.tanzulabs.app.application.out.SaveOrderPort;
import com.vmware.tanzulabs.app.domain.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class SeedOrdersService implements SeedOrdersUseCase {

    private final SaveOrderPort saveOrderPort;
    private final DeleteAllOrdersPort deleteAllOrdersPort;

    SeedOrdersService(
            final SaveOrderPort saveOrderPort,
            final DeleteAllOrdersPort deleteAllOrdersPort
    ) {

        this.saveOrderPort = saveOrderPort;
        this.deleteAllOrdersPort = deleteAllOrdersPort;

    }

    @Override
    public void execute() {

        this.deleteAllOrdersPort.deleteAll();

        List.of(
                new Order( null, "dfrey@vmware.com" ),
                new Order( null, "dfrey@vmware.com" ),
                new Order( null, "dfrey@vmware.com" ),
                new Order( null, "gallen@vmware.com" ),
                new Order( null, "ftyler@vmware.com" ),
                new Order( null, "ftyler@vmware.com" ),
                new Order( null, "ftyler@vmware.com" ),
                new Order( null, "ftyler@vmware.com" ),
                new Order( null, "ftyler@vmware.com" ),
                new Order( null, "ibautista@vmware.com" ),
                new Order( null, "ibautista@vmware.com" ),
                new Order( null, "sshah@vmware.com" ),
                new Order( null, "sshah@vmware.com" ),
                new Order( null, "sshah@vmware.com" ),
                new Order( null, "sshah@vmware.com" ),
                new Order( null, "sshah@vmware.com" ),
                new Order( null, "sshah@vmware.com" )
        ).forEach( this.saveOrderPort::save );

    }

}
