package com.vicpin.extrasinjector.sample

import com.vicpin.extrasprocessor.annotations.ForActivity
import com.vicpin.extrasprocessor.annotations.InjectExtra
import org.parceler.Parcel
import java.io.Serializable

/**
 * Created by victor on 25/1/18.
 */

@ForActivity(MainActivity::class)
class MainPresenter {


    @InjectExtra
    lateinit var value2: String

    @InjectExtra(optional = true)
    var valueNullable: String? = null

    var e = 3
    var e3 = 3

    @InjectExtra
    var value3aa : Boolean = false

    @InjectExtra
    var value4 = 0.0

    @InjectExtra
    var value6 = 0

    @InjectExtra
    var value5 = 0.0


    @InjectExtra
    lateinit var value9: MySerializable

    @InjectExtra
    lateinit var value10: MyParcelable


}


class MySerializable : Serializable {

}

@Parcel
class MyParcelable()  {

}


