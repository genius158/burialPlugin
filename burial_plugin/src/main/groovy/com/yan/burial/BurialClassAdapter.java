package com.yan.burial;

import java.util.Arrays;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public final class BurialClassAdapter extends ClassVisitor {

  private String className;
  private String pluginPatch;
  private boolean isPluginInterface;
  private BurialExtension burialExtension;

  BurialClassAdapter(final ClassVisitor cv, BurialExtension burialExtension) {
    super(Opcodes.ASM5, cv);
    this.burialExtension = burialExtension;
    pluginPatch = BurialWeaver.PLUGIN_LIBRARY.replace(".", "/");
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName,
      String[] interfaces) {
    super.visit(version, access, name, signature, superName, interfaces);
    this.className = name.replace("/", ".");
    String interfacesStr = Arrays.toString(interfaces);
    isPluginInterface = interfacesStr.contains(pluginPatch);
    BurialLog.info(
        " -visit- className:" + className
            + " access:" + access
            + " version:" + version
            + " signature:" + signature
            + " superName:" + superName
            + " interfaces:" + interfacesStr
    );
  }

  @Override
  public MethodVisitor visitMethod(final int access, final String name,
      final String desc, final String signature, final String[] exceptions) {
    MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
    boolean isPluginListenMethod = desc.contains(pluginPatch);
    if (isPluginInterface || isPluginListenMethod) {
      return mv;
    }
    BurialLog.info(
        " -visitMethod- className:" + className
            + " access:" + access
            + " methodName:" + name
            + " methodDesc:" + desc
            + " signature:" + signature
            + " exceptions:" + Arrays.toString(exceptions)
    );
    return mv == null ? null
        : new BurialMethodAdapter(className, name, access, desc, mv, burialExtension);
  }
}