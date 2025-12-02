package com.smartagenda.notif

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartagenda.notif.models.SmartAgendaEvent

class NotificationAdapter(
    private val notifications: MutableList<SmartAgendaEvent>
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(android.R.id.text1)
        val messageText: TextView = view.findViewById(android.R.id.text2)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notifications[position]
        holder.titleText.text = notification.title
        holder.messageText.text = notification.message
    }
    
    override fun getItemCount() = notifications.size
    
    fun addNotification(notification: SmartAgendaEvent) {
        notifications.add(0, notification)
        notifyItemInserted(0)
    }
    
    fun clear() {
        notifications.clear()
        notifyDataSetChanged()
    }
}
