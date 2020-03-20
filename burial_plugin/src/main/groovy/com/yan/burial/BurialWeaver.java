package com.yan.burial;

import com.quinn.hunter.transform.asm.BaseWeaver;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Created by Quinn on 09/07/2017.
 */
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
      //whitelist is prior to to blacklist
      if (!burialExtension.whitelist.isEmpty()) {
        boolean inWhiteList = false;
        for (String item : burialExtension.whitelist) {
          if (fullQualifiedClassName.startsWith(item)) {
            inWhiteList = true;
            break;
          }
        }
        return superResult && inWhiteList;
      }
      if (!burialExtension.blacklist.isEmpty()) {
        boolean inBlackList = false;
        for (String item : burialExtension.blacklist) {
          if (fullQualifiedClassName.startsWith(item)) {
            inBlackList = true;
            break;
          }
        }
        return superResult && !inBlackList;
      }
    }
    return superResult;
  }

  @Override
  protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
    return new BurialClassAdapter(classWriter);
  }
}
