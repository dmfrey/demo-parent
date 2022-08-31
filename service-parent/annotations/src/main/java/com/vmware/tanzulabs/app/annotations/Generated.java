package com.vmware.tanzulabs.app.annotations;

import java.lang.annotation.*;

@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Generated {
}
