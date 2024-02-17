package com.world.test.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.world.test.common.extension.SingleLiveEvent

object LifecycleUtils {
    var created = SingleLiveEvent<Unit>()
    var started = SingleLiveEvent<Unit>()
    var resumed = SingleLiveEvent<Unit>()
    var paused = SingleLiveEvent<Unit>()
    var stopped = SingleLiveEvent<Unit>()
    var saveInstanceState = SingleLiveEvent<Unit>()
    var predestroyed = SingleLiveEvent<Unit>()
    var destroyed = SingleLiveEvent<Unit>()

    private val listener = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            created.postValue(Unit)
        }
        override fun onActivityStarted(activity: Activity) {
            started.postValue(Unit)
        }
        override fun onActivityResumed(activity: Activity) {
            resumed.postValue(Unit)
        }

        override fun onActivityPaused(activity: Activity) {
            paused.postValue(Unit)
        }

        override fun onActivityStopped(activity: Activity) {
            stopped.postValue(Unit)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            saveInstanceState.postValue(Unit)
        }

        override fun onActivityPreDestroyed(activity: Activity) {
            predestroyed.postValue(Unit)
            super.onActivityPreDestroyed(activity)
        }

        override fun onActivityDestroyed(activity: Activity) {
            destroyed.postValue(Unit)
        }
    }

    fun initListener(application: Application) {
        application.registerActivityLifecycleCallbacks(listener)
    }
}