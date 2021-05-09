package com.waseefakhtar.notificationreceiver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.waseefakhtar.notificationreceiver.service.CustomNotificationListenerService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationListenerService = CustomNotificationListenerService()
        startService(Intent(this, notificationListenerService.javaClass))

        askForPermissionIfNecessary()
    }

    private fun askForPermissionIfNecessary() {
        if (!NotificationManagerCompat.getEnabledListenerPackages(this).contains(packageName)) {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }
    }
}