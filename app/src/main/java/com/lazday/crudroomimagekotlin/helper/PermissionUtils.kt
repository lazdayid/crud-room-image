package com.lazday.crudroomimagekotlin.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionUtils {
    // PERMISSION ABOUVE API 23
    companion object {
        fun writeExtStorage(context: Context, activity: Activity): Boolean{
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Ask for permision
                ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
            }
            // Permission has already been granted
            return true
        }
    }
}