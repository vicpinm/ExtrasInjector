package com.vicpin.extrasinjector.processor.model


import com.vicpin.butcherknife.annotation.processor.entity.EntityProperty
import com.vicpin.extrasinjector.processor.EnvironmentUtil
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.PackageElement


/**
 * Created by Oesia on 07/12/2017.
 */
class Model private constructor() {

    var entities = mutableListOf<EntityModel>()

    private fun parseAnnotation(env: RoundEnvironment, annotationClass: Class<Annotation>) {
        val annotatedFields = env.getElementsAnnotatedWith(annotationClass)

        for (annotatedField in annotatedFields) {
            if (annotatedField.kind != ElementKind.FIELD) {
                EnvironmentUtil.logError(annotationClass.simpleName + " can only be used for properties")
                break
            }

            val entity = createEntityModel(annotatedField.enclosingElement)
            val property = EntityProperty(annotatedField)

            entity.addProperty(property)
            entities.add(entity)

        }

    }

    private fun createEntityModel(parentClass: Element): EntityModel {

        var entityModel = findEntityModel(parentClass)
        if (entityModel == null) {
            entityModel = EntityModel(parentClass, getPackpageFor(parentClass))
            entities.add(entityModel)
        }

        return entityModel


    }

    private fun getPackpageFor(parentClass: Element): String {
        var parent = parentClass.enclosingElement
        while (parent !is PackageElement) {
            parent = parent.enclosingElement
        }
        return parent.qualifiedName.toString()
    }

    private fun findEntityModel(modelClass: Element): EntityModel? {
        return entities.firstOrNull { it.modelClass == modelClass }
    }


    inner class EntityModel(internal val modelClass: Element, internal val pkg: String) {
        var name: String
        var properties = mutableListOf<EntityProperty>()

        init {
            this.name = modelClass.simpleName.toString()
        }

        fun addProperty(property: EntityProperty) {
            properties.add(property)
        }

        fun getPropertyName(property: EntityProperty): String {
            return "${name.toLowerCase()}_${properties.indexOf(property)}"
        }


    }

    companion object {

        fun buildFrom(env: RoundEnvironment, annotations: Set<String>): Model {
            val model = Model()

            for (annotation in annotations) {
                try {
                    model.parseAnnotation(env, Class.forName(annotation) as Class<Annotation>)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            return model
        }
    }
}


