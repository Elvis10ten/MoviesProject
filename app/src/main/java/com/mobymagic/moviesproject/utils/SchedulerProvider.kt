package com.mobymagic.moviesproject.utils

import io.reactivex.schedulers.Schedulers
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

object SchedulerProvider {

    fun uiProduction(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    fun uiTest(): Scheduler {
        return Schedulers.trampoline()
    }

}