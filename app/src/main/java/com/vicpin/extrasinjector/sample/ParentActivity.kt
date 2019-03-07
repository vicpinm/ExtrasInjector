package com.vicpin.extrasinjector.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vicpin.extrasinjector.library.ExtrasInjector

abstract class ParentActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        ExtrasInjector.bind(this, getPresenter())
        getPresenter().onStart()
    }

        abstract fun getPresenter(): Presenter<Any>
}