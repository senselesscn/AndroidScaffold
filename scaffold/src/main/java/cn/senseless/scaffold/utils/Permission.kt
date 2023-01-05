@file:JvmName("PermissionUtils")

package cn.senseless.scaffold.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.isGrantedAllPermissions(vararg permissions: String): Boolean {
    return permissions.all { isGrantedPermission(it) }
}

fun Context.isGrantedPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}