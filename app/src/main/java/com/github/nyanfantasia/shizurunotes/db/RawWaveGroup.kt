package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.Enemy
import com.github.nyanfantasia.shizurunotes.data.EnemyRewardData
import com.github.nyanfantasia.shizurunotes.data.WaveGroup
import com.github.nyanfantasia.shizurunotes.db.DBHelper.Companion.get
import java.util.*

@Suppress("PropertyName", "PrivatePropertyName")
class RawWaveGroup {
    var id = 0
    private var wave_group_id = 0
    private var enemy_id_1 = 0
    private var drop_gold_1 = 0
    private var drop_reward_id_1 = 0
    private var enemy_id_2 = 0
    private var drop_gold_2 = 0
    private var drop_reward_id_2 = 0
    private var enemy_id_3 = 0
    private var drop_gold_3 = 0
    private var drop_reward_id_3 = 0
    private var enemy_id_4 = 0
    private var drop_gold_4 = 0
    private var drop_reward_id_4 = 0
    private var enemy_id_5 = 0
    private var drop_gold_5 = 0
    private var drop_reward_id_5 = 0
    fun getWaveGroup(needEnemy: Boolean): WaveGroup {
        val waveGroup = WaveGroup(id, wave_group_id)
        if (needEnemy) {
            val enemyList: MutableList<Enemy> = ArrayList()
            val rawEnemyList = get().getEnemy(
                ArrayList(
                    listOf(
                        enemy_id_1,
                        enemy_id_2,
                        enemy_id_3,
                        enemy_id_4,
                        enemy_id_5
                    )
                )
            )
            if (rawEnemyList != null) {
                for (rawEnemy in rawEnemyList) {
                    enemyList.add(rawEnemy.enemy)
                }
                waveGroup.enemyList = enemyList
            }
        }
        val rewardDataList: MutableList<EnemyRewardData> = ArrayList()
        val rawRewardDataList = get().getEnemyRewardData(
            ArrayList(
                listOf(
                    drop_reward_id_1,
                    drop_reward_id_2,
                    drop_reward_id_3,
                    drop_reward_id_4,
                    drop_reward_id_5
                )
            )
        )
        if (rawRewardDataList != null) {
            for (raw in rawRewardDataList) {
                rewardDataList.add(raw.enemyRewardData)
            }
        }
        waveGroup.dropGoldList = ArrayList(
            listOf(
                drop_gold_1,
                drop_gold_2,
                drop_gold_3,
                drop_gold_4,
                drop_gold_5
            )
        )
        waveGroup.dropRewardList = rewardDataList
        return waveGroup
    }
}