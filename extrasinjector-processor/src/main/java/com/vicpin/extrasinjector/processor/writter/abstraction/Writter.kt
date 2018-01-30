package com.vicpin.extrasinjector.processor.writter.abstraction

import com.vicpin.extrasinjector.processor.model.Model
import com.vicpin.extrasinjector.processor.writter.FileWritter
import javax.annotation.processing.ProcessingEnvironment

/**
 * Created by Oesia on 29/01/2018.
 */
abstract class Writter {

    abstract var CLASS_NAME : String

    internal val writter = FileWritter()

    internal abstract fun writeModel(model: Model, processingEnv: ProcessingEnvironment)
    internal abstract fun createPackage(packpage: String)
    internal abstract fun generateImports()
    internal abstract fun generateClass()

    internal fun closeClass() {
        writter.closeClass()
    }
}


