package com.vmware.tanzulabs.app;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

class ArchitectureTest {

    private static JavaClasses importedClasses;

    @BeforeAll
    static void setup() {

        ImportOption ignoreTests = location -> !location.contains( "/test/" );


        importedClasses =
                new ClassFileImporter()
                        .withImportOption( ignoreTests )
                        .importPackages( "com.vmware.tanzulabs.app" );

    }

    @Test
    void weShouldOnlyBeAccessedByEndpointAdapter() {

        var serviceRule =
                classes()
                        .that()
                            .resideInAPackage( "..adapter.in.endpoint.." )
                        .should()
                            .onlyBeAccessed()
                                .byAnyPackage( "..adapter.in.endpoint.." );

        serviceRule.check( importedClasses );

    }

    @Test
    void weShouldOnlyBeAccessedByPersistenceAdapter() {

        var serviceRule =
                classes()
                        .that()
                        .resideInAPackage( "..adapter.out.persistence.." )
                        .should()
                        .onlyBeAccessed()
                        .byAnyPackage( "..adapter.out.persistence.." );

        serviceRule.check( importedClasses );

    }

    @Test
    void hexagonalArchitecture() {

        var hexagonalArchitecture =
                onionArchitecture()
                        .withOptionalLayers( true )
                        .domainModels( "..domain.." )
                        .applicationServices( "..application.." )
                        .adapter( "endpoint", "..adapter.in.endpoint.." )
                        .adapter( "persistence", "..adapter.out.persistence.." );

        hexagonalArchitecture.check( importedClasses );

        SlicesRuleDefinition
                .slices()
                    .matching( "..adapter.in.(**)" )
                .should()
                    .notDependOnEachOther();

        SlicesRuleDefinition
                .slices()
                    .matching( "..adapter.out.(**)" )
                .should()
                    .notDependOnEachOther();

    }

    @Test
    void domainLayerDoesNotDependOnApplicationLayer() {

        noClasses()
                .that()
                    .resideInAPackage( "..domain.." )
                .should()
                    .dependOnClassesThat()
                        .resideInAnyPackage( "..application.." )
                .check( importedClasses );

    }

}
