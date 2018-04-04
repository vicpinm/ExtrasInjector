package com.vicpin.extrasinjector.sample

import android.os.Parcelable
import com.vicpin.extrasprocessor.annotations.ForFragment
import com.vicpin.extrasprocessor.annotations.InjectExtra
import org.parceler.Parcel
import java.io.Serializable

/**
 * Created by victor on 25/1/18.
 */

@ForFragment(BlankFragment::class)
class MainPresenter {

    @InjectExtra(optional = true)
    var valueNullable: String? = null

    var e = 3
    var e3 = 3

    @InjectExtra(optional = true)
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

    @InjectExtra lateinit var list: ArrayList<MyParcelable>


}


class MySerializable : Serializable {

}

class MyParcelable2() : Parcelable {

    constructor(parcel: android.os.Parcel) : this() {
    }

    override fun writeToParcel(p0: android.os.Parcel?, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<MyParcelable2> {
        override fun createFromParcel(parcel: android.os.Parcel): MyParcelable2 {
            return MyParcelable2(parcel)
        }

        override fun newArray(size: Int): Array<MyParcelable2?> {
            return arrayOfNulls(size)
        }
    }
}

@Parcel
class MyParcelable()  {

}


