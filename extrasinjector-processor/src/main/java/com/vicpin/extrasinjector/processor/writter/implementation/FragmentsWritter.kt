package com.vicpin.extrasinjector.processor.writter.implementation

import com.vicpin.butcherknife.annotation.processor.entity.ExtraProperty
import com.vicpin.extrasinjector.processor.model.Model
import com.vicpin.extrasinjector.processor.util.lastSegmentFrom
import com.vicpin.extrasinjector.processor.writter.abstraction.ExtrasWritter

/**
 * Created by victor on 10/12/17.
 */
class FragmentsWritter : ExtrasWritter() {

    override var CLASS_NAME = "Fragments"

    override fun createPackage(packpage: String) {
        writter.setPackage(packpage)
    }

    override fun generateImports() {
        writter.writeImport("import android.content.Intent")
        writter.writeImport("import androidx.fragment.app.Fragment")
        writter.writeImport("import android.os.Bundle")
    }

    override fun generateClass() {
        writter.openClass("object ${CLASS_NAME}")
    }

    override fun generateBody(targetClass: String, withExtras: List<ExtraProperty>) {
        writter.apply {
            val params = withExtras.joinToString { "${it.name}: ${it.getExtraClass()}" }

            openMethod("fun create${targetClass.lastSegmentFrom(".")}($params) : Fragment")
            methodBody("val fragment = $targetClass()")
            methodBody("val args = Bundle()")
            for(extra in withExtras) {
                methodBody(extra.getArgumentsPutMethod(argumentsVariable = "args"))
            }
            methodBody("fragment.arguments = args")
            methodBody("return fragment")
            closeMethod()
        }
    }

    override fun getExtrasFromModel(model: Model) = model.getExtrasForFragments()


}
