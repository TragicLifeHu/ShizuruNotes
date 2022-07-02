package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.Enemy
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.Skill
import com.github.nyanfantasia.shizurunotes.db.DBHelper.Companion.get
import com.github.nyanfantasia.shizurunotes.utils.Utils.getValueFromObject

@Suppress("PropertyName")
class RawEnemy {
    var enemy_id = 0
    var unit_id = 0
    var name: String? = null
    var level = 0
    var resist_status_id = 0
    var prefab_id = 0
    var atk_type = 0
    var search_area_width = 0
    var normal_atk_cast_time = 0.0
    var comment: String? = null
    var hp = 0
    var atk = 0
    var magic_str = 0
    var def = 0
    var magic_def = 0
    var physical_critical = 0
    var magic_critical = 0
    var wave_hp_recovery = 0
    var wave_energy_Recovery = 0
    var dodge = 0
    var physical_penetrate = 0
    var magic_penetrate = 0
    var life_steal = 0
    var hp_recovery_rate = 0
    var energy_recovery_rate = 0
    var energy_reduce_rate = 0
    var accuracy = 0
    var union_burst_level = 0
    var main_skill_lv_1 = 0
    var main_skill_lv_2 = 0
    var main_skill_lv_3 = 0
    var main_skill_lv_4 = 0
    var main_skill_lv_5 = 0
    var main_skill_lv_6 = 0
    var main_skill_lv_7 = 0
    var main_skill_lv_8 = 0
    var main_skill_lv_9 = 0
    var main_skill_lv_10 = 0
    var ex_skill_lv_1 = 0
    var ex_skill_lv_2 = 0
    var ex_skill_lv_3 = 0
    var ex_skill_lv_4 = 0
    var ex_skill_lv_5 = 0
    var child_enemy_parameter_1 = 0
    var child_enemy_parameter_2 = 0
    var child_enemy_parameter_3 = 0
    var child_enemy_parameter_4 = 0
    var child_enemy_parameter_5 = 0
    var union_burst = 0
    var main_skill_1 = 0
    var main_skill_2 = 0
    var main_skill_3 = 0
    var main_skill_4 = 0
    var main_skill_5 = 0
    var main_skill_6 = 0
    var main_skill_7 = 0
    var main_skill_8 = 0
    var main_skill_9 = 0
    var main_skill_10 = 0
    var ex_skill_1 = 0
    var ex_skill_evolution_1 = 0
    var ex_skill_2 = 0
    var ex_skill_evolution_2 = 0
    var ex_skill_3 = 0
    var ex_skill_evolution_3 = 0
    var ex_skill_4 = 0
    var ex_skill_evolution_4 = 0
    var ex_skill_5 = 0
    var ex_skill_evolution_5 = 0
    var sp_skill_1 = 0
    var sp_skill_2 = 0
    var sp_skill_3 = 0
    var sp_skill_4 = 0
    var sp_skill_5 = 0
    var union_burst_evolution = 0
    var main_skill_evolution_1 = 0
    var main_skill_evolution_2 = 0

