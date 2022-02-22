package com.example.assignment.application

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication


@SuppressLint("CommitPrefEdits")
class AppConfig : MultiDexApplication(), AppLifeCycleHandler.AppLifeCycleCallback {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        //        sharedPreferencesEditor.putString(AppConstants.SharedPreferenceKeys.ONE_SIGNAL_PUSH_PLAYER_ID, OneSignalPlayerId);
        sharedPreferencesEditor = sharedPreferences?.edit()
        context = getApplicationContext()
        val appLifeCycleHandler = AppLifeCycleHandler(this)
        registerActivityLifecycleCallbacks(appLifeCycleHandler)
        registerComponentCallbacks(appLifeCycleHandler)

    }

    protected override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onAppBackground() {}
    override fun onAppForeground() {}
    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        currentActivity = activity

    }

    override fun onActivityStopped(activity: Activity?) {}
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }


    companion object {
        private var appInstance: AppConfig? = null
        private var sharedPreferences: SharedPreferences? = null
        private var sharedPreferencesEditor: SharedPreferences.Editor? = null
        var context: Context? = null
        var currentActivity: Activity? = null
        fun getAppInstance(): AppConfig? {
            checkNotNull(appInstance) { "The application is not created yet!" }
            return appInstance
        }

        val applicationPreferenceEditor: SharedPreferences.Editor?
            get() = sharedPreferencesEditor
        val applicationPreference: SharedPreferences?
            get() = sharedPreferences

        fun isTablet(context: Context?): Boolean {
            return ((context!!.resources.configuration.screenLayout
                    and Configuration.SCREENLAYOUT_SIZE_MASK)
                    >= Configuration.SCREENLAYOUT_SIZE_LARGE)
        }

    }
}