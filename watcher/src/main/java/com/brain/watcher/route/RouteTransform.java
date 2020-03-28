package com.brain.watcher.route;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.gradle.api.Project;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import static com.brain.watcher.util.Utils.log;
import static com.brain.watcher.util.Utils.logDivider;

/**
 * Route watcher
 * <p>
 * Created by zero on 2020/3/21.
 */
public class RouteTransform extends Transform {

    private Project project;

    public RouteTransform(Project project) {
        this.project = project;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        logDivider();
        log("Start to inject routing related code");
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        if (outputProvider != null) {
            outputProvider.deleteAll();
        }
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        for (TransformInput input : inputs) {
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                transformDirectoryInput(directoryInput, transformInvocation);
            }
            for (JarInput jarInput : input.getJarInputs()) {
                transformJarInput(jarInput, transformInvocation);
            }
        }
        log("Finishing injecting routing related code");
        logDivider();
    }

    private void transformDirectoryInput(DirectoryInput input, TransformInvocation transformInvocation) {
        log("Transform directory input : " + input.getName());
        transformDirectoryFiles(input.getFile());
        File output = transformInvocation.getOutputProvider().getContentLocation(input.getName(),
                input.getContentTypes(), input.getScopes(), Format.DIRECTORY);
        try {
            FileUtils.copyDirectory(input.getFile(), output);
        } catch (IOException e) {
            log("Copy directory input " + input.getName() + " failed");
            e.printStackTrace();
        }
    }

    private void transformDirectoryFiles(File dir) {
        if (dir.isDirectory()) {
            log("Transform directory " + dir.getAbsolutePath());
        }
        File[] listFiles = dir.listFiles();
        if (listFiles == null) {
            return;
        }

        for (File file : listFiles) {
            if (file.isDirectory()) {
                transformDirectoryFiles(file);
            } else if (needToTransform(file.getName())) {
                log("Transform file " + file.getAbsolutePath());
                FileInputStream fileInputStream = null;
                FileOutputStream fileOutputStream = null;
                try {
                    // Transform class file
                    fileInputStream = new FileInputStream(file);
                    byte[] injectedFileBytes = transformActivityBytes(file.getName(), IOUtils.toByteArray(fileInputStream));;
                    // Write class file
                    if (injectedFileBytes != null) {
                        fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(injectedFileBytes);
                    }
                } catch (IOException e) {
                    log("Transform file " + file.getAbsolutePath() + " failed");
                    e.printStackTrace();
                } finally {

                    try {
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private void transformJarInput(JarInput input, TransformInvocation transformInvocation) {
        log("Transform jar input : " + input.getName());

        String jarName = input.getFile().getName();
        String jarMd5 = DigestUtils.md5Hex(input.getFile().getAbsolutePath());
        if (jarName.endsWith(".jar")) {
            jarName = jarName.substring(0, jarName.length() - 4);
        }
        File dest = transformInvocation.getOutputProvider().getContentLocation(jarName + jarMd5,
                input.getContentTypes(), input.getScopes(), Format.JAR);

        JarFile srcJarFile = null;
        JarOutputStream tempJarOutputStream = null;
        File tempJarFile = new File(input.getFile().getParent() + File.separator + "temp_" + input.getFile().getName());
        if (tempJarFile.exists()) {
            tempJarFile.delete();
        }
        try {
            tempJarFile.createNewFile();
        } catch (IOException e) {
            log("Create temp jar file " + tempJarFile.getName() + " failed");
            tempJarFile = null;
            e.printStackTrace();
        }

        if (tempJarFile == null) {
            // Generate temp jar file failed, copy src file to dest
            try {
                FileUtils.copyFile(input.getFile(), dest);
            } catch (IOException e) {
                log("Copy jar " + input.getFile().getName() + " failed");
                e.printStackTrace();
            }
            return;
        }

        try {
            srcJarFile = new JarFile(input.getFile());
            tempJarOutputStream = new JarOutputStream(new FileOutputStream(tempJarFile));

            Enumeration<JarEntry> srcJarEntryEnum = srcJarFile.entries();
            while (srcJarEntryEnum.hasMoreElements()) {
                JarEntry srcJarEntry = srcJarEntryEnum.nextElement();
                InputStream srcJarInputStream = srcJarFile.getInputStream(srcJarEntry);
                String srcJarEntryName = srcJarEntry.getName();
                JarEntry destJarEntry = new JarEntry(srcJarEntry.getName());
                if (needToTransform(srcJarEntryName)) {
                    // Transform jar entry data
                    log("Transform jar entry " + srcJarEntryName);
                    byte[] injectedFileBytes = transformActivityBytes(srcJarEntryName, IOUtils.toByteArray(srcJarInputStream));
                    if (injectedFileBytes != null) {
                        tempJarOutputStream.putNextEntry(destJarEntry);
                        tempJarOutputStream.write(injectedFileBytes);
                    } else {
                        log("Did not transform " + srcJarEntryName + " cause classVisitor is null");
                        // ClassVisitor is null, write src jar entry data
                        tempJarOutputStream.putNextEntry(destJarEntry);
                        tempJarOutputStream.write(IOUtils.toByteArray(srcJarInputStream));
                    }
                } else {
                    // Write src jar entry data
                    tempJarOutputStream.putNextEntry(destJarEntry);
                    tempJarOutputStream.write(IOUtils.toByteArray(srcJarInputStream));
                }
                tempJarOutputStream.closeEntry();
            }
        } catch (IOException e) {
            log("Transform jar " + input.getFile().getName() + " failed");
            e.printStackTrace();
        } finally {
            try {
                if (srcJarFile != null) {
                    srcJarFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (tempJarOutputStream != null) {
                    tempJarOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        log("Copy temp jar to " + dest.getAbsolutePath());
        try {
            FileUtils.copyFile(tempJarFile, dest);
        } catch (IOException e) {
            log("Copy jar " + tempJarFile.getName() + " failed");
            e.printStackTrace();
        }
    }

    private boolean needToTransform(String fileName) {
        return isActivity(fileName);
    }

    private boolean isActivity(String fileName) {
        return fileName.equals("androidx/appcompat/app/AppCompatActivity.class");
    }

    private byte[] transformActivityBytes(String fileName, byte[] inputBytes) {
        ClassReader classReader = new ClassReader(inputBytes);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = null;
        if (isActivity(fileName)) {
            classVisitor = new ActivityRouteClassVisitor(classWriter, fileName.replace(".class", ""));
        }
        if (classVisitor != null) {
            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
            return classWriter.toByteArray();
        } else {
            return null;
        }
    }
}
