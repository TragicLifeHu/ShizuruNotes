package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.Enemy
import com.github.nyanfantasia.shizurunotes.data.SpEvent
import com.github.nyanfantasia.shizurunotes.db.DBHelper.Companion.get
import com.google.common.collect.Lists

@Suppress("PrivatePropertyName")
class RawSpEvent {
    private var boss_id = 0
    var name: String? = null
    private var wave_group_id = 0
    private var enemy_id_1 = 0
    private var enemy_id_2 = 0
    private var enemy_id_3 = 0
    private var enemy_id_4 = 0
    private var enemy_id_5 = 0
    val spEvent: SpEvent?
        get() {
            val rawEnemyList = get().getEnemy(
                Lists.newArrayList(
                    enemy_id_1,
                    enemy_id_2,
                    enemy_id_3,
                    enemy_id_4,
                    enemy_id_5
                )
            )
            val enemyList: MutableList<Enemy> = ArrayList()
            assert(rawEnemyList != null)
            for (raw in rawEnemyList!!) {
                enemyList.add(raw.enemy)
            }
            return if (enemyList.size > 0) {
                SpEvent(
                    boss_id,
                    name!!,
                    wave_group_id,
                    enemyList
                )
            } else {
                null
            }
        }
}