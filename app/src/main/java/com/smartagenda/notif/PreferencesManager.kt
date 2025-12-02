package com.smartagenda.notif

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "smartagenda_prefs",
        Context.MODE_PRIVATE
    )
    
    companion object {
        private const val KEY_SERVER_URL = "server_url"
        private const val KEY_POLL_INTERVAL = "poll_interval"
        private const val KEY_AUTO_START = "auto_start"
        private const val KEY_NIGHT_MODE = "night_mode"
    }
    
    fun getServerUrl(): String {
        return prefs.getString(KEY_SERVER_URL, ApiConfig.DEFAULT_SERVER_URL) 
            ?: ApiConfig.DEFAULT_SERVER_URL
    }
    
    fun setServerUrl(url: String) {
        prefs.edit().putString(KEY_SERVER_URL, url).apply()
    }
    
    fun getPollInterval(): Long {
        return prefs.getLong(KEY_POLL_INTERVAL, ApiConfig.DEFAULT_POLL_INTERVAL_SECONDS)
    }
    
    fun setPollInterval(seconds: Long) {
        prefs.edit().putLong(KEY_POLL_INTERVAL, seconds).apply()
    }
    
    fun isAutoStartEnabled(): Boolean {
        return prefs.getBoolean(KEY_AUTO_START, true)
    }
    
    fun setAutoStart(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_AUTO_START, enabled).apply()
    }
    
    fun isNightModeEnabled(): Boolean {
        return prefs.getBoolean(KEY_NIGHT_MODE, true)
    }
    
    fun setNightMode(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_NIGHT_MODE, enabled).apply()
    }
}
