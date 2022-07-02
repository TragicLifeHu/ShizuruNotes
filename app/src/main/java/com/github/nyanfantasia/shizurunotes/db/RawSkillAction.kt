package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.Skill

@Suppress("PropertyName", "PrivatePropertyName")
class RawSkillAction {
    var action_id = 0
    private var class_id = 0
    private var action_type = 0
    private var action_detail_1 = 0
    private var action_detail_2 = 0
    private var action_detail_3 = 0
    private var action_value_1 = 0.0
    private var action_value_2 = 0.0
    private var action_value_3 = 0.0
    private var action_value_4 = 0.0
    private var action_value_5 = 0.0
    private var action_value_6 = 0.0
    private var action_value_7 = 0.0
    private var target_assignment = 0
    private var target_area = 0
    private var target_range = 0
    private var target_type = 0
    private var target_number = 0
    private var target_count = 0
    fun setActionData(action: Skill.Action) {
        action.setActionData(
            class_id,
            action_type,
            action_detail_1,
            action_detail_2,
            action_detail_3,
            action_value_1,
            action_value_2,
            action_value_3,
            action_value_4,
            action_value_5,
            action_value_6,
            action_value_7,
            target_assignment,
            target_area,
            target_range,
            target_type,
            target_number,
            target_count
        )
    }
}