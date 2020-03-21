package com.yan.burial;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

public final class BurialMethodAdapter extends LocalVariablesSorter implements Opcodes {
  private int startVarIndex;

  private String className;
  private String methodDes;

  public BurialMethodAdapter(String className, String methodName, int access, String desc,
      MethodVisitor mv, BurialExtension burialExtension) {
    super(Opcodes.ASM5, access, desc, mv);
    this.className = className;
    this.methodDes = desc;
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
      mv.visitLdcInsn(Type.getType("L" + className + ";"));
      mv.visitLdcInsn(methodDes);
      mv.visitVarInsn(LLOAD, startVarIndex);

      mv.visitMethodInsn(INVOKESTATIC, "com/yan/burial/method/timer/BurialTimer",
          "timer", "(Ljava/lang/Class;Ljava/lang/String;J)V", false);
    }
    super.visitInsn(opcode);
  }
}
