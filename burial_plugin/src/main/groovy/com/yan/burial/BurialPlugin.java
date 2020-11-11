package com.yan.burial;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.BaseExtension;
import com.android.build.gradle.LibraryExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.UnknownDomainObjectException;

import java.util.Collections;

public class BurialPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getDependencies().add("api", "com.yan.burial.method.timer:burialtimer:1.0.8");
        BaseExtension extension = null;
        try {
            extension = project.getExtensions().getByType(AppExtension.class);
        } catch (UnknownDomainObjectException e1) {
            e1.printStackTrace();
            try {
                extension = project.getExtensions().getByType(LibraryExtension.class);
            } catch (UnknownDomainObjectException e2) {
                e2.printStackTrace();
            }
        }
        if (extension == null) {
            throw new RuntimeException("error when BurialPlugin apply");
        }
        extension.registerTransform(new BurialTransform(project), Collections.EMPTY_LIST);
    }
}
