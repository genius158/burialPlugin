package com.yan.burial;


import com.android.build.api.transform.QualifiedContent;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import quinn.hunter.bltransform.asm.BaseWeaver;

import static com.yan.burial.BurialExtension.PLUGIN_LIBRARY;

public final class BurialWeaver extends BaseWeaver {

    private BurialExtension burialExtension;

    public BurialWeaver(BurialExtension burialExtension) {
        this.burialExtension = burialExtension;
    }

    @Override
    public boolean isWeavableClass(QualifiedContent input, String fullQualifiedClassName) {
        boolean superResult = super.isWeavableClass(input, fullQualifiedClassName);
        if (!superResult) return false;
        boolean isByteCodePlugin = fullQualifiedClassName.contains(PLUGIN_LIBRARY);
        if (isByteCodePlugin) return false;

        if (burialExtension != null) {
            if (!burialExtension.foreList.isEmpty()) {
                return burialExtension.isInWhitelist(fullQualifiedClassName);
            }

            return !burialExtension.isInBlacklist(fullQualifiedClassName);
        }
        return true;
    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new BurialClassAdapter(classWriter, burialExtension);
    }
}
