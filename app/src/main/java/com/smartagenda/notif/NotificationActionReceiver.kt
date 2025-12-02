package com.smartagenda.notif

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationActionReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "SNOOZE" -> {
                Toast.makeText(context, "Rappel dans 10 minutes", Toast.LENGTH_SHORT).show()
                // TODO: Programmer un rappel dans 10 minutes
            }
            "MARK_DONE" -> {
                Toast.makeText(context, "Marqué comme terminé", Toast.LENGTH_SHORT).show()
                // TODO: Marquer comme terminé
            }
        }
    }
}
