package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils.roundDownDouble
import java.math.RoundingMode

open class AttackSealAction : ActionParameter() {
    enum class Condition(val value: Int) {
        Unknown(-1),
        Damage(1),
        Target(2),
        DamageMulti(3),
        CriticalHit(4),
        Hit(5);

        companion object {
            fun parse(value: Int): Condition {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    enum class Target(val value: Int) {
        Unknown(-1), RTarget(0), Owner(1);

        companion object {
            fun parse(value: Int): Target {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    protected var condition: Condition? = null
    protected var target: Target? = null
    protected var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        condition = Condition.parse(actionDetail1)
        target = Target.parse(actionDetail3)
        durationValues.add(ActionValue(actionValue5!!, actionValue6!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return if (condition == Condition.Hit) getString(
            R.string.Make_s1_when_get_one_hit_by_the_caster_gain_one_mark_stack_max_s2_ID_s3_for_s4_sec,
            targetParameter!!.buildTargetClause(),
            roundDownDouble(actionValue1!!.value),
            roundDownDouble(actionValue2!!.value),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        ) else if (condition == Condition.Damage && target == Target.Owner) getString(
            R.string.Make_s1_when_deal_damage_gain_one_mark_stack_max_s2_ID_s3_for_s4_sec,
            targetParameter!!.buildTargetClause(),
            roundDownDouble(actionValue1!!.value),
            roundDownDouble(actionValue2!!.value),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        ) else if (condition == Condition.CriticalHit && target == Target.Owner) getString(
            R.string.Make_s1_when_deal_critical_damage_gain_one_mark_stack_max_s2_ID_s3_for_s4_sec,
            targetParameter!!.buildTargetClause(),
            roundDownDouble(actionValue1!!.value),
            roundDownDouble(actionValue2!!.value),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        ) else super.localizedDetail(level, property)
    }
}