package com.smartagenda.notif

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class FullScreenNotificationActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_notification)
        
        findViewById<MaterialButton>(R.id.btnDismiss)?.setOnClickListener {
            finish()
        }
    }
}
