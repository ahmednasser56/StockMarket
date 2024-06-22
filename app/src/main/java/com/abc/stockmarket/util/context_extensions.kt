package com.abc.stockmarket.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.provider.Settings.Secure
import android.widget.Toast

@Suppress("DEPRECATION")
fun <T> Context.isServiceRunning(service: Class<T>): Boolean =
    (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
        .getRunningServices(Integer.MAX_VALUE)
        .any { it.service.className == service.name }

@SuppressLint("HardwareIds")
fun Context.getAndroidDeviceId(): String = Secure.getString(contentResolver, Secure.ANDROID_ID)

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Context.toast(text: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}