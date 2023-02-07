@file:JvmName("PermissionUtils")

package cn.senseless.scaffold.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Context.isGrantedAllPermissions(vararg permissions: String): Boolean {
    return permissions.all { isGrantedPermission(it) }
}

inline fun Context.isGrantedPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

inline fun Fragment.isGrantedPermission(permission: String) = requireContext().isGrantedPermission(permission)