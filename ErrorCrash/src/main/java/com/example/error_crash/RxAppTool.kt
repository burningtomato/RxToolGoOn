package com.example.error_crash

import android.content.Context
import android.content.pm.PackageManager
import com.example.error_crash.TCrashTool.isNullString

object RxAppTool {
    /**
     * 获取App版本码
     *
     * @param context 上下文
     * @return App版本码
     */
    @JvmStatic
    fun getAppVersionCode(context: Context): Int {
        return getAppVersionCode(context, context.packageName)
    }

    /**
     * 获取App版本码
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App版本码
     */
    @JvmStatic
    fun getAppVersionCode(context: Context, packageName: String?): Int {
        return if (TCrashTool.isNullString(packageName)) -1 else try {
            val pm = context.packageManager
            val pi = packageName?.let { pm.getPackageInfo(it, 0) }
            pi?.versionCode ?: -1
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            -1
        }
    }


    /**
     * 获取App版本号
     *
     * @param context 上下文
     * @return App版本号
     */
    @JvmStatic
    fun getAppVersionName(context: Context): String? {
        return getAppVersionName(context, context.packageName)
    }

    /**
     * 获取App版本号
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App版本号
     */
    @JvmStatic
    fun getAppVersionName(context: Context, packageName: String?): String? {
        return if (isNullString(packageName)) null else try {
            val pm = context.packageManager
            val pi = packageName?.let { pm.getPackageInfo(it, 0) }
            pi?.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }
}