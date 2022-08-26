package com.vmware.tanzulabs.app.adapter;

import java.nio.file.FileSystems;
import java.nio.file.Paths;

public final class SpringCloudContractHelper {

    public static String repoRoot() {

        try {

            var projectModulePath = FileSystems.getDefault().getPath("" ).toAbsolutePath();
            var projectParentPath = projectModulePath.getParent().toAbsolutePath();
            var rootPath = projectParentPath.getParent().toAbsolutePath();
            var stubsPath = Paths.get( rootPath.toString(), "/stubs" );

            return "stubs://file://" + stubsPath;

        } catch( Exception e ) {

            System.err.println( e );
            e.printStackTrace();

            return "";
        }

    }

}
