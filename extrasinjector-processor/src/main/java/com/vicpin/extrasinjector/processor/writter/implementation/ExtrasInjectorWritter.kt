package com.vicpin.extrasinjector.processor.writter.implementation

import com.vicpin.butcherknife.annotation.processor.entity.ExtraProperty
import com.vicpin.extrasinjector.processor.model.Model
import com.vicpin.extrasinjector.processor.writter.FileWritter
import com.vicpin.extrasinjector.processor.writter.abstraction.Writter
import javax.annotation.processing.ProcessingEnvironment

/**
 * Created by Victor on 26/01/2018.
 */
class ExtrasInjectorWritter : Writter() {

    override var CLASS_NAME = "ExtrasInjectorFactory"

    override fun createPackage(packpage: String) {
        writter.setPackage(packpage)

    }

    override fun generateImports() {
        writter.writeImport("import android.content.Context")
        writter.writeImport("import android.app.Activity")
        writter.writeImport("import android.support.v4.app.Fragment")
    }

    override fun generateClass() {
        writter.openClass("object $CLASS_NAME")
    }

    override fun writeModel(model: Model, processingEnv: ProcessingEnvironment) {
        val extrasByActivityPresenters = model.getExtrasForActivityPresenters()
        val extrasByFragmentPresenters = model.getExtrasForFragmentPresenters()

        if(extrasByActivityPresenters.isNotEmpty() || extrasByFragmentPresenters.isNotEmpty()) {

            createPackage(FileWritter.PACKAGE)
            generateImports()

            if(model.useParcelerLibrary()) {
                writter.writeImport("import org.parceler.Parcels")
            }

            generateClass()

            if(extrasByActivityPresenters.isNotEmpty()) {
                generateActivityBindGenericMethod(activityPresenters = extrasByActivityPresenters.keys.toList())

                for (extrasForActivities in model.getExtrasForActivityPresenters()) {
                    generateActivityBindMethod(targetClass = extrasForActivities.key,
                            withExtras = extrasForActivities.value)
                }
            }

            if(extrasByFragmentPresenters.isNotEmpty()) {
                generateFragmentBindGenericMethod(fragmentPresenters = extrasByFragmentPresenters.keys.toList())

                for (extrasForFragments in model.getExtrasForFragmentPresenters()) {
                    generateFragmentBindMethod(targetClass = extrasForFragments.key,
                            withExtras = extrasForFragments.value)
                }
            }

            closeClass()
            writter.generateFile(processingEnv, CLASS_NAME)
        }
    }


    private fun generateActivityBindGenericMethod(activityPresenters: List<String>) {
        writter.apply {
            openMethod("@JvmStatic fun bind(act: Activity, target: Any)")
            methodBody("when(target) {")
            for(presenter in activityPresenters) {
                methodBody("is $presenter -> bind(act, target)", indentationLevel = 1)
            }
            methodBody("else -> android.util.Log.w(\"ExtrasInjector\",\"Target \${target::class.java.name} supplied to bind function is not valid. Have you annotated this class with @ForActivity?\")")
            methodBody("}")
            closeMethod()
        }
    }

    private fun generateFragmentBindGenericMethod(fragmentPresenters: List<String>) {
        writter.apply {
            openMethod("@JvmStatic fun bind(act: Fragment, target: Any)")
            methodBody("when(target) {")
            for(presenter in fragmentPresenters) {
                methodBody("is $presenter -> bind(act, target)", indentationLevel = 1)
            }
            methodBody("else -> android.util.Log.w(\"ExtrasInjector\",\"Target \${target::class.java.name} supplied to bind function is not valid. Have you annotated this class with @ForFragment?\")")
            methodBody("}")
            closeMethod()
        }
    }

    private fun generateActivityBindMethod(targetClass: String, withExtras: List<ExtraProperty>) {
        writter.apply {
            openMethod("fun bind(act: Activity, target: $targetClass)")
            methodBody("act.intent.extras?.apply {")
            for(extra in withExtras) {
                methodBody(extra.getBindMethod("target"), indentationLevel = 1)
            }
            methodBody("}")
            closeMethod()
        }
    }

    private fun generateFragmentBindMethod(targetClass: String, withExtras: List<ExtraProperty>) {
        writter.apply {
            openMethod("fun bind(fragment: Fragment, target: $targetClass)")
            methodBody("fragment.arguments?.apply {")
            for(extra in withExtras) {
                methodBody(extra.getBindMethod("target"), indentationLevel = 1)
            }
            methodBody("}")
            closeMethod()
        }
    }

}