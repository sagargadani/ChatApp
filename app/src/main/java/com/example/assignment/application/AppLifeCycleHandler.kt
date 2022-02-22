package com.example.assignment.application

import com.example.assignment.application.AppLifeCycleHandler.AppLifeCycleCallback
import android.app.Application.ActivityLifecycleCallbacks
import android.content.ComponentCallbacks2
import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle


class AppLifeCycleHandler(var appLifeCycleCallback: AppLifeCycleCallback) :
    ActivityLifecycleCallbacks, ComponentCallbacks2 {
    var appInForeground = false
    override fun onActivityResumed(activity: Activity) {
        if (!appInForeground) {
            appInForeground = true
            appLifeCycleCallback.onAppForeground()
        }
    }

    override fun onTrimMemory(i: Int) {
        if (i == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            appInForeground = false
            appLifeCycleCallback.onAppBackground()
        }
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        appLifeCycleCallback.onActivityCreated(activity, bundle)
    }

    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {
        appLifeCycleCallback.onActivityStopped(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
    override fun onConfigurationChanged(configuration: Configuration) {}
    override fun onLowMemory() {}
    interface AppLifeCycleCallback {
        fun onAppBackground()
        fun onAppForeground()
        fun onActivityCreated(activity: Activity?, bundle: Bundle?)
        fun onActivityStopped(activity: Activity?)
    }
}