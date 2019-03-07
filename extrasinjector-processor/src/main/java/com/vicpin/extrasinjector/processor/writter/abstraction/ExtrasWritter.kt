package com.vicpin.extrasinjector.processor.writter.abstraction

import com.vicpin.butcherknife.annotation.processor.entity.ExtraProperty
import com.vicpin.extrasinjector.processor.model.Model
import com.vicpin.extrasinjector.processor.writter.FileWritter
import javax.annotation.processing.ProcessingEnvironment

/**
 * Created by victor on 10/12/17.
 */
abstract class ExtrasWritter : Writter() {


   override fun writeModel(model: Model, processingEnv: ProcessingEnvironment) {

       val extrasForClasses = getExtrasFromModel(model)

        if(extrasForClasses.isNotEmpty()) {
            createPackage(FileWritter.PACKAGE)
            generateImports()

            generateClass()

            for (extrasForClass in extrasForClasses) {
                generateBody(targetClass = extrasForClass.key,
                        withExtras = extrasForClass.value)
            }

            closeClass()
            writter.generateFile(processingEnv, CLASS_NAME)
        }
    }

    internal abstract fun generateBody(targetClass: String, withExtras: List<ExtraProperty>)
    abstract fun getExtrasFromModel(model: Model) : Map<String, List<ExtraProperty>>

}


