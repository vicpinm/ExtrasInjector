package com.vicpin.extrasinjector.processor

/**
 * Created by Oesia on 26/01/2018.
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