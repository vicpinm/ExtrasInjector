package com.vicpin.extrasinjector.sample

abstract  class Presenter<out T: Any>(val view: T?) {

    abstract fun onStart()
}