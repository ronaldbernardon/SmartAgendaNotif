package com.smartagenda.notif.models

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("count")
    val count: Int,
    
    @SerializedName("notifications")
    val notifications: List<SmartAgendaEvent>
)
