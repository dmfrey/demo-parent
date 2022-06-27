package com.vmware.tanzulabs.app.adapter.out.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties( prefix = "webservice" )
public record OrderResourceConfigurationProperties( String orderResourceUrl ) {
}
