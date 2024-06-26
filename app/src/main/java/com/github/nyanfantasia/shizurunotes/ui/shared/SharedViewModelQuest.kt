package com.github.nyanfantasia.shizurunotes.ui.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.nyanfantasia.shizurunotes.data.Quest
import com.github.nyanfantasia.shizurunotes.db.MasterQuest
import kotlin.concurrent.thread

class SharedViewModelQuest : ViewModel() {
    val questList = MutableLiveData<List<Quest>>()
    val loadingFlag = MutableLiveData(false)
    var includeNormal = false
    var includeHard = false

    /***
     * 从数据库读取所有任务数据。
     */
    fun loadData() {
        if (questList.value.isNullOrEmpty()) {
            loadingFlag.value = true
            thread(start = true) {
                questList.postValue(MasterQuest().quest)
                loadingFlag.postValue(false)
            }
        }
    }
}