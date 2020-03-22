package com.yan.burial;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

public final class BurialMethodAdapter extends LocalVariablesSorter implements Opcodes {
  private int startVarIndex;

  private String classNamePath;
  private String methodDes;
  private String methodName;
  private BurialExtension burialExtension;

  public BurialMethodAdapter(String className, String methodName, int access, String desc,
      MethodVisitor mv, BurialExtension burialExtension) {
    super(Opcodes.ASM5, access, desc, mv);
    this.classNamePath = className.replace(".", "/");
    this.methodName = methodName;
    this.methodDes = desc;
    this.burialExtension = burialExtension;
  }

  @Override public void visitCode() {
    super.visitCode();
    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
    startVarIndex = newLocal(Type.LONG_TYPE);
    mv.visitVarInsn(Opcodes.LSTORE, startVarIndex);
  }

  @Override public void visitFieldInsn(int opcode, String owner, String name, String desc) {
    super.visitFieldInsn(opcode, owner, name, desc);
  }

  @Override
  public void visitInsn(int opcode) {
    if (((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW)) {
      mv.visitLdcInsn(Type.getType("L" + classNamePath + ";"));
      if (backMethodDetail()) {
        mv.visitLdcInsn(methodName);
      } else {
        mv.visitInsn(ACONST_NULL);
      }
      mv.visitLdcInsn(reset(methodDes));
      mv.visitVarInsn(LLOAD, startVarIndex);
      mv.visitMethodInsn(INVOKESTATIC, "com/yan/burial/method/timer/BurialTimer",
          "timer", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;J)V", false);
    }
    super.visitInsn(opcode);
  }

  private boolean backMethodDetail() {
    return burialExtension != null && burialExtension.listenerWithMethodDetail;
  }

  private String reset(String methodDes) {
    if (methodDes == null) return null;
    if (backMethodDetail()) {
      Pattern pattern = Pattern.compile("L[^;]+;");
      Matcher matcher = pattern.matcher(methodDes);
      while (matcher.find()) {
        String packagePath = matcher.group();
        if (packagePath.contains("/")) {
          methodDes =
              methodDes.replace(packagePath, "L" +
                  packagePath.substring(packagePath.lastIndexOf("/") + 1));
        }
      }
      return methodDes;
    }
    return methodDes.replaceAll("L[^;]+;", "L;");
  }
}