    //children part
    val enemy: Enemy
        get() {
            val boss = Enemy(enemy_id)
            val property = Property(
                hp.toDouble(),
                atk.toDouble(),
                magic_str.toDouble(),
                def.toDouble(),
                magic_def.toDouble(),
                physical_critical.toDouble(),
                magic_critical.toDouble(),
                wave_hp_recovery.toDouble(),
                wave_energy_Recovery.toDouble(),
                dodge.toDouble(),
                physical_penetrate.toDouble(),
                magic_penetrate.toDouble(),
                life_steal.toDouble(),
                hp_recovery_rate.toDouble(),
                energy_recovery_rate.toDouble(),
                energy_reduce_rate.toDouble(),
                accuracy.toDouble()
            )
            comment = if (comment != null) {
                comment!!.replace("\\n　", "").replace("\\n", "").replace("・", "\n・")
            } else {
                ""
            }
            boss.setBasic(
                unit_id,
                name!!,
                comment!!,
                level,
                prefab_id,
                atk_type,
                search_area_width,
                normal_atk_cast_time,
                resist_status_id,
                property
            )

            //children part
            for (i in 1..5) {
                val childId = getValueFromObject(this, "child_enemy_parameter_$i") as Int
                if (childId != 0) {
                    val child = get().getEnemy(childId)!!.enemy
                    boss.children.add(child)
                    boss.isMultiTarget = true
                }
            }
            if (union_burst != 0) boss.skills.add(
                Skill(
                    union_burst,
                    Skill.SkillClass.UB,
                    union_burst_level
                )
            )
            if (union_burst_evolution != 0) boss.skills.add(
                Skill(
                    union_burst_evolution,
                    Skill.SkillClass.UB_EVO
                )
            )
            if (main_skill_1 != 0) boss.skills.add(
                Skill(
                    main_skill_1,
                    Skill.SkillClass.MAIN1,
                    main_skill_lv_1
                )
            )
            if (main_skill_evolution_1 != 0) boss.skills.add(
                Skill(
                    main_skill_evolution_1,
                    Skill.SkillClass.MAIN1_EVO
                )
            )
            if (main_skill_2 != 0) boss.skills.add(
                Skill(
                    main_skill_2,
                    Skill.SkillClass.MAIN2,
                    main_skill_lv_2
                )
            )
            if (main_skill_evolution_2 != 0) boss.skills.add(
                Skill(
                    main_skill_evolution_2,
                    Skill.SkillClass.MAIN2_EVO
                )
            )
            if (main_skill_3 != 0) boss.skills.add(
                Skill(
                    main_skill_3,
                    Skill.SkillClass.MAIN3,
                    main_skill_lv_3
                )
            )
            if (main_skill_4 != 0) boss.skills.add(
                Skill(
                    main_skill_4,
                    Skill.SkillClass.MAIN4,
                    main_skill_lv_4
                )
            )
            if (main_skill_5 != 0) boss.skills.add(
                Skill(
                    main_skill_5,
                    Skill.SkillClass.MAIN5,
                    main_skill_lv_5
                )
            )
            if (main_skill_6 != 0) boss.skills.add(
                Skill(
                    main_skill_6,
                    Skill.SkillClass.MAIN6,
                    main_skill_lv_6
                )
            )
            if (main_skill_7 != 0) boss.skills.add(
                Skill(
                    main_skill_7,
                    Skill.SkillClass.MAIN7,
                    main_skill_lv_7
                )
            )
            if (main_skill_8 != 0) boss.skills.add(
                Skill(
                    main_skill_8,
                    Skill.SkillClass.MAIN8,
                    main_skill_lv_8
                )
            )
            if (main_skill_9 != 0) boss.skills.add(
                Skill(
                    main_skill_9,
                    Skill.SkillClass.MAIN9,
                    main_skill_lv_9
                )
            )
            if (main_skill_10 != 0) boss.skills.add(
                Skill(
                    main_skill_10,
                    Skill.SkillClass.MAIN10,
                    main_skill_lv_10
                )
            )
            if (sp_skill_1 != 0) boss.skills.add(Skill(sp_skill_1, Skill.SkillClass.SP1))
            if (sp_skill_2 != 0) boss.skills.add(Skill(sp_skill_2, Skill.SkillClass.SP2))
            if (sp_skill_3 != 0) boss.skills.add(Skill(sp_skill_3, Skill.SkillClass.SP3))
            if (sp_skill_4 != 0) boss.skills.add(Skill(sp_skill_4, Skill.SkillClass.SP4))
            if (sp_skill_5 != 0) boss.skills.add(Skill(sp_skill_5, Skill.SkillClass.SP5))
            if (ex_skill_1 != 0) boss.skills.add(
                Skill(
                    ex_skill_1,
                    Skill.SkillClass.EX1,
                    ex_skill_lv_1
                )
            )
            if (ex_skill_evolution_1 != 0) boss.skills.add(
                Skill(
                    ex_skill_evolution_1,
                    Skill.SkillClass.EX1_EVO
                )
            )
            if (ex_skill_2 != 0) boss.skills.add(
                Skill(
                    ex_skill_2,
                    Skill.SkillClass.EX2,
                    ex_skill_lv_2
                )
            )
            if (ex_skill_evolution_2 != 0) boss.skills.add(
                Skill(
                    ex_skill_evolution_2,
                    Skill.SkillClass.EX2_EVO
                )
            )
            if (ex_skill_3 != 0) boss.skills.add(
                Skill(
                    ex_skill_3,
                    Skill.SkillClass.EX3,
                    ex_skill_lv_3
                )
            )
            if (ex_skill_evolution_3 != 0) boss.skills.add(
                Skill(
                    ex_skill_evolution_3,
                    Skill.SkillClass.EX3_EVO
                )
            )
            if (ex_skill_4 != 0) boss.skills.add(
                Skill(
                    ex_skill_4,
                    Skill.SkillClass.EX4,
                    ex_skill_lv_4
                )
            )
            if (ex_skill_evolution_4 != 0) boss.skills.add(
                Skill(
                    ex_skill_evolution_4,
                    Skill.SkillClass.EX4_EVO
                )
            )
            if (ex_skill_5 != 0) boss.skills.add(
                Skill(
                    ex_skill_5,
                    Skill.SkillClass.EX5,
                    ex_skill_lv_5
                )
            )
            if (ex_skill_evolution_5 != 0) boss.skills.add(
                Skill(
                    ex_skill_evolution_5,
                    Skill.SkillClass.EX5_EVO
                )
            )
            return boss
        }
}