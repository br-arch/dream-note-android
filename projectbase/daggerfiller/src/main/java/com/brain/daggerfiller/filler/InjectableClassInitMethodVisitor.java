package com.brain.daggerfiller.filler;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InjectableClassInitMethodVisitor extends AdviceAdapter {

    private String classFullName;
    private String componentFullName;
    private String daggerComponentFullName;

    protected InjectableClassInitMethodVisitor(MethodVisitor methodVisitor, int access, String name,
                                               String descriptor, String classPath, String className) {
        super(Opcodes.ASM4, methodVisitor, access, name, descriptor);

        String regex = "(\\w+/){5}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(classPath);
        if (matcher.find()) {
            String businessClassPath = matcher.group();
            classFullName = classPath + className;
            componentFullName = businessClassPath + "di/" + className + "Component";
            daggerComponentFullName = businessClassPath + "di/Dagger" + className + "Component";
        }
    }

    @Override
    protected void onMethodEnter() {
        if (classFullName == null || daggerComponentFullName == null) {
            return;
        }

        mv.visitMethodInsn(INVOKESTATIC, daggerComponentFullName, "builder", "()L" + daggerComponentFullName + "$Builder;", false);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, classFullName, "getArchComponent", "()Lcom/brain/arch/di/ArchComponent;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, daggerComponentFullName + "$Builder", "archComponent",
                "(Lcom/brain/arch/di/ArchComponent;)L" + daggerComponentFullName + "$Builder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, daggerComponentFullName +"$Builder", "build", "()L" + componentFullName + ";", false);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(INVOKEINTERFACE, componentFullName, "inject", "(L" + classFullName + ";)V", true);

    }
}