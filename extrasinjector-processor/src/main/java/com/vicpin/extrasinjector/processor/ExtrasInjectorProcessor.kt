package com.vicpin.extrasinjector.processor


import com.vicpin.extrasinjector.processor.model.Model
import com.vicpin.extrasinjector.processor.util.EnvironmentUtil
import com.vicpin.extrasinjector.processor.writter.implementation.ActivitiesWritter
import com.vicpin.extrasinjector.processor.writter.implementation.ExtrasInjectorWritter
import com.vicpin.extrasinjector.processor.writter.implementation.FragmentsWritter
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * Created by victor on 10/12/17.
 */
@SupportedAnnotationTypes("com.vicpin.extrasprocessor.annotations.ForFragment", "com.vicpin.extrasprocessor.annotations.ForActivity", "com.vicpin.extrasprocessor.annotations.InjectExtra")
class ExtrasInjectorProcessor : AbstractProcessor() {

    val activitiesWritter = ActivitiesWritter()
    val fragmentsWritter = FragmentsWritter()
    val extrasInjectorWritter = ExtrasInjectorWritter()

    @Synchronized override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        EnvironmentUtil.init(processingEnv)
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        val model = buildModel(roundEnv)

        if(model.extraProperties.isNotEmpty()) {
            generateActivitiesFileForModel(model)
            generateFragmentsFileForModel(model)
            generateExtrasInjectorFileForModel(model)
        }

        return true
    }

    private fun generateActivitiesFileForModel(model: Model) {
        activitiesWritter.writeModel(model, processingEnv)
    }

    private fun generateFragmentsFileForModel(model: Model) {
        fragmentsWritter.writeModel(model, processingEnv)
    }


    private fun generateExtrasInjectorFileForModel(model: Model) {
        extrasInjectorWritter.writeModel(model, processingEnv)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    private fun buildModel(env: RoundEnvironment): Model {
        return Model.buildFrom(env, supportedAnnotationTypes)
    }
}
