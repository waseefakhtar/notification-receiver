package com.waseefakhtar.notificationreceiver

import android.os.Parcel
import android.os.Parcelable

data class NotificationInfo constructor(var title: String, var message: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString() ?: "") {
        readFromParcel(parcel)
    }

    fun readFromParcel(parcel: Parcel) {
        title = parcel.readString() ?: ""
        message = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NotificationInfo> {
        override fun createFromParcel(parcel: Parcel): NotificationInfo {
            return NotificationInfo(parcel)
        }

        override fun newArray(size: Int): Array<NotificationInfo?> {
            return arrayOfNulls(size)
        }
    }
}