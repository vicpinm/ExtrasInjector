package com.vicpin.extrasinjector.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : ParentActivity(), DetailsPresenter.View {

    val mPresenter by lazy { DetailsPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        back.setOnClickListener { finish() }
    }

    override fun getPresenter(): Presenter<DetailsPresenter.View> {
        return mPresenter
    }

    override fun setTextValue(text: String?) {
        stringValue.setText(text)
    }

    override fun setBooleanValue(value: Boolean) {
        booleanValue.setText(value.toString())
    }

    override fun setDoubleValue(value: Double) {
        decimalValue.setText(value.toString())
    }
}
