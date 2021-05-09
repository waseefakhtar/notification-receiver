package com.waseefakhtar.notificationreceiver.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.waseefakhtar.notificationreceiver.NotificationInfo
import com.waseefakhtar.notificationreceiver.NotificationsAidlInterface

class CustomNotificationListenerService : NotificationListenerService() {

    private var notificationsAidInterface: NotificationsAidlInterface? = null
    private var bound = false

    private var notificationInfoList: List<NotificationInfo> = listOf()

    private val serviceConnection: ServiceConnection = object: ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            notificationsAidInterface = NotificationsAidlInterface.Stub.asInterface(service)
            bound = true

            if (notificationsAidInterface != null) {
                try {
                    notificationInfoList = notificationsAidInterface?.notificationInfoList ?: listOf()
                } catch (exception: RemoteException) {
                    exception.printStackTrace()
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (!bound) {
            attemptToBindService()
        }
    }

    private fun attemptToBindService() {
        val intent = Intent()
        intent.action = "com.waseefakhtar.aidl"
        intent.setPackage("com.waseefakhtar.datareceiver")
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bound) {
            unbindService(serviceConnection)
            bound = false
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        val extras = sbn?.notification?.extras
        val title = extras?.getString("android.title") ?: ""
        val text = extras?.getCharSequence("android.text").toString()

        addNotification(title, text)
    }

    private fun addNotification(title: String, message: String) {
        val info = NotificationInfo(title, message)
        try {
            notificationsAidInterface?.addInfo(info)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }
}