package com.vicpin.extrasinjector.processor.model

import com.vicpin.butcherknife.annotation.processor.entity.ExtraProperty
import com.vicpin.extrasinjector.processor.util.EnvironmentUtil
import com.vicpin.extrasprocessor.annotations.ForActivity
import com.vicpin.extrasprocessor.annotations.ForFragment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.ElementKind

/**
 * Created by Victor on 07/12/2017.
 */
class Model private constructor() {

    var extraProperties = mutableListOf<ExtraProperty>()

    private fun parseAnnotation(env: RoundEnvironment, annotationClass: Class<Annotation>) {
        val annotatedFields = env.getElementsAnnotatedWith(annotationClass)

        for (annotatedField in annotatedFields) {

            if (annotatedField.getAnnotation(ForActivity::class.java) != null ||
                    annotatedField.getAnnotation(ForFragment::class.java) != null) {
                if (annotatedField.kind != ElementKind.CLASS) {
                    EnvironmentUtil.logError(annotationClass.simpleName + " can only be used for classes")
                }
                break
            } else if (annotatedField.kind != ElementKind.FIELD) {
                EnvironmentUtil.logError(annotationClass.simpleName + " can only be used for properties")
                break
            }

            val property = ExtraProperty(annotatedField)

            extraProperties.add(property)
        }
    }

    fun getExtrasForActivities(): Map<String, List<ExtraProperty>> {
        val map = mutableMapOf<String, List<ExtraProperty>>()
        val activities = extraProperties.filter { it.activityClass != null }.distinctBy { it.activityClass }.map { it.activityClass }
        for(activity in activities) {
            map[activity!!] = extraProperties.filter { it.activityClass == activity }
        }
        return map
    }

    fun getExtrasForFragments(): Map<String, List<ExtraProperty>> {
        val map = mutableMapOf<String, List<ExtraProperty>>()
        val fragments = extraProperties.filter { it.fragmentClass != null }.distinctBy { it.fragmentClass }.map { it.fragmentClass }
        for(fragment in fragments) {
            map[fragment!!] = extraProperties.filter { it.fragmentClass == fragment }
        }
        return map
    }

    fun getExtrasForActivityPresenters(): Map<String, List<ExtraProperty>> {
        val map = mutableMapOf<String, List<ExtraProperty>>()
        val activityPresenters = extraProperties.filter { it.activityClass != null }.distinctBy { it.presenterClass }.map { it.presenterClass }
        for(presenter in activityPresenters) {
            map[presenter] = extraProperties.filter { it.presenterClass == presenter }
        }
        return map
    }

    fun getExtrasForFragmentPresenters(): Map<String, List<ExtraProperty>> {
        val map = mutableMapOf<String, List<ExtraProperty>>()
        val fragmentPresenters = extraProperties.filter { it.fragmentClass != null }.distinctBy { it.presenterClass }.map { it.presenterClass }
        for(presenter in fragmentPresenters) {
            map[presenter] = extraProperties.filter { it.presenterClass == presenter }
        }
        return map
    }

    fun useParcelerLibrary() = extraProperties.any { EnvironmentUtil.isParcelableWithParceler(it.type) }


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


