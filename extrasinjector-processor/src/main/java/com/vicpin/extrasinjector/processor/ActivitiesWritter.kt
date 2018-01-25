package com.vicpin.extrasinjector.processor

import com.vicpin.butcherknife.annotation.processor.entity.EntityProperty

/**
 * Created by victor on 10/12/17.
 */
class ActivitiesWritter(private val activityClass: String, entities: List<EntityProperty>) {

    private var text: String = ""

    init {
    }

    private fun generateClass(): String {




        return text
    }



    private fun appendClassEnding() {
        newLine("}")
    }



    fun newLine(line: String = "", level: Int = 0, newLine: Boolean = false) {
        var indentation = ""
        var semicolon = if (!line.isEmpty() && !line.endsWith("}") && !line.endsWith("{")) ";" else ""

        (1..level).forEach { indentation += "\t" }

        text += if (newLine) {
            "$indentation$line$semicolon\n\n"
        } else {
            "$indentation$line$semicolon\n"
        }
    }

    companion object {

        private val CLASSNAME_SUFIX = "Binding"
    }
}
