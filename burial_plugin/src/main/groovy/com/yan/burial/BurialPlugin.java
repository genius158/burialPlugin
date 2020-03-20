package com.yan.burial;

import com.android.build.gradle.AppExtension;
import java.util.Collections;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Created by Quinn on 25/02/2017.
 */
public class BurialPlugin implements Plugin<Project> {

    @SuppressWarnings("NullableProblems")
    @Override
    public void apply(Project project) {
        AppExtension appExtension = (AppExtension)project.getProperties().get("android");
        appExtension.registerTransform(new BurialTransform(project), Collections.EMPTY_LIST);
    }

}
