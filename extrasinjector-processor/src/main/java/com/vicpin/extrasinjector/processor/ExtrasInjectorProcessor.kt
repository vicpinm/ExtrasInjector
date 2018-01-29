package com.vicpin.extrasinjector.processor


import com.vicpin.extrasinjector.processor.model.Model
import com.vicpin.extrasinjector.processor.writter.ActivitiesWritter
import com.vicpin.extrasinjector.processor.writter.ExtrasInjectorWritter
import com.vicpin.extrasinjector.processor.writter.FragmentsWritter
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

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

        if(model.extraProperties.size > 0) {
            generateActivitiesFileForModel(model)
            generateFragmentsFileForModel(model)
            generateExtrasInjectorFileForModel(model)
        }

        return true
    }

    private fun generateActivitiesFileForModel(model: Model) {
        activitiesWritter.writeModel(model.packpage, model.getExtrasForActivities(), processingEnv)
    }

    private fun generateFragmentsFileForModel(model: Model) {
        fragmentsWritter.writeModel(model.packpage, model.getExtrasForFragments(), processingEnv)
    }


    private fun generateExtrasInjectorFileForModel(model: Model) {
        val extrasByActivityPresenters = model.getExtrasForActivityPresenters()
        val extrasByFragmentPresenters = model.getExtrasForFragmentPresenters()

        if(extrasByActivityPresenters.isNotEmpty() || extrasByFragmentPresenters.isNotEmpty()) {

            extrasInjectorWritter.createPackage(model.packpage)

            if(extrasByActivityPresenters.isNotEmpty()) {
                extrasInjectorWritter.generateActivityBindGenericMethod(activityPresenters = extrasByActivityPresenters.keys.toList())

                for (extrasForActivities in model.getExtrasForActivityPresenters()) {
                    extrasInjectorWritter.generateActivityBindMethod(targetClass = extrasForActivities.key,
                            withExtras = extrasForActivities.value)
                }
            }

            if(extrasByFragmentPresenters.isNotEmpty()) {
                extrasInjectorWritter.generateFragmentBindGenericMethod(fragmentPresenters = extrasByFragmentPresenters.keys.toList())

                for (extrasForFragments in model.getExtrasForFragmentPresenters()) {
                    extrasInjectorWritter.generateFragmentBindMethod(targetClass = extrasForFragments.key,
                            withExtras = extrasForFragments.value)
                }
            }

            extrasInjectorWritter.closeClass()
            extrasInjectorWritter.generateFile(processingEnv, model.packpage)
        }
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    private fun buildModel(env: RoundEnvironment): Model {
        return Model.buildFrom(env, supportedAnnotationTypes)
    }
}
