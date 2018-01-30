package com.vicpin.extrasinjector.sample

import com.vicpin.extrasprocessor.annotations.InjectExtra

/**
 * Created by victor on 25/1/18.
 */

class MainPresenter2 {

    @InjectExtra
    lateinit var value2: String

    @InjectExtra(optional = true)
    var valueNullable2: String? = null



}
