package com.github.nyanfantasia.shizurunotes.common

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.ui.MainActivity
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Statics.NOTIFICATION_ACTION -> {
                var title = ""
                var text = ""
                var channelId =
                    Statics.NOTIFICATION_CHANNEL_LOW
                when (intent.extras?.getString(Statics.NOTIFICATION_EXTRA_TYPE)) {
                    Statics.NORMAL_BEFORE -> {
                        title = I18N.getString(R.string.notification_normal_before_title)
                        text = I18N.getString(R.string.notification_normal_before_text)
                    }
                    Statics.DUNGEON_BEFORE_2 -> {
                        title = I18N.getString(R.string.notification_dungeon_before_2_title)
                        text = I18N.getString(R.string.notification_dungeon_before_2_text)
                    }
                    Statics.DUNGEON_BEFORE -> {
                        title = I18N.getString(R.string.notification_dungeon_before_title)
                        text = I18N.getString(R.string.notification_dungeon_before_text)
                    }
                    Statics.HATSUNE_LAST -> {
                        title = I18N.getString(R.string.notification_hatsune_last_title)
                        text = I18N.getString(R.string.notification_hatsune_last_text)
                    }
                    Statics.HATSUNE_LAST_HOUR -> {
                        title = I18N.getString(R.string.notification_hatsune_last_hour_title)
                        text = I18N.getString(R.string.notification_hatsune_last_hour_text)
                        channelId =
                            Statics.NOTIFICATION_CHANNEL_DEFAULT
                    }
                    Statics.TOWER_LAST_HOUR -> {
                        title = I18N.getString(R.string.notification_tower_last_hour_title)
                        text = I18N.getString(R.string.notification_tower_last_hour_text)
                        channelId =
                            Statics.NOTIFICATION_CHANNEL_DEFAULT
                    }
                }
                val newIntent = Intent(context, MainActivity::class.java)
                val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_MUTABLE)
                } else {
                    PendingIntent.getActivity(context, 0, newIntent, 0)
                }
                val builder = NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.mic_crepe_filled)
                    .setContentTitle(title)
                    .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(text))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                with (NotificationManagerCompat.from(context)) {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return
                    }
                    // Random Id generator
                    notify(Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE), builder.build())
                }
            }
        }
    }
}