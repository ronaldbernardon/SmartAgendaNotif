package com.smartagenda.notif

/**
 * Configuration de l'API SmartAgenda
 */
object ApiConfig {
    // URL par défaut - À modifier selon votre serveur
    const val DEFAULT_SERVER_URL = "http://192.168.1.2:8086"
    
    // Endpoints
    const val ENDPOINT_NOTIFICATIONS_PENDING = "/api/notifications/pending"
    const val ENDPOINT_NOTIFICATIONS_PUSH = "/api/notifications/push"
    
    // Paramètres de polling
    const val DEFAULT_POLL_INTERVAL_SECONDS = 60L
    const val MIN_POLL_INTERVAL_SECONDS = 30L
    const val MAX_POLL_INTERVAL_SECONDS = 300L
    
    // Mode nuit
    const val NIGHT_MODE_START_HOUR = 22  // 22h
    const val NIGHT_MODE_END_HOUR = 7     // 7h
    
    // Timeouts réseau
    const val CONNECT_TIMEOUT_SECONDS = 10L
    const val READ_TIMEOUT_SECONDS = 10L
    const val WRITE_TIMEOUT_SECONDS = 10L
}
