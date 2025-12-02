package com.smartagenda.notif

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    
    private lateinit var context: Context
    private var retrofit: Retrofit? = null
    
    fun init(appContext: Context) {
        context = appContext
    }
    
    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            val preferencesManager = PreferencesManager(context)
            val baseUrl = preferencesManager.getServerUrl()
            
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(ApiConfig.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(ApiConfig.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(ApiConfig.WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build()
            
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        
        return retrofit!!
    }
    
    val apiService: ApiService
        get() = getRetrofit().create(ApiService::class.java)
    
    fun resetRetrofit() {
        retrofit = null
    }
}
