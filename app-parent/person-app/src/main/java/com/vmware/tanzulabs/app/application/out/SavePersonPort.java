package com.vmware.tanzulabs.app.application.out;

import com.vmware.tanzulabs.app.domain.Person;

public interface SavePersonPort {

    void save( Person person );
}
