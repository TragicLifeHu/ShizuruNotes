package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.utils.Utils.getValueFromObject
import com.github.nyanfantasia.shizurunotes.data.EnemyRewardData
import com.github.nyanfantasia.shizurunotes.data.RewardData
import java.util.ArrayList

@Suppress("PropertyName")
class RawEnemyRewardData {
    var drop_reward_id = 0
    var drop_count = 0
    var reward_type_1 = 0
    var reward_id_1 = 0
    var reward_num_1 = 0
    var odds_1 = 0
    var reward_type_2 = 0
    var reward_id_2 = 0
    var reward_num_2 = 0
    var odds_2 = 0
    var reward_type_3 = 0
    var reward_id_3 = 0
    var reward_num_3 = 0
    var odds_3 = 0
    var reward_type_4 = 0
    var reward_id_4 = 0
    var reward_num_4 = 0
    var odds_4 = 0
    var reward_type_5 = 0
    var reward_id_5 = 0
    var reward_num_5 = 0
    var odds_5 = 0
    val enemyRewardData: EnemyRewardData
        get() {
            val rewardDataList: MutableList<RewardData> = ArrayList()
            for (i in 1..5) {
                val rewardId = getValueFromObject(this, "reward_id_$i") as Int
                if (rewardId != 0) {
                    rewardDataList.add(
                        RewardData(
                            getValueFromObject(this, "reward_type_$i") as Int,
                            rewardId,
                            getValueFromObject(this, "reward_num_$i") as Int,
                            getValueFromObject(this, "odds_$i") as Int
                        )
                    )
                }
            }
            return EnemyRewardData(rewardDataList)
        }
}