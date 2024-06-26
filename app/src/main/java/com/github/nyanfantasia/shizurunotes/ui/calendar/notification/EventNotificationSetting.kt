package com.github.nyanfantasia.shizurunotes.ui.calendar.notification

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.AppNotificationManager
import com.github.nyanfantasia.shizurunotes.common.Statics.TYPE_STRING_LIST

class EventNotificationSetting : PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.event_notification_preferences, rootKey)

        TYPE_STRING_LIST.forEach {
            findPreference<SwitchPreferenceCompat>(it)?.onPreferenceChangeListener = OnPreferenceChangeListener(switchNotification())
        }
    }

    private fun switchNotification(): (p: Preference, b: Any) -> Boolean {
        return { p , b ->
            AppNotificationManager.instance.refreshSpecificNotification(p.key, b as Boolean)
            true
        }
    }
}