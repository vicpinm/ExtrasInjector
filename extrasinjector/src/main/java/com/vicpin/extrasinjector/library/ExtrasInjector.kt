package com.vicpin.extrasinjector.library

import android.app.Activity
import android.support.v4.app.Fragment

/**
 * Created by Victor on 04/04/2018.
 */
object ExtrasInjector {

    val FACTORY_CLASS = "com.vicpin.extrasinjector.ExtrasInjectorFactory"

    fun bind(act: Fragment, target: Any) {
        try {
            val clazz = Class.forName(FACTORY_CLASS)
            val method = clazz.getMethod("bind", Fragment::class.java, Any::class.java)
            try {
                method.invoke(null, act, target)
            } catch (ex: IllegalArgumentException) {
                throw ex
            }
        } catch (ex: ClassNotFoundException) {
            throw IllegalStateException("Either kapt has not been executed or it has been executed but the annotation processor has not found any class annotated with @ForFragment")
        } catch (ex: Exception) {
            throw ex
        }
    }

    fun bind(act: Activity, target: Any) {
        try {
            val clazz = Class.forName(FACTORY_CLASS)
            val method = clazz.getMethod("bind", Fragment::class.java, Any::class.java)
            try {
                method.invoke(null, act, target)
            } catch (ex: IllegalArgumentException) {
                throw ex
            }
        } catch (ex: ClassNotFoundException) {
            throw IllegalStateException("Either kapt has not been executed or it has been executed but the annotation processor has not found any class annotated with @ForActivity")
        } catch (ex: Exception) {
            throw ex
        }
    }
}