package com.mobymagic.moviesproject

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import android.os.StrictMode
import timber.log.Timber

class MoviesApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // App init shouldn't be done in this process.
            return
        }

        LeakCanary.install(this)
        initStrictModeInDebug()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {

                override fun createStackElementTag(element: StackTraceElement): String? {
                    // Add the line number to the tag
                    return super.createStackElementTag(element) + ":" + element.lineNumber
                }

            })
        } else {
            // TODO: Create a release tree to log errors with crash reporting tool
            // Timber.plant(new ReleaseTree());
        }
    }

    private fun initStrictModeInDebug() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
        }
    }

}