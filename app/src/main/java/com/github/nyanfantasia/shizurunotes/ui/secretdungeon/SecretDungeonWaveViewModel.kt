package com.github.nyanfantasia.shizurunotes.ui.secretdungeon

import androidx.lifecycle.ViewModel
import com.github.nyanfantasia.shizurunotes.data.WaveGroup
import com.github.nyanfantasia.shizurunotes.ui.base.OnItemActionListener
import com.github.nyanfantasia.shizurunotes.ui.base.SecretDungeonQuestVT
import com.github.nyanfantasia.shizurunotes.ui.base.ViewType
import com.github.nyanfantasia.shizurunotes.ui.shared.SharedViewModelSecretDungeon

class SecretDungeonWaveViewModel(
    private val sharedSecretDungeon: SharedViewModelSecretDungeon
) : ViewModel() {
        val viewList = mutableListOf<ViewType<*>>()
            get() {
                field.clear()
                sharedSecretDungeon.selectedSchedule?.let { schedule ->
                    schedule.waveGroupMap.forEach {
                        field.add(SecretDungeonQuestVT(it))
                    }
                }
                return field
            }
}

interface OnSecretDungeonQuestClickListener: OnItemActionListener {
    fun onSecretDungeonQuestClicked(waveGroup: WaveGroup)
}
