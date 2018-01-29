package com.vicpin.extrasinjector.processor.writter

import com.vicpin.butcherknife.annotation.processor.entity.ExtraProperty
import java.io.File
import java.io.IOException
import javax.annotation.processing.ProcessingEnvironment

/**
 * Created by Oesia on 26/01/2018.
 */
class ExtrasInjectorWritter {

    companion object {
        private val CLASS_NAME = "ExtrasInjector"
        private val KAPT_KOTLIN_GENERATED_OPTION = "kapt.kotlin.generated"
    }

    private val writter = FileWritter()

    fun createPackage(packpage: String) {
        writter.setPackage(packpage)
        generateImports()
        generateClass()
    }

    private fun generateImports() {
        writter.writeImport("import android.content.Context")
        writter.writeImport("import android.app.Activity")
        writter.writeImport("import android.support.v4.app.Fragment")
    }

    private fun generateClass() {
        writter.openClass("object $CLASS_NAME")
    }

    fun generateActivityBindGenericMethod(activityPresenters: List<String>) {
        writter.apply {
            openMethod("fun bind(act: Activity, target: Any)")
            methodBody("when(target) {")
            for(presenter in activityPresenters) {
                methodBody("is $presenter -> bind(act, target)", indentationLevel = 1)
            }
            methodBody("}")
            closeMethod()
        }
    }

    fun generateFragmentBindGenericMethod(fragmentPresenters: List<String>) {
        writter.apply {
            openMethod("fun bind(act: Fragment, target: Any)")
            methodBody("when(target) {")
            for(presenter in fragmentPresenters) {
                methodBody("is $presenter -> bind(act, target)", indentationLevel = 1)
            }
            methodBody("}")
            closeMethod()
        }
    }

    fun generateActivityBindMethod(targetClass: String, withExtras: List<ExtraProperty>) {
        writter.apply {
            openMethod("fun bind(act: Activity, target: $targetClass)")
            methodBody("val bundle = act.intent.extras")
            for(extra in withExtras) {
                methodBody(extra.getBindMethod("target", "bundle"))
            }
            closeMethod()
        }
    }

    fun generateFragmentBindMethod(targetClass: String, withExtras: List<ExtraProperty>) {
        writter.apply {
            openMethod("fun bind(fragment: Fragment, target: $targetClass)")
            methodBody("val bundle = fragment.arguments")
            for(extra in withExtras) {
                methodBody(extra.getBindMethod("target", "bundle"))
            }
            closeMethod()
        }
    }

    fun closeClass() {
        writter.closeClass()
    }

    fun generateFile(env: ProcessingEnvironment, packpage: String) {

        try { // write the env
            val options = env.options
            val kotlinGenerated = options[KAPT_KOTLIN_GENERATED_OPTION] ?: ""

            File(kotlinGenerated.replace("kaptKotlin","kapt"), "$packpage.${CLASS_NAME}.kt").writer().buffered().use {
                it.appendln(writter.text)
            }

        } catch (e: IOException) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }
    }
}