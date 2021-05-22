package com.local.app.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast

class ContactsManager {

    companion object {
        const val WHATSUP_PACKAGE = "com.whatsapp"
        const val INSTAGRAM_PACKAGE = "com.instagram.android"
        const val TELEGRAM_PACKAGE = "org.telegram.messenger"
    }

    fun callPhone(phone: String, activity: Activity) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        activity.startActivity(intent)
    }

    fun openTelegram(phone: String, activity: Activity) {
        if (isAppInstalled(TELEGRAM_PACKAGE, activity)) {
            val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/qa_ti"))
            telegram.setPackage(TELEGRAM_PACKAGE)
            activity.startActivity(telegram)
        } else {
            Toast
                .makeText(activity, "Instagram не установлен", Toast.LENGTH_SHORT)
                .show();
            val uri = Uri.parse("market://details?id=$TELEGRAM_PACKAGE");
            val goToMarket = Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(goToMarket);
        }
    }

    fun openInstagram(instagramAccount: String, activity: Activity) {
        if (isAppInstalled(INSTAGRAM_PACKAGE, activity)) {
            val uri = Uri.parse("https://www.instagram.com/sharfloveu/")
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(uri)
            intent.setPackage(INSTAGRAM_PACKAGE);
            activity.startActivity(intent);
        } else {
            Toast
                .makeText(activity, "Instagram не установлен", Toast.LENGTH_SHORT)
                .show();
            val uri = Uri.parse("market://details?id=com.instagram.android");
            val goToMarket = Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(goToMarket);
        }
    }

    fun openWhatsApp(phone: String, activity: Activity) {
        val isWhatsappInstalled = isAppInstalled(WHATSUP_PACKAGE, activity);
        if (isWhatsappInstalled) {
            val uri = Uri.parse("smsto:" + phone)
            val sendIntent = Intent(Intent.ACTION_SENDTO, uri);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Здравствуйте, я из приложения Local");
            sendIntent.setPackage(WHATSUP_PACKAGE);
            activity.startActivity(sendIntent);
        } else {
            Toast
                .makeText(activity, "WhatsApp не установлен", Toast.LENGTH_SHORT)
                .show();
            val uri = Uri.parse("market://details?id=com.whatsapp");
            val goToMarket = Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(goToMarket);
        }
    }

    private fun isAppInstalled(uri: String, activity: Activity): Boolean {
        val pm: PackageManager = activity.packageManager
        var app_installed = false
        app_installed = try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
    }
}