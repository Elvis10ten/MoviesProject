package com.mobymagic.moviesproject.utils

import android.support.test.espresso.IdlingResource

object EspressoIdlingResource {

    private val RESOURCE = "GLOBAL"

    private val countingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

    fun getIdlingResource() = countingIdlingResource

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }
}