package com.thurainx.cointrack

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import kotlin.random.Random

@HiltAndroidApp
class CoinTrackApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "noti_channel",
                "Noti Channel",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}