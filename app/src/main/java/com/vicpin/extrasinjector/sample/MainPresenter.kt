package com.vicpin.extrasinjector.sample

import android.os.Parcel
import android.os.Parcelable
import com.vicpin.extrasprocessor.annotations.ForActivity
import com.vicpin.extrasprocessor.annotations.InjectExtra
import java.io.Serializable

/**
 * Created by victor on 25/1/18.
 */

@ForActivity(activityClass = MainActivity::class)
class MainPresenter {

    @InjectExtra
    lateinit var value2: String

    @InjectExtra
    lateinit var value32: String

    var e = 3
    var e3 = 3

    @InjectExtra
    var value3 = true

    @InjectExtra
    var value4 = 0f

    @InjectExtra
    var value6 = 0

    @InjectExtra
    var value5 = 0.0

    @InjectExtra
    var value7 = 0L

    @InjectExtra
    lateinit var value9: MySerializable

    @InjectExtra
    lateinit var value10: MyParcelable


}

class MySerializable : Serializable {

}

class MyParcelable() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<MyParcelable> {
        override fun createFromParcel(parcel: Parcel): MyParcelable {
            return MyParcelable(parcel)
        }

        override fun newArray(size: Int): Array<MyParcelable?> {
            return arrayOfNulls(size)
        }
    }
}


