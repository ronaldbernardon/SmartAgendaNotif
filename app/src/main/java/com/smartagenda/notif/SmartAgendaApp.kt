package com.smartagenda.notif

import android.app.Application

class SmartAgendaApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialiser ApiClient avec le contexte de l'application
        ApiClient.init(this)
    }
}
