package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.Quest
import com.github.nyanfantasia.shizurunotes.data.WaveGroup
import com.github.nyanfantasia.shizurunotes.db.DBHelper.Companion.get
import java.util.*

@Suppress("PropertyName", "PrivatePropertyName")
class RawQuest {
    private var quest_id = 0
    private var area_id = 0
    private var quest_name: String? = null
    private var wave_group_id_1 = 0
    private var wave_group_id_2 = 0
    private var wave_group_id_3 = 0
    private var reward_image_1 = 0
    private var reward_image_2 = 0
    private var reward_image_3 = 0
    private var reward_image_4 = 0
    private var reward_image_5 = 0
    val quest: Quest
        get() {
            val waveGroupList: MutableList<WaveGroup> = ArrayList()
            val rawWaveGroupList = get().getWaveGroupData(
                ArrayList(
                    listOf(
                        wave_group_id_1,
                        wave_group_id_2,
                        wave_group_id_3
                    )
                )
            )
            if (rawWaveGroupList != null) {
                for (rawWaveGroup in rawWaveGroupList) {
                    waveGroupList.add(rawWaveGroup.getWaveGroup(false))
                }
            }
            val rewardImages = ArrayList<Int>()
            if (reward_image_1 > 100000) {
                rewardImages.add(reward_image_1)
            }
            if (reward_image_2 > 100000) {
                rewardImages.add(reward_image_2)
            }
            if (reward_image_3 > 100000) {
                rewardImages.add(reward_image_3)
            }
            if (reward_image_4 > 100000) {
                rewardImages.add(reward_image_4)
            }
            if (reward_image_5 > 100000) {
                rewardImages.add(reward_image_5)
            }
            return Quest(quest_id, area_id, quest_name!!, waveGroupList, rewardImages)
        }
}