package com.vicpin.extrasinjector.processor

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

import java.io.IOException

import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.PackageElement
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements
import javax.tools.Diagnostic

object EnvironmentUtil {
    private var processingEnvironment: ProcessingEnvironment? = null
    var utils: Elements? = null
        private set

    fun init(environment: ProcessingEnvironment) {
        processingEnvironment = environment
        utils = processingEnvironment?.elementUtils
    }

    fun logError(message: String) {
        processingEnvironment?.messager?.printMessage(Diagnostic.Kind.ERROR, message)
    }

    fun logWarning(message: String) {
        processingEnvironment?.messager?.printMessage(Diagnostic.Kind.WARNING, message)
    }

    @Throws(IOException::class)
    fun generateFile(typeSpec: TypeSpec, packageName: String) {
        JavaFile.builder(packageName, typeSpec)
                .build()
                .writeTo(processingEnvironment?.filer)
    }

    fun isSerializable(typeMirror: TypeMirror): Boolean {
        val serializable = processingEnvironment!!.elementUtils
                .getTypeElement("java.io.Serializable").asType()
        return processingEnvironment?.typeUtils?.isAssignable(typeMirror, serializable) ?: false
    }

    fun isParcelable(typeMirror: TypeMirror): Boolean {
        val parcelable = processingEnvironment!!.elementUtils
                .getTypeElement("android.os.Parcelable").asType()
        return processingEnvironment?.typeUtils?.isAssignable(typeMirror, parcelable) ?: false
    }

    fun isArray(typeMirror: TypeMirror): Boolean {
        val array = processingEnvironment!!.elementUtils
                .getTypeElement("").asType()
        return false
    }

    fun getPackpageFor(parentClass: Element): String {
        var parent = parentClass.enclosingElement
        while (parent !is PackageElement) {
            parent = parent.enclosingElement
        }
        return parent.qualifiedName.toString()
    }

    private fun getCommonRootPackage(packpages: List<String>): String {
        var parts = packpages[0].split(".")
        var canditatePackage = ""

        parts.filter { part ->
            packpages.all {
                val index = parts.indexOf(part)
                if (it.split(".").size <= index) {
                    false
                }
                else {
                    it.split(".")[index] == part
                }
            }
        }.forEach { canditatePackage += it + "." }

        return canditatePackage.substring(0, canditatePackage.length - 1)
    }

    fun getCommonRootPackage(annotatedFields: Set<Element>): String {
        val packpages = annotatedFields.map { getPackpageFor(it) }
        return getCommonRootPackage(packpages)
    }

}
