package com.smartagenda.notif

import com.smartagenda.notif.models.NotificationResponse
import retrofit2.http.GET

interface ApiService {
    
    @GET(ApiConfig.ENDPOINT_NOTIFICATIONS_PENDING)
    suspend fun getPendingNotifications(): NotificationResponse
}
