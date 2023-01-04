package com.vmware.tanzulabs.app.application;

import com.vmware.tanzulabs.app.annotations.usecase.UseCase;
import com.vmware.tanzulabs.app.application.in.SeedOrdersUseCase;
import com.vmware.tanzulabs.app.application.out.DeleteAllOrdersPort;
import com.vmware.tanzulabs.app.application.out.SaveOrderPort;
import com.vmware.tanzulabs.app.domain.Order;

import java.util.List;

@UseCase
class SeedOrdersService implements SeedOrdersUseCase {

    private final SaveOrderPort saveOrderPort;
    private final DeleteAllOrdersPort deleteAllOrdersPort;

    private static final List<String> customerIds = List.of( "dfrey@vmware.com", "gallen@vmware.com", "ftyler@vmware.com", "ibautista@vmware.com", "sshah@vmware.com" );
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
                new Order( null, customerIds.get( 0 ) ),
                new Order( null, customerIds.get( 0 ) ),
                new Order( null, customerIds.get( 0 ) ),
                new Order( null, customerIds.get( 1 ) ),
                new Order( null, customerIds.get( 2 ) ),
                new Order( null, customerIds.get( 2 ) ),
                new Order( null, customerIds.get( 2 ) ),
                new Order( null, customerIds.get( 2 ) ),
                new Order( null, customerIds.get( 2 ) ),
                new Order( null, customerIds.get( 3 ) ),
                new Order( null, customerIds.get( 3 ) ),
                new Order( null, customerIds.get( 4 ) ),
                new Order( null, customerIds.get( 4 ) ),
                new Order( null, customerIds.get( 4 ) ),
                new Order( null, customerIds.get( 4 ) ),
                new Order( null, customerIds.get( 4 ) ),
                new Order( null, customerIds.get( 4 ) )
        ).forEach( this.saveOrderPort::save );

    }

}
