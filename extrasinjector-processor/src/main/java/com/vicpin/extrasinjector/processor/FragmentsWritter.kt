package com.vicpin.extrasinjector.processor

import com.vicpin.butcherknife.annotation.processor.entity.ExtraProperty
import java.io.File
import java.io.IOException
import javax.annotation.processing.ProcessingEnvironment

/**
 * Created by victor on 10/12/17.
 */
class FragmentsWritter {

    companion object {
        private val CLASS_NAME = "Fragments"
        private val KAPT_KOTLIN_GENERATED_OPTION = "kapt.kotlin.generated"
    }

    private val writter = Writter()

    fun createPackage(packpage: String) {
        writter.setPackage(packpage)
        generateImports()
        generateClass()
    }

    private fun generateImports() {
        writter.writeImport("import android.content.Intent")
        writter.writeImport("import android.support.v4.app.Fragment")
        writter.writeImport("import android.os.Bundle")
    }

    private fun generateClass() {
        writter.openClass("object $CLASS_NAME")
    }

    fun generateFragmentMethod(forFragment: String, withExtras: List<ExtraProperty>) {
        writter.apply {
            val params = withExtras.joinToString { "${it.name}: ${it.getExtraClass()}" }

            openMethod("fun create$forFragment($params) : Fragment")
            methodBody("val fragment = $forFragment()")
            methodBody("val args = Bundle()")
            for(extra in withExtras) {
                methodBody(extra.getBindExtraLine(argumentsVariable = "args"))
            }
            methodBody("fragment.arguments = args")
            methodBody("return fragment")
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

            File(kotlinGenerated.replace("kaptKotlin","kapt"), "$packpage.$CLASS_NAME.kt").writer().buffered().use {
                it.appendln(writter.text)
            }

        } catch (e: IOException) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }
    }
}
