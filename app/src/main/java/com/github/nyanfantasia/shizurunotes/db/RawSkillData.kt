package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.Skill
import com.github.nyanfantasia.shizurunotes.utils.Utils.getValueFromObject

@Suppress("PropertyName", "PrivatePropertyName")
class RawSkillData {
    var skill_id = 0
    var name: String? = null
    private var skill_type = 0
    private var skill_area_width = 0
    private var skill_cast_time = 0.0
    private var boss_ub_cool_time = 0.0
    var action_1 = 0
    var action_2 = 0
    var action_3 = 0
    var action_4 = 0
    var action_5 = 0
    var action_6 = 0
    var action_7 = 0
    var action_8 = 0
    var action_9 = 0
    var action_10 = 0
    var depend_action_1 = 0
    var depend_action_2 = 0
    var depend_action_3 = 0
    var depend_action_4 = 0
    var depend_action_5 = 0
    var depend_action_6 = 0
    var depend_action_7 = 0
    var depend_action_8 = 0
    var depend_action_9 = 0
    var depend_action_10 = 0
    var description: String? = null
    private var icon_type = 0
    fun setSkillData(skill: Skill) {
        skill.setSkillData(
            name!!,
            skill_type,
            skill_area_width,
            skill_cast_time,
            boss_ub_cool_time,
            description!!,
            icon_type
        )
        var iteration = if (DBHelper.get().hasAction10) 10 else 7
        for (i in 1..iteration) {
            val action = getValueFromObject(this, "action_$i") as Int
            if (action != 0) {
                skill.actions.add(
                    skill.Action(
                        action,
                        getValueFromObject(this, "depend_action_$i") as Int
                    )
                )
            }
        }
    }
}