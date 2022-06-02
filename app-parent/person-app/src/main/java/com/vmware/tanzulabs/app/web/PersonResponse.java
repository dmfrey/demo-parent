package com.vmware.tanzulabs.app.web;

import java.util.UUID;

public record PersonResponse( UUID id, String firstName, String LastName ) {
}
