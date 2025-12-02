package com.smartagenda.notif.models

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class SmartAgendaEvent(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("message")
    val message: String,
    
    @SerializedName("type")
    val type: String,  // "notification" ou "reminder"
    
    @SerializedName("timestamp")
    val timestamp: String,
    
    @SerializedName("category")
    val category: String? = null,
    
    @SerializedName("description")
    val description: String? = null
) {
    fun getTimeText(): String {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val date = format.parse(timestamp)
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            "⏰ ${timeFormat.format(date ?: Date())}"
        } catch (e: Exception) {
            "⏰ Maintenant"
        }
    }
    
    val isReminder: Boolean
        get() = type == "reminder"
}
