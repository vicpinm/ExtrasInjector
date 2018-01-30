package com.vicpin.extrasinjector.processor.writter.abstraction

import com.vicpin.butcherknife.annotation.processor.entity.ExtraProperty
import com.vicpin.extrasinjector.processor.model.Model
import javax.annotation.processing.ProcessingEnvironment

/**
 * Created by Oesia on 29/01/2018.
 */
abstract class ExtrasWritter : Writter() {


   override fun writeModel(model: Model, processingEnv: ProcessingEnvironment) {

       val packpage = model.packpage
       val extrasForClasses = getExtrasFromModel(model)

        if(extrasForClasses.isNotEmpty()) {
            createPackage(packpage)
            generateImports()

            val importParceler = extrasForClasses.values.any { extrasGrouped -> extrasGrouped.any { it.isParcelableWithParceler() } }

            if(importParceler) {
                writter.writeImport("import org.parceler.Parcels")
            }

            generateClass()

            for (extrasForClass in extrasForClasses) {
                generateBody(targetClass = extrasForClass.key,
                        withExtras = extrasForClass.value)
            }

            closeClass()
            writter.generateFile(processingEnv, packpage, CLASS_NAME)
        }
    }

    internal abstract fun generateBody(targetClass: String, withExtras: List<ExtraProperty>)
    abstract fun getExtrasFromModel(model: Model) : Map<String, List<ExtraProperty>>

}


