package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode


class AccumulativeDamageActionForAllEnemy : AccumulativeDamageAction() {
    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.Add_additional_s1_damage_per_attack_with_max_s2_stacks_to_current_target_for_all_enemy,
            buildExpression(level, property),
            buildExpression(level, stackValues, RoundingMode.FLOOR, property)
        )
    }
}