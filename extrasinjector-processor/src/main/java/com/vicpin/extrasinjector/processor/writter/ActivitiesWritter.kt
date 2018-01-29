package com.vicpin.extrasinjector.processor.writter

import com.vicpin.butcherknife.annotation.processor.entity.ExtraProperty

/**
 * Created by victor on 10/12/17.
 */
class ActivitiesWritter : Writter() {

    override var CLASS_NAME = "Activities"

    override fun createPackage(packpage: String) {
        writter.setPackage(packpage)
        generateImports()
        generateClass()
    }

    override fun generateImports() {
        writter.writeImport("import android.content.Context")
        writter.writeImport("import android.content.Intent")
    }

    override fun generateClass() {
        writter.openClass("object $CLASS_NAME")
    }

    override fun generateBody(targetClass: String, withExtras: List<ExtraProperty>) {
        writter.apply {
            val params = withExtras.joinToString { "${it.name}: ${it.getExtraClass()}" }

            openMethod("fun intentFor$targetClass(context: Context, $params) : Intent")
            methodBody("val intent = Intent(context, $targetClass::class.java)")
            for(extra in withExtras) {
                methodBody("intent.putExtra(\"${extra.name}\",${extra.name})")
            }
            methodBody("return intent")
            closeMethod()
        }
    }


}
