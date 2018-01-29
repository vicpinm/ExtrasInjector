package com.vicpin.extrasprocessor.annotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

/**
 * Created by victor on 25/1/18.
 */

@Retention(RetentionPolicy.CLASS)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class ForActivity(val activityClass: KClass<*>)