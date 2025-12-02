package com.smartagenda.notif

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class SettingsActivity : AppCompatActivity() {
    
    private lateinit var preferencesManager: PreferencesManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Paramètres"
        
        preferencesManager = PreferencesManager(this)
        
        // Charger les valeurs actuelles
        findViewById<TextInputEditText>(R.id.editServerUrl)?.setText(
            preferencesManager.getServerUrl()
        )
        
        findViewById<MaterialButton>(R.id.btnSaveSettings)?.setOnClickListener {
            saveSettings()
        }
    }
    
    private fun saveSettings() {
        val url = findViewById<TextInputEditText>(R.id.editServerUrl)?.text.toString()
        
        if (url.isNotEmpty()) {
            preferencesManager.setServerUrl(url)
            ApiClient.resetRetrofit()
            Toast.makeText(this, "Paramètres enregistrés", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "URL invalide", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
