package com.vicpin.extrasinjector.processor.util

/**
 * Created by victor on 10/12/17.
 */
fun String.lastSegmentFrom(delimiter: String): String {
    return if (endsWith(delimiter)) {
        ""
    } else if (contains(".")) {
        substring(lastIndexOf(".") + 1, length)
    } else {
        return this
    }
}