package com.vicpin.extrasinjector.processor


import com.vicpin.extrasinjector.processor.model.Model
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

    @Synchronized override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        EnvironmentUtil.init(processingEnv)
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {

        val model = buildModel(roundEnv)

        if(model.extraProperties.size > 0) {
            generateActivitiesFileForModel(model)
            generateFragmentsFileForModel(model)
        }

        return true
    }

    private fun generateActivitiesFileForModel(model: Model) {

        if(model.getExtrasForActivities().isNotEmpty()) {
            activitiesWritter.createPackage(model.packpage)

            for (extrasForActivity in model.getExtrasForActivities()) {
                EnvironmentUtil.logWarning("Processing extras for activity: " + extrasForActivity.key)
                activitiesWritter.generateIntentMethod(forActivity = extrasForActivity.key,
                        withExtras = extrasForActivity.value)
            }

            activitiesWritter.closeClass()
            activitiesWritter.generateFile(processingEnv, model.packpage)
        }
    }

    private fun generateFragmentsFileForModel(model: Model) {
        if(model.getExtrasForFragments().isNotEmpty()) {

            fragmentsWritter.createPackage(model.packpage)

            for (extrasForFragments in model.getExtrasForFragments()) {
                EnvironmentUtil.logWarning("Processing extras for fragment: " + extrasForFragments.key)
                fragmentsWritter.generateFragmentMethod(forFragment = extrasForFragments.key,
                        withExtras = extrasForFragments.value)
            }

            fragmentsWritter.closeClass()
            fragmentsWritter.generateFile(processingEnv, model.packpage)
        }
    }



    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    private fun buildModel(env: RoundEnvironment): Model {
        return Model.buildFrom(env, supportedAnnotationTypes)
    }
}
