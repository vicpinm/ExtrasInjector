package com.vicpin.extrasinjector.sample

import com.vicpin.extrasprocessor.annotations.ForFragment
import com.vicpin.extrasprocessor.annotations.InjectExtra

/**
 * Created by victor on 25/1/18.
 */

@ForFragment(fragmentClass = BlankFragment::class)
class MainPresenter2 {

    @InjectExtra
    lateinit var value2: String

    @InjectExtra
    lateinit var value32: String

    @InjectExtra
    var value32d: Float = 0f

    var e = 3
    var e3 = 3

    @InjectExtra
    var value3 = true

    @InjectExtra
    lateinit var value9: MySerializable

    @InjectExtra
    lateinit var value10: MyParcelable


}
