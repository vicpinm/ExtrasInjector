package com.vicpin.extrasinjector.processor


import com.vicpin.extrasinjector.processor.model.Model

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

@SupportedAnnotationTypes("com.vicpin.extrasprocessor.annotations.ForFragment", "com.vicpin.extrasprocessor.annotations.ForActivity", "com.vicpin.extrasprocessor.annotations.InjectExtra")
class ExtrasInjectorProcessor : AbstractProcessor() {

    lateinit var utils: Elements

    @Synchronized override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        utils = processingEnv.elementUtils
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        EnvironmentUtil.init(processingEnv, utils)
        val model = buildModel(roundEnv)

        for (entity in model.entities) {
            createBindingClassFor(entity)
        }

        return true
    }

    private fun createBindingClassFor(entity: Model.EntityModel) {

    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    private fun buildModel(env: RoundEnvironment): Model {
        return Model.buildFrom(env, supportedAnnotationTypes)
    }
}
