package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils
import java.math.RoundingMode


class AttackSealActionForAllEnemy : AttackSealAction() {
    override fun localizedDetail(level: Int, property: Property?): String? {
        return if (condition === Condition.Hit) getString(
            R.string.Make_s1_when_get_one_hit_by_the_caster_gain_one_mark_stack_max_s2_ID_s3_for_s4_sec_for_all_enemy,
            targetParameter!!.buildTargetClause(),
            Utils.roundDownDouble(actionValue1!!.value),
            Utils.roundDownDouble(actionValue2!!.value),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        ) else if (condition === Condition.Damage && target === Target.Owner) getString(
            R.string.Make_s1_when_deal_damage_gain_one_mark_stack_max_s2_ID_s3_for_s4_sec_for_all_enemy,
            targetParameter!!.buildTargetClause(),
            Utils.roundDownDouble(actionValue1!!.value),
            Utils.roundDownDouble(actionValue2!!.value),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        ) else if (condition === Condition.CriticalHit && target === Target.Owner) getString(
            R.string.Make_s1_when_deal_critical_damage_gain_one_mark_stack_max_s2_ID_s3_for_s4_sec_for_all_enemy,
            targetParameter!!.buildTargetClause(),
            Utils.roundDownDouble(actionValue1!!.value),
            Utils.roundDownDouble(actionValue2!!.value),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        ) else super.localizedDetail(level, property)
    }
}