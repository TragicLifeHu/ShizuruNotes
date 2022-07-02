package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.Chara
import com.github.nyanfantasia.shizurunotes.data.CharaStoryStatus
import com.github.nyanfantasia.shizurunotes.data.OneStoryStatus
import com.github.nyanfantasia.shizurunotes.utils.Utils.getValueFromObject

@Suppress("PropertyName", "PrivatePropertyName")
class RawCharaStoryStatus {
    private var story_id = 0
    private var unlock_story_name: String? = null
    var status_type_1 = 0
    var status_rate_1 = 0
    var status_type_2 = 0
    var status_rate_2 = 0
    var status_type_3 = 0
    var status_rate_3 = 0
    var status_type_4 = 0
    var status_rate_4 = 0
    var status_type_5 = 0
    var status_rate_5 = 0
    private var chara_id_1 = 0
    fun getCharaStoryStatus(chara: Chara): OneStoryStatus {
        val list: MutableList<CharaStoryStatus> = ArrayList()
        for (i in 1..5) {
            val typeValue = getValueFromObject(this, "status_type_$i") as Int
            if (typeValue != 0) {
                val typeRate = getValueFromObject(this, "status_rate_$i") as Int
                val charaStoryStatus =
                    CharaStoryStatus(chara.charaId, typeValue, typeRate.toDouble())
                list.add(charaStoryStatus)
            }
        }
        return OneStoryStatus(story_id, unlock_story_name!!, chara_id_1, list)
    }
}