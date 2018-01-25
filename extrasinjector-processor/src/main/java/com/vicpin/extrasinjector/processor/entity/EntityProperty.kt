package com.vicpin.butcherknife.annotation.processor.entity

import com.vicpin.extrasinjector.processor.EnvironmentUtil
import com.vicpin.extrasprocessor.annotations.ForActivity
import javax.lang.model.element.Element
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror

/**
 * Created by victor on 15/12/17.
 */

class EntityProperty(annotatedField: Element) {

    val name: String
    val type: TypeKind
    val typeMirror: TypeMirror
    val presenterClass: String
    val activityClass: String


    init {
        name = annotatedField.toString()
        typeMirror = annotatedField.asType()
        type = typeMirror.kind
        presenterClass = annotatedField.enclosingElement.toString()
        activityClass = getActivityClass(annotatedField.enclosingElement)

        EnvironmentUtil.logWarning("name: $name type $type mirror $typeMirror presenter $presenterClass activity $activityClass" )
    }

    fun getActivityClass(presenter: Element) : String {
        try {
            presenter.getAnnotation(ForActivity::class.java).activityClass
        } catch (e: MirroredTypeException) {
            return e.typeMirror.toString()
        }

        EnvironmentUtil.logError("Could not get Activity class from ForActivity annotation in class " + presenter.simpleName.toString())
        return ""
    }



}
