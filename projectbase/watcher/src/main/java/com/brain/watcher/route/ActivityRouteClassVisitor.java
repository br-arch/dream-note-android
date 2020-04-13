package com.brain.watcher.route;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static com.brain.watcher.util.Utils.log;

public class ActivityRouteClassVisitor extends ClassVisitor {

    private String className;

    public ActivityRouteClassVisitor(ClassVisitor classVisitor, String className) {
        super(Opcodes.ASM5, classVisitor);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (name.equals("onCreate")) {
            log("inject routing related code in " + className + " onCreate");
            return new PageEnterMethodVisitor(mv, access, name, descriptor);
        } else if (name.equals("onDestroy")) {
            log("inject routing related code in " + className + " onDestroy");
            return new PageExitMethodVisitor(mv, access, name, descriptor);
        }
        return mv;
    }
}
