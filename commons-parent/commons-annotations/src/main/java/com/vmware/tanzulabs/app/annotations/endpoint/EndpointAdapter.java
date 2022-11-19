package com.vmware.tanzulabs.app.annotations.endpoint;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention( RetentionPolicy.RUNTIME )
@Documented
@RestController
public @interface EndpointAdapter {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any (or empty String otherwise)
     */
    @AliasFor( annotation = RestController.class )
    String value() default "";

}
