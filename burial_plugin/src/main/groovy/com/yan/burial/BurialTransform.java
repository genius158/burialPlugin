package com.yan.burial;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformOutputProvider;
import com.quinn.hunter.transform.HunterTransform;
import com.quinn.hunter.transform.RunVariant;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import org.gradle.api.Project;

/**
 * Created by quinn on 07/09/2018
 */
public final class BurialTransform extends HunterTransform {
  private BurialExtension burialExtension;
  private Project project;

  public BurialTransform(Project project) {
    super(project);
    this.project = project;
    burialExtension = project.getExtensions().create("burialExt", BurialExtension.class);
    this.bytecodeWeaver = new BurialWeaver(burialExtension);
  }

  @Override
  public void transform(Context context, Collection<TransformInput> inputs,
      Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider,
      boolean isIncremental) throws IOException, TransformException, InterruptedException {
    burialExtension = (BurialExtension) project.getExtensions().getByName("burialExt");
    bytecodeWeaver.setExtension(burialExtension);
    BurialLog.logEnable = burialExtension.logEnable;
    BurialLog.info("BurialExtension:" + burialExtension.toString());
    super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
  }

  protected RunVariant getRunVariant() {
    return burialExtension.runVariant;
  }

  @Override public Set<QualifiedContent.Scope> getScopes() {
    if (burialExtension==null)return super.getScopes();
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