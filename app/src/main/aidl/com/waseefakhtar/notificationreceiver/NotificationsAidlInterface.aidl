// NotificationsAidlInterface.aidl
package com.waseefakhtar.notificationreceiver;

import com.waseefakhtar.notificationreceiver.NotificationInfo;


interface NotificationsAidlInterface {

    List<NotificationInfo> getNotificationInfoList();

    void addInfo(inout NotificationInfo notificationInfo);
}