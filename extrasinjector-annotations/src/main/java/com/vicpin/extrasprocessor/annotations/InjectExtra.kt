package com.vicpin.extrasprocessor.annotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by victor on 25/1/18.
 */

@Retention(RetentionPolicy.CLASS)
@Target(AnnotationTarget.FIELD)
annotation class InjectExtra