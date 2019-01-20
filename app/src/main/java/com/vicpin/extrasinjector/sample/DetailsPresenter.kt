package com.vicpin.extrasinjector.sample

import com.vicpin.extrasprocessor.annotations.ForActivity
import com.vicpin.extrasprocessor.annotations.InjectExtra

/**
 * Created by victor on 25/1/18.
 */
@ForActivity(DetailsActivity::class)
class DetailsPresenter(view: DetailsPresenter.View): Presenter<DetailsPresenter.View>(view) {

    @InjectExtra(optional = true)
    var textOptional: String? = null

    @InjectExtra
    var booleanRequired : Boolean = false

    @InjectExtra
    var doubleRequired = 0.0

    override fun onStart() {
        view?.apply {
            setTextValue(textOptional)
            setBooleanValue(booleanRequired)
            setDoubleValue(doubleRequired)
        }
    }

    interface View {
        fun setTextValue(text: String?)
        fun setBooleanValue(value: Boolean)
        fun setDoubleValue(value: Double)
    }


}



