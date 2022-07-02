package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.Dungeon
import com.github.nyanfantasia.shizurunotes.data.Enemy
import com.github.nyanfantasia.shizurunotes.db.DBHelper.Companion.get
import com.google.common.collect.Lists

@Suppress("PropertyName", "PrivatePropertyName")
class RawDungeon {
    private var dungeon_area_id = 0
    private var dungeon_name: String? = null
    var description: String? = null
    var mode = 0
    private var wave_group_id = 0
    private var enemy_id_1 = 0
    private var enemy_id_2 = 0
    private var enemy_id_3 = 0
    private var enemy_id_4 = 0
    private var enemy_id_5 = 0
    val dungeon: Dungeon?
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
            for (raw in rawEnemyList!!) {
                enemyList.add(raw.enemy)
            }
            return if (enemyList.size > 0) {
                Dungeon(
                    dungeon_area_id,
                    wave_group_id,
                    enemy_id_1,
                    mode,
                    dungeon_name!!,
                    description!!,
                    enemyList
                )
            } else {
                null
            }
        }
}