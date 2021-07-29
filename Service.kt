package com.example.sehat

import android.app.Notification
import android.content.Intent

open class Service {
    fun startForeground(notificationId: Int, notification: Notification) {

    }

    open fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        TODO("Not yet implemented")
    }
}