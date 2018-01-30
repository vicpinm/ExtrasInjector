package com.vicpin.extrasinjector.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var a=MainPresenter2()

        ExtrasInjector.bind(this, a)

        var s = 3;

    }



}
