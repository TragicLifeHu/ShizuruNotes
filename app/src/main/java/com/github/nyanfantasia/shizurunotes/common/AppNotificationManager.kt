package com.github.nyanfantasia.shizurunotes.common

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.github.nyanfantasia.shizurunotes.data.CampaignSchedule
import com.github.nyanfantasia.shizurunotes.data.CampaignType
import com.github.nyanfantasia.shizurunotes.data.EventSchedule
import com.github.nyanfantasia.shizurunotes.data.EventType
import com.github.nyanfantasia.shizurunotes.db.MasterSchedule
import com.github.nyanfantasia.shizurunotes.user.UserSettings
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.concurrent.thread

class AppNotificationManager {
    companion object {
        val instance: AppNotificationManager by lazy { AppNotificationManager() }
    }

    private val mContext: Context = App.instance

    private var futureSchedule: MutableList<EventSchedule> = mutableListOf()

    private val notificationTypeMap = mapOf(
        Statics.NORMAL_BEFORE to CampaignType.DropAmountNormal,
        Statics.DUNGEON_BEFORE_2 to CampaignType.ManaDungeon,
        Statics.DUNGEON_BEFORE to CampaignType.ManaDungeon,
        Statics.HATSUNE_LAST to EventType.Hatsune,
        Statics.HATSUNE_LAST_HOUR to EventType.Hatsune,
        Statics.TOWER_LAST_HOUR to EventType.Tower
    )

    fun loadData() {
        thread(start = true) {
            futureSchedule = MasterSchedule().getSchedule(LocalDateTime.now())
            refreshNotification()
        }
    }

    private fun refreshNotification() {
        futureSchedule.forEach {
            prepareAlarm(it, false)
        }
    }

    fun cancelAllAlarm() {
        futureSchedule.forEach {
            prepareAlarm(it, true)
        }
    }

    fun refreshSpecificNotification(typeString: String, newValue: Boolean) {
        futureSchedule.forEach {
            var t: Enum<*> = it.type
            if (it is CampaignSchedule) {
                t = it.campaignType
            }
            if (t == notificationTypeMap[typeString]) {
                if (newValue) {
                    setAlarm(it, typeString)
                } else {
                    cancelAlarm(getIntent(typeString), getSpecificId(it, typeString))
                }
            }
        }
    }

    private fun prepareAlarm(eventSchedule: EventSchedule, cancel: Boolean) {
        if (eventSchedule is CampaignSchedule) {
            when (eventSchedule.campaignType) {
                CampaignType.DropAmountNormal -> {
                    setOrCancelAlarm(eventSchedule, Statics.NORMAL_BEFORE, cancel)
                }
                CampaignType.ManaDungeon -> {
                    setOrCancelAlarm(eventSchedule, Statics.DUNGEON_BEFORE_2, cancel)
                    setOrCancelAlarm(eventSchedule, Statics.DUNGEON_BEFORE, cancel)
                }
                else -> {  }
            }
        } else {
            when (eventSchedule.type) {
                EventType.Hatsune -> {
                    setOrCancelAlarm(eventSchedule, Statics.HATSUNE_LAST, cancel)
                    setOrCancelAlarm(eventSchedule, Statics.HATSUNE_LAST_HOUR, cancel)
                }
                EventType.Tower -> {
                    setOrCancelAlarm(eventSchedule, Statics.TOWER_LAST_HOUR, cancel)
                }
                else -> {  }
            }
        }
    }

    private fun setOrCancelAlarm(eventSchedule: EventSchedule, typeString: String, cancel: Boolean) {
        if (UserSettings.get().preference.getBoolean(typeString, false) && !cancel) {
            setAlarm(eventSchedule, typeString)
        } else {
            cancelAlarm(getIntent(typeString), getSpecificId(eventSchedule, typeString))
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setAlarm(eventSchedule: EventSchedule, typeString: String) {
        if (!eventSchedule.startTime.isEqual(eventSchedule.endTime)) {
            val triggerTime = getTriggerTime(eventSchedule, typeString)
            if (triggerTime.isAfter(LocalDateTime.now())) {
                val alarmMgr = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = getIntent(typeString)
                val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getBroadcast(
                        mContext,
                        getSpecificId(eventSchedule, typeString),
                        intent,
                        PendingIntent.FLAG_MUTABLE
                    )
                } else {
                    PendingIntent.getBroadcast(
                        mContext,
                        getSpecificId(eventSchedule, typeString),
                        intent,
                        0
                    )
                }
//                val zoneOffset = TimeZone.getDefault().toZoneId().rules.getOffset(LocalDateTime.now())

                alarmMgr.set(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime.toInstant(ZoneOffset.of("+9")).toEpochMilli(),
                    pendingIntent
                )
//            alarmMgr.set(AlarmManager.RTC_WAKEUP, LocalDateTime.now().plusSeconds(8).toInstant(zoneOffset).toEpochMilli(), pendingIntent)
            }
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun cancelAlarm(intent: Intent, id: Int) {
        val alarmMgr = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(mContext, id, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getBroadcast(mContext, id, intent, 0)
        }
        alarmMgr.cancel(pendingIntent)
    }

    private fun getTriggerTime(eventSchedule: EventSchedule, type: String): LocalDateTime {
        return when (type) {
            Statics.DUNGEON_BEFORE_2 -> eventSchedule.startTime.plusDays(-2).withHour(5).withMinute(0).withSecond(0)
            Statics.NORMAL_BEFORE,
            Statics.DUNGEON_BEFORE -> eventSchedule.startTime.plusDays(-1).withHour(5).withMinute(0).withSecond(0)
            Statics.HATSUNE_LAST -> eventSchedule.endTime.withHour(5).withMinute(0).withSecond(0)
            Statics.HATSUNE_LAST_HOUR,
            Statics.TOWER_LAST_HOUR -> eventSchedule.endTime.plusHours(-1)
            else -> LocalDateTime.MIN
        }
    }

    private fun getIntent(type: String): Intent {
        return Intent().apply {
            setClass(mContext, AlarmReceiver::class.java)
            action =
                Statics.NOTIFICATION_ACTION
            putExtra(Statics.NOTIFICATION_EXTRA_TYPE, type)
        }
    }

    private fun getSpecificId(eventSchedule: EventSchedule, typeString: String): Int {
        return when (typeString) {
            Statics.NORMAL_BEFORE -> eventSchedule.id + 100000
            Statics.DUNGEON_BEFORE_2 -> eventSchedule.id + 100000
            Statics.DUNGEON_BEFORE -> eventSchedule.id + 200000
            Statics.HATSUNE_LAST -> eventSchedule.id + 100000
            Statics.HATSUNE_LAST_HOUR -> eventSchedule.id + 200000
            Statics.TOWER_LAST_HOUR -> eventSchedule.id + 100000
            else -> 0
        }
    }
}