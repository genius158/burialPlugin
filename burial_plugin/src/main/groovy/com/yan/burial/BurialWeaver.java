package com.yan.burial;

import com.quinn.hunter.transform.asm.BaseWeaver;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public final class BurialWeaver extends BaseWeaver {
  static final String PLUGIN_LIBRARY = "com.yan.burial.method.timer";

  private BurialExtension burialExtension;

  public BurialWeaver(BurialExtension burialExtension) {
    this.burialExtension = burialExtension;
  }

  @Override
  public boolean isWeavableClass(String fullQualifiedClassName) {
    boolean superResult = super.isWeavableClass(fullQualifiedClassName);
    boolean isByteCodePlugin = fullQualifiedClassName.startsWith(PLUGIN_LIBRARY);
    if (isByteCodePlugin) return false;

    if (burialExtension != null) {
      if (!burialExtension.foreList.isEmpty()) {
        return burialExtension.isInWhitelist(fullQualifiedClassName) && superResult;
      }

      if (burialExtension.isInBlacklist(fullQualifiedClassName)) return !superResult;
    }
    return superResult;
  }

  @Override
  protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
    return new BurialClassAdapter(classWriter, burialExtension);
  }
}
