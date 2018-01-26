package com.vicpin.extrasinjector.processor

/**
 * Created by Oesia on 26/01/2018.
 */
class Writter() {

    var text = ""

    fun newLine(line: String = "", level: Int = 0, newLine: Boolean = false) {
        var indentation = ""
        var semicolon = if (!line.isEmpty() && !line.endsWith("}") && !line.endsWith("{")) "" else ""

        (1..level).forEach { indentation += "\t" }

        text += if (newLine) {
            "$indentation$line$semicolon\n\n"
        } else {
            "$indentation$line$semicolon\n"
        }
    }

    fun setPackage(packpage: String) {
        text = "package $packpage\n$text"
    }

    fun writeImport(line: String) {
        newLine(line)
    }

    fun openClass(line: String) {
        newLine()
        newLine(line + " {", newLine = true)
    }

    fun closeClass() {
        newLine("}")
    }

    fun openMethod(line: String) {
        newLine(line + " {", level = 1)
    }

    fun closeMethod() {
        newLine("}", level = 1, newLine = true)
    }


    fun methodBody(line: String, indentationLevel: Int = 0) {
        newLine(line, level = indentationLevel + 2)
    }



}