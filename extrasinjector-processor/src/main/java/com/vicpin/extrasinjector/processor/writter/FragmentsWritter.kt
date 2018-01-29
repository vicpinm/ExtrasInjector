package com.vicpin.extrasinjector.processor.writter

import com.vicpin.butcherknife.annotation.processor.entity.ExtraProperty

/**
 * Created by victor on 10/12/17.
 */
class FragmentsWritter : Writter() {

    override var CLASS_NAME = "Fragments"

    override fun createPackage(packpage: String) {
        writter.setPackage(packpage)
        generateImports()
        generateClass()
    }

    override fun generateImports() {
        writter.writeImport("import android.content.Intent")
        writter.writeImport("import android.support.v4.app.Fragment")
        writter.writeImport("import android.os.Bundle")
    }

    override fun generateClass() {
        writter.openClass("object ${CLASS_NAME}")
    }

    override fun generateBody(targetClass: String, withExtras: List<ExtraProperty>) {
        writter.apply {
            val params = withExtras.joinToString { "${it.name}: ${it.getExtraClass()}" }

            openMethod("fun create$targetClass($params) : Fragment")
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

}
