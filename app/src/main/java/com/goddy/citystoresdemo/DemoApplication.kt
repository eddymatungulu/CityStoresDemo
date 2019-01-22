package com.goddy.citystoresdemo

import android.app.Activity
import android.app.Application
import com.goddy.citystoresdemo.di.Injector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class DemoApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Injector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}