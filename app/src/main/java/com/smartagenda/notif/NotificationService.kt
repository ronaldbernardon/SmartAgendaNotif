package com.smartagenda.notif

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import java.util.*

class NotificationService : Service() {
    
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var notificationHelper: NotificationHelper
    
    companion object {
        private const val NOTIFICATION_ID = 1000
        private const val CHANNEL_ID = "smartagenda_service"
    }
    
    override fun onCreate() {
        super.onCreate()
        preferencesManager = PreferencesManager(this)
        notificationHelper = NotificationHelper(this)
        
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createForegroundNotification())
        
        // Démarrer le polling
        startPolling()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Service SmartAgenda",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Service de synchronisation des notifications"
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createForegroundNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("SmartAgenda Notif")
            .setContentText("Service actif - En attente de notifications")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
    
    private fun startPolling() {
        scope.launch {
            while (isActive) {
                if (shouldCheckNotifications()) {
                    checkForNotifications()
                }
                
                val interval = preferencesManager.getPollInterval() * 1000L
                delay(interval)
            }
        }
    }
    
    private fun shouldCheckNotifications(): Boolean {
        if (!preferencesManager.isNightModeEnabled()) {
            return true
        }
        
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        
        // Ne pas vérifier entre 22h et 7h
        return hour in ApiConfig.NIGHT_MODE_END_HOUR until ApiConfig.NIGHT_MODE_START_HOUR
    }
    
    private suspend fun checkForNotifications() {
        try {
            val response = ApiClient.apiService.getPendingNotifications()
            
            if (response.success && response.notifications.isNotEmpty()) {
                for (notification in response.notifications) {
                    notificationHelper.showNotification(notification)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("NotificationService", "Erreur polling: ${e.message}")
        }
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
