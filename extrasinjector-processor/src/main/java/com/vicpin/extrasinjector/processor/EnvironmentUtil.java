package com.vicpin.extrasinjector.processor;


import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;


public final class EnvironmentUtil {
    private static ProcessingEnvironment processingEnvironment;
    private static Elements utils;

    private EnvironmentUtil() {
        // Empty private constructor
    }

    public static void init(ProcessingEnvironment environment, Elements envUtils) {
        processingEnvironment = environment;
        utils = envUtils;
    }

    public static void logError(String message) {
        processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }

    public static void logWarning(String message) {
        processingEnvironment.getMessager().printMessage(Diagnostic.Kind.WARNING, message);
    }

    public static void generateFile(final TypeSpec typeSpec, String packageName) throws IOException {
        JavaFile.builder(packageName, typeSpec)
                .build()
                .writeTo(processingEnvironment.getFiler());
    }

    public static boolean isSerializable(TypeMirror typeMirror) {
        final TypeMirror serializable = processingEnvironment.getElementUtils()
                .getTypeElement("java.io.Serializable").asType();
        return processingEnvironment.getTypeUtils().isAssignable(typeMirror, serializable);
    }

    public static boolean isParcelable(TypeMirror typeMirror) {
        final TypeMirror parcelable = processingEnvironment.getElementUtils()
                .getTypeElement("android.os.Parcelable").asType();
        return processingEnvironment.getTypeUtils().isAssignable(typeMirror, parcelable);
    }

    public static boolean isArray(TypeMirror typeMirror) {
        final TypeMirror array = processingEnvironment.getElementUtils()
                .getTypeElement("").asType();
        return false;
    }

    public static Elements getUtils() {
        return utils;
    }
}
