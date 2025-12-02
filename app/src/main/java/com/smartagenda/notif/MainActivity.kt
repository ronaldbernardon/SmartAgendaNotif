package com.smartagenda.notif

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var notificationAdapter: NotificationAdapter
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        preferencesManager = PreferencesManager(this)
        
        // Demander les permissions nécessaires
        requestNotificationPermission()
        
        // Setup RecyclerView
        setupRecyclerView()
        
        // Boutons
        findViewById<MaterialButton>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        
        findViewById<MaterialButton>(R.id.btnStartService).setOnClickListener {
            startNotificationService()
        }
        
        findViewById<MaterialButton>(R.id.btnStopService).setOnClickListener {
            stopNotificationService()
        }
        
        // FAB test connexion
        findViewById<FloatingActionButton>(R.id.fabTest).setOnClickListener {
            testConnection()
        }
        
        // Démarrer le service si configuré
        if (preferencesManager.isAutoStartEnabled()) {
            startNotificationService()
        }
    }
    
    private fun setupRecyclerView() {
        notificationAdapter = NotificationAdapter(mutableListOf())
        findViewById<RecyclerView>(R.id.recyclerViewNotifications).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = notificationAdapter
        }
    }
    
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1001
            )
        }
    }
    
    private fun startNotificationService() {
        val intent = Intent(this, NotificationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        Toast.makeText(this, "Service démarré", Toast.LENGTH_SHORT).show()
    }
    
    private fun stopNotificationService() {
        val intent = Intent(this, NotificationService::class.java)
        stopService(intent)
        Toast.makeText(this, "Service arrêté", Toast.LENGTH_SHORT).show()
    }
    
    private fun testConnection() {
        scope.launch {
            try {
                val response = ApiClient.apiService.getPendingNotifications()
                if (response.success) {
                    Toast.makeText(
                        this@MainActivity,
                        "✅ Connexion OK - ${response.count} notification(s)",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "❌ Erreur connexion",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "❌ Erreur: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
