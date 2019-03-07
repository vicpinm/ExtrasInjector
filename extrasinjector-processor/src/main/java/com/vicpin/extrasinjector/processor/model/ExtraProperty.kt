package com.vicpin.butcherknife.annotation.processor.entity

import com.vicpin.extrasinjector.processor.util.EnvironmentUtil
import com.vicpin.extrasprocessor.annotations.ForActivity
import com.vicpin.extrasprocessor.annotations.ForFragment
import com.vicpin.extrasprocessor.annotations.InjectExtra
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror

/**
 * Created by victor on 15/12/17.
 */
class ExtraProperty(annotatedField: Element) {

    val name: String
    val type: TypeMirror
    val presenterClass: String
    var activityClass: String? = null
    var fragmentClass: String? = null
    var optional = false

    init {
        name = annotatedField.toString()
        type = annotatedField.asType()
        presenterClass = annotatedField.enclosingElement.toString()
        optional = annotatedField.getAnnotation(InjectExtra::class.java).optional

        getViewClass(annotatedField.enclosingElement)

        if (annotatedField.modifiers.contains(Modifier.PRIVATE)) {
            // EnvironmentUtil.logError("field $name in class $$presenterClass has to be public in order to inject extras")
        }
    }

    private fun getViewClass(presenter: Element) {
        when {
            presenter.getAnnotation(ForActivity::class.java) != null -> getActivityClass(presenter)
            presenter.getAnnotation(ForFragment::class.java) != null -> getFragmentClass(presenter)
            else -> EnvironmentUtil.logError("Class $presenter has field $name annotated with @InjectExtra. You have to annotate this class with @ForActivity or @ForFragment")
        }
    }

    private fun getActivityClass(presenter: Element) {
        try {
            presenter.getAnnotation(ForActivity::class.java).activityClass
        } catch (e: MirroredTypeException) {
            activityClass = e.typeMirror.toString()
        }

        if (activityClass == null) {
            EnvironmentUtil.logError("Could not get Activity class from ForActivity annotation in class " + presenter.simpleName.toString())
        }
    }


    private fun getFragmentClass(presenter: Element) {
        try {
            presenter.getAnnotation(ForFragment::class.java).fragmentClass
        } catch (e: MirroredTypeException) {
            fragmentClass = e.typeMirror.toString()
        }

        if (fragmentClass == null) {
            EnvironmentUtil.logError("Could not get Fragment class from ForFragment annotation in class " + presenter.simpleName.toString())
        }
    }

    fun getExtraClass(): String {
        var typeString = type.toString()
        if (typeString.contains("java.lang.")) {
            typeString = typeString.removePrefix("java.lang.")
        }

        if (type.kind.isPrimitive) {
            typeString = typeString.capitalize()
        }

        if(optional) {

            typeString += when {
                type.kind == TypeKind.INT -> " = 0"
                type.kind == TypeKind.LONG -> " = 0L"
                type.kind == TypeKind.FLOAT -> " = 0F"
                type.kind == TypeKind.DOUBLE -> " = 0.0"
                type.kind == TypeKind.BOOLEAN -> " = false"
                type.kind == TypeKind.CHAR -> " = \"\""
                else -> "? = null"
            }
        }
        return typeString
    }

    fun getArgumentsPutMethod(argumentsVariable: String) = "$argumentsVariable.${putMethod()}(\"$name\",$name)"

    fun getIntentPutMethod(intentVariable: String) = "$intentVariable.putExtra(\"$name\",$name)"


    fun getBindMethod(targetVariable: String) : String {
        var line = "$targetVariable.$name = ${getMethod()}(\"$name\")"

        if(!type.kind.isPrimitive && type.toString() != String::class.java.name
                && !type.toString().contains("java.util.ArrayList")
                && EnvironmentUtil.isSerializable(type)) {
            line += " as $type"
        }
        return line
    }

    private fun putMethod() : String {
        return when {

            type.kind == TypeKind.INT -> "putInt"
            type.kind == TypeKind.LONG -> "putLong"
            type.kind == TypeKind.FLOAT -> "putFloat"
            type.kind == TypeKind.DOUBLE -> "putDouble"
            type.kind == TypeKind.BOOLEAN -> "putBoolean"
            type.kind == TypeKind.CHAR -> "putString"
            type.toString() == String::class.java.name -> "putString"
            EnvironmentUtil.isParcelableArray(type) -> "putParcelableArrayList"
            EnvironmentUtil.isParcelable(type) -> "putParcelable"
            EnvironmentUtil.isSerializable(type) -> "putSerializable"
            else -> {
                EnvironmentUtil.logError("@InjectExtra annotation does not support field type ${type.kind} for property $name")
                ""
            }
        }
    }

    private fun getMethod() : String {
        return when {
            type.kind == TypeKind.INT -> "getInt"
            type.kind == TypeKind.LONG -> "getLong"
            type.kind == TypeKind.FLOAT -> "getFloat"
            type.kind == TypeKind.DOUBLE -> "getDouble"
            type.kind == TypeKind.BOOLEAN -> "getBoolean"
            type.kind == TypeKind.CHAR -> "getString"
            type.toString() == String::class.java.name -> "getString"
            EnvironmentUtil.isParcelableArray(type) -> "getParcelableArrayList"
            EnvironmentUtil.isParcelable(type) -> "getParcelable"
            EnvironmentUtil.isSerializable(type) -> "getSerializable"
            else -> {
                EnvironmentUtil.logError("@InjectExtra annotation does not support field type ${type.kind} for property $name")
                ""
            }
        }
    }



}


