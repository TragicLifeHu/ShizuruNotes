package com.github.nyanfantasia.shizurunotes.common

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.github.nyanfantasia.shizurunotes.db.DBHelper
import com.github.nyanfantasia.shizurunotes.user.UserSettings
import com.github.nyanfantasia.shizurunotes.utils.Utils

class App : Application() {
    companion object {
        lateinit var localeManager: LocaleManager
        lateinit var instance: App private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        createNotificationChannel()
        initSingleton()
    }

    override fun attachBaseContext(base: Context) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(localeManager.setLocale(base))
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val channelDefault = NotificationChannel(Statics.NOTIFICATION_CHANNEL_DEFAULT, "important", NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "important notification"
        }
        val channelLow = NotificationChannel(Statics.NOTIFICATION_CHANNEL_LOW, "regular", NotificationManager.IMPORTANCE_LOW).apply {
            description = "regular notification"
        }
        // Register the channel with the system
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channelDefault)
        notificationManager.createNotificationChannel(channelLow)
    }

    private fun initSingleton() {
        Utils.app = this
        UserSettings.with(this)
        initUserServer()
        DBHelper.with(this)
        ResourceManager.with(this)
        I18N.application = this
        AppNotificationManager.instance
    }

    private fun initUserServer() {
        when (UserSettings.get().preference.getString(UserSettings.SERVER_KEY, "jp")) {
            "jp" -> {
                Statics.DB_FILE_NAME = Statics.DB_FILE_NAME_JP
                Statics.DB_FILE_NAME_COMPRESSED = Statics.DB_FILE_NAME_COMPRESSED_JP
                Statics.LATEST_VERSION_URL = Statics.LATEST_VERSION_URL_JP
                Statics.DB_FILE_URL = Statics.DB_FILE_URL_JP
            }
            "tw" -> {
                Statics.DB_FILE_NAME = Statics.DB_FILE_NAME_TW
                Statics.DB_FILE_NAME_COMPRESSED = Statics.DB_FILE_NAME_COMPRESSED_TW
                Statics.LATEST_VERSION_URL = Statics.LATEST_VERSION_URL_TW
                Statics.DB_FILE_URL = Statics.DB_FILE_URL_TW
            }
            "cn" -> {
                Statics.DB_FILE_NAME = Statics.DB_FILE_NAME_CN
                Statics.DB_FILE_NAME_COMPRESSED = Statics.DB_FILE_NAME_COMPRESSED_CN
                Statics.LATEST_VERSION_URL = Statics.LATEST_VERSION_URL_CN
                Statics.DB_FILE_URL = Statics.DB_FILE_URL_CN
            }
        }
    }
}