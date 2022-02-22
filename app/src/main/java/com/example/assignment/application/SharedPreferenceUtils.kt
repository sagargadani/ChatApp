package com.example.assignment.application

import com.example.assignment.application.AppConfig


object SharedPreferenceUtils {
    fun preferencePutInteger(key: String?, value: Int) {
        AppConfig.applicationPreferenceEditor?.putInt(key, value)
        AppConfig.applicationPreferenceEditor?.commit()
    }

    fun preferenceGetInteger(key: String?, defaultValue: Int): Int? {
        return AppConfig.applicationPreference?.getInt(key, defaultValue)
    }

    fun preferencePutBoolean(key: String?, value: Boolean) {
        AppConfig.applicationPreferenceEditor?.putBoolean(key, value)
        AppConfig.applicationPreferenceEditor?.commit()
    }

    fun preferenceGetBoolean(key: String?, defaultValue: Boolean): Boolean? {
        return AppConfig.applicationPreference?.getBoolean(key, defaultValue)
    }

    @JvmStatic
    fun preferencePutString(key: String?, value: String?) {
        AppConfig.applicationPreferenceEditor?.putString(key, value)
        AppConfig.applicationPreferenceEditor?.commit()
    }

    fun removeKey(key: String?) {
        AppConfig.applicationPreferenceEditor?.remove(key)
        AppConfig.applicationPreferenceEditor?.commit()
    }

    fun preferenceGetString(key: String?): String? {
        return AppConfig.applicationPreference?.getString(key, "")
    }

    fun preferenceGetString(key: String?, value: String?): String? {
        return AppConfig.applicationPreference?.getString(key, value)
    }

    fun preferencePutLong(key: String?, value: Long) {
        AppConfig.applicationPreferenceEditor?.putLong(key, value)
        AppConfig.applicationPreferenceEditor?.commit()
    }

    fun preferenceGetLong(key: String?, defaultValue: Long): Long? {
        return AppConfig.applicationPreference?.getLong(key, defaultValue)
    }

    fun preferencePutFloat(key: String?, value: Float) {
        AppConfig.applicationPreferenceEditor?.putFloat(key, value)
        AppConfig.applicationPreferenceEditor?.commit()
    }

    fun preferenceGetFloat(key: String?, defaultValue: Float): Float? {
        return AppConfig.applicationPreference?.getFloat(key, defaultValue)
    }

    fun preferenceRemoveKey(key: String?) {
        AppConfig.applicationPreferenceEditor?.remove(key)
        AppConfig.applicationPreferenceEditor?.commit()
    }

    fun clearPreference() {
        AppConfig.applicationPreferenceEditor?.clear()
        AppConfig.applicationPreferenceEditor?.commit()
    }
}