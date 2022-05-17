package com.github.nyanfantasia.shizurunotes.ui.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.nyanfantasia.shizurunotes.data.SecretDungeonQuest
import com.github.nyanfantasia.shizurunotes.data.SecretDungeonSchedule
import com.github.nyanfantasia.shizurunotes.db.MasterSecretDungeon
import kotlin.concurrent.thread

class SharedViewModelSecretDungeon : ViewModel() {
    var secretDungeonScheduleList = MutableLiveData<List<SecretDungeonSchedule>>()
    var secretDungeonQuestMap = MutableLiveData<MutableMap<Int, List<SecretDungeonQuest>>>()
    var selectedSchedule: SecretDungeonSchedule? = null
    var selectedQuest: SecretDungeonQuest? = null

    fun loadSecretDungeonSchedules() {
        if (secretDungeonScheduleList.value.isNullOrEmpty()){
            thread(start = true){
                secretDungeonScheduleList.postValue(MasterSecretDungeon.getSecretDungeonSchedules())
            }
        }
    }

//    fun getDungeonQuest(dungeonAreaId: Int): List<SecretDungeonQuest> {
//        loadSecretDungeonQuests(dungeonAreaId)
//        return secretDungeonQuestMap.value!![dungeonAreaId]!!
//    }
}