package com.yan.burial;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;

public final class BurialClassAdapter extends ClassVisitor {

    private String className;
    private String pluginPatch;
    private boolean isPluginInterface;
    private BurialExtension burialExtension;

    BurialClassAdapter(final ClassVisitor cv, BurialExtension burialExtension) {
        super(Opcodes.ASM6, cv);
        this.burialExtension = burialExtension;
        pluginPatch = BurialExtension.PLUGIN_LIBRARY.replace(".", "/");
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName,
                      String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        if (name != null) this.className = name.replace("/", ".");

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
        boolean isPluginListenMethod = className.contains(pluginPatch) || className.contains(BurialExtension.PLUGIN_LIBRARY);
        if (isPluginInterface || isPluginListenMethod) {
            return cv.visitMethod(access, name, desc, signature, exceptions);
        }
        BurialLog.info(
                " -visitMethod- className:" + className
                        + " access:" + access
                        + " methodName:" + name
                        + " methodDesc:" + desc
                        + " signature:" + signature
                        + " exceptions:" + Arrays.toString(exceptions)
        );
        MethodVisitor mcv = cv.visitMethod(access, name, desc, signature, exceptions);
        return mcv == null ? null
                : new BurialMethodAdapter(className, name, access, desc, mcv, burialExtension);
    }
}