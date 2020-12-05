package com.yan.burial;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.quinn.hunter.transform.HunterTransform;
import com.quinn.hunter.transform.RunVariant;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public final class BurialTransform extends HunterTransform {
  private BurialExtension burialExtension;

  public BurialTransform(Project project) {
    super(project);
    burialExtension = project.getExtensions().create("burialExt", BurialExtension.class);
    this.bytecodeWeaver = new BurialWeaver(burialExtension);
  }

  @Override
  public void transform(Context context, Collection<TransformInput> inputs,
      Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider,
      boolean isIncremental) throws IOException, TransformException, InterruptedException {
    bytecodeWeaver.setExtension(burialExtension);
    BurialLog.logEnable = burialExtension.logEnable;
    BurialLog.info("BurialExtension:" + burialExtension.toString());
    super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
  }

  protected RunVariant getRunVariant() {
    return burialExtension.runVariant;
  }

  @Override public Set<QualifiedContent.Scope> getScopes() {
    if (burialExtension == null) return super.getScopes();
    Set<QualifiedContent.Scope> extScopes = burialExtension.getScopes();
    if (extScopes != null) {
      return extScopes;
    }
    return super.getScopes();
  }

  @Override
  protected boolean inDuplcatedClassSafeMode() {
    return burialExtension.duplicatedClassSafeMode;
  }
}
