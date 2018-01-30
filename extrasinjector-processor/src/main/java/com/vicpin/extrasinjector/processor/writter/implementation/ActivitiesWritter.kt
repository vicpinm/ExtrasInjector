package com.vicpin.extrasinjector.processor.writter.implementation

import com.vicpin.butcherknife.annotation.processor.entity.ExtraProperty
import com.vicpin.extrasinjector.processor.model.Model
import com.vicpin.extrasinjector.processor.util.lastSegmentFrom
import com.vicpin.extrasinjector.processor.writter.abstraction.ExtrasWritter

/**
 * Created by victor on 10/12/17.
 */
class ActivitiesWritter : ExtrasWritter() {

    override var CLASS_NAME = "Activities"

    override fun createPackage(packpage: String) {
        writter.setPackage(packpage)
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

            openMethod("fun intentFor${targetClass.lastSegmentFrom(".")}(context: Context, $params) : Intent")
            methodBody("val intent = Intent(context, $targetClass::class.java)")
            for(extra in withExtras) {
                methodBody(extra.getIntentPutMethod("intent"))
            }
            methodBody("return intent")
            closeMethod()
        }
    }

    override fun getExtrasFromModel(model: Model) = model.getExtrasForActivities()

}
