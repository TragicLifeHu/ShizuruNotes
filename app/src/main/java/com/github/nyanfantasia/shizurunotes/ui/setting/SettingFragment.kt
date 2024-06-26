package com.github.nyanfantasia.shizurunotes.ui.setting

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.checkbox.checkBoxPrompt
import com.github.nyanfantasia.shizurunotes.BuildConfig
import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.App
import com.github.nyanfantasia.shizurunotes.common.AppNotificationManager
import com.github.nyanfantasia.shizurunotes.common.UpdateManager
import com.github.nyanfantasia.shizurunotes.user.UserSettings
import com.github.nyanfantasia.shizurunotes.user.UserSettings.Companion.DB_VERSION
import com.jakewharton.processphoenix.ProcessPhoenix
import kotlin.concurrent.thread

class SettingFragment : PreferenceFragmentCompat() {

    override fun onResume() {
        super.onResume()
        findPreference<Preference>(DB_VERSION)?.summary = UserSettings.get().getDbVersion().toString()
    }

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        //App version
        findPreference<Preference>(UserSettings.APP_VERSION)?.apply {
            summary = BuildConfig.VERSION_NAME
            isSelectable = false
        }

        //Database version
        findPreference<Preference>(DB_VERSION)?.apply {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                it.isEnabled = false
                UpdateManager.get().checkDatabaseVersion()
                thread(start = true){
                    Thread.sleep(5000)
                    activity?.runOnUiThread {
                        it.isEnabled = true
                    }
                }
                true
            }
        }

        //Retry download database, disabled
//        findPreference<Preference>("reDownloadDb")?.apply {
//            onPreferenceClickListener = Preference.OnPreferenceClickListener {
//                UpdateManager.get().forceDownloadDb()
//                true
//            }
//        }

        //Log
        findPreference<Preference>(UserSettings.LOG)?.apply {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                val action = SettingContainerFragmentDirections.actionNavSettingContainerToNavLog()
                findNavController().navigate(action)
                true
            }
        }

        //About
        findPreference<Preference>(UserSettings.ABOUT)?.apply {
            onPreferenceClickListener = Preference.OnPreferenceClickListener {
                val action = SettingContainerFragmentDirections.actionNavSettingContainerToNavSettingAbout()
                findNavController().navigate(action)
                true
            }
        }

        //Language selection
        val languagePreference = findPreference<ListPreference>(UserSettings.LANGUAGE_KEY)
        if (languagePreference != null) {
            languagePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _: Preference?, newValue: Any? ->
                    App.localeManager.setNewLocale(
                        requireActivity().application,
                        newValue as String
                    )
                    thread(start = true){
                        Thread.sleep(100)
                        ProcessPhoenix.triggerRebirth(activity)
                    }
                    true
                }
        }

        //Server selection
        val serverPreference = findPreference<ListPreference>(UserSettings.SERVER_KEY)
        if (serverPreference != null) {
            serverPreference.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    if (!UserSettings.get().getHideServerSwitchHint()) {
                        thread(start = true) {
                            Thread.sleep(100)
                            activity?.runOnUiThread {
                                MaterialDialog(requireContext(), MaterialDialog.DEFAULT_BEHAVIOR)
                                    .title(R.string.dialog_server_switch_title)
                                    .message(R.string.dialog_server_switch_text)
                                    .show {
                                        checkBoxPrompt(R.string.do_not_show_anymore) { checked ->
                                            UserSettings.get().setHideServerSwitchHint(checked)
                                        }
                                        positiveButton(res = R.string.text_ok)
                                    }
                            }
                        }
                    }
                    true
                }
            serverPreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, _ ->
                    AppNotificationManager.instance.cancelAllAlarm()
                    thread(start = true){
                        Thread.sleep(100)
                        ProcessPhoenix.triggerRebirth(activity)
                    }
                    true
                }
        }
    }
}