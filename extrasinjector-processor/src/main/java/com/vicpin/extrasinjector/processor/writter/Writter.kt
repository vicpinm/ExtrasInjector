package com.vicpin.extrasinjector.processor.writter

import com.vicpin.butcherknife.annotation.processor.entity.ExtraProperty
import javax.annotation.processing.ProcessingEnvironment

/**
 * Created by Oesia on 29/01/2018.
 */
abstract class Writter {

    abstract var CLASS_NAME : String

    internal val writter = FileWritter()

    fun writeModel(packpage: String, extrasForClasses: Map<String, List<ExtraProperty>> , processingEnv: ProcessingEnvironment) {

        if(extrasForClasses.isNotEmpty()) {
            createPackage(packpage)

            for (extrasForClass in extrasForClasses) {
                generateBody(targetClass = extrasForClass.key,
                        withExtras = extrasForClass.value)
            }

            closeClass()
            writter.generateFile(processingEnv, packpage, CLASS_NAME)
        }
    }

    internal abstract fun createPackage(packpage: String)
    internal abstract fun generateImports()
    internal abstract fun generateClass()
    internal abstract fun generateBody(targetClass: String, withExtras: List<ExtraProperty>)

    private fun closeClass() {
        writter.closeClass()
    }
}


