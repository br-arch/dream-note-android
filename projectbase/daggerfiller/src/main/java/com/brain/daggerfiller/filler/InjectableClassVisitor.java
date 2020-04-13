package com.brain.daggerfiller.filler;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static com.brain.daggerfiller.util.Utils.log;

public class InjectableClassVisitor extends ClassVisitor {

    private String classPath;
    private String className;
    private String[] interfaces;
    
    public InjectableClassVisitor(ClassVisitor classVisitor, String className) {
        super(Opcodes.ASM5, classVisitor);
        this.className = className;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        classPath = name.replace(className, "");
        this.interfaces = interfaces;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (shouldFillDaggerCode(name, descriptor)) {
            log("inject dagger filling related code in " + className + " onCreate");
            return new InjectableClassInitMethodVisitor(mv, access, name, descriptor, classPath, className);
        }
        return mv;
    }
    
    private boolean shouldFillDaggerCode(String name, String desc) {
        boolean isInjectableClass = false;
        if (interfaces != null) {
            for (String anInterface : interfaces) {
                if (anInterface.equals("com/brain/arch/di/DaggerFillable")) {
                    isInjectableClass = true;
                    break;
                }
            }
        }
        return isInjectableClass && name.equals("onCreate") && desc.equals("(Landroid/os/Bundle;)V");
    }
}
