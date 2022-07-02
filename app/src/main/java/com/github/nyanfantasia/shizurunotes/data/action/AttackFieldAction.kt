package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.PropertyKey
import java.math.RoundingMode

class AttackFieldAction : ActionParameter() {
    private var damageClass: ClassModifier? = null
    private var fieldType: FieldType? = null
    private val durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        damageClass = if (actionDetail1 % 2 == 0) ClassModifier.Magic else ClassModifier.Physical
        fieldType =
            if (actionDetail1 <= 2) FieldType.Normal else FieldType.Repeat
        when (damageClass) {
            ClassModifier.Magic -> {
                actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
                actionValues.add(ActionValue(actionValue3!!, actionValue4!!, PropertyKey.MagicStr))
            }
            ClassModifier.Physical -> {
                actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
                actionValues.add(ActionValue(actionValue3!!, actionValue4!!, PropertyKey.Atk))
            }
            else -> {
                actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
                actionValues.add(ActionValue(actionValue3!!, actionValue4!!, PropertyKey.Atk))
            }
        }
        durationValues.add(ActionValue(actionValue5!!, actionValue6!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (fieldType) {
            FieldType.Repeat -> if (targetParameter!!.targetType === TargetType.Absolute) {
                getString(
                    R.string.Summon_a_field_of_radius_d1_to_deal_s2_s3_damage_per_second_for_s4_sec_to_s5,
                    actionValue7!!.value.toInt(),
                    buildExpression(level, property),
                    damageClass!!.description(),
                    buildExpression(
                        level,
                        durationValues,
                        RoundingMode.UNNECESSARY,
                        property
                    ),
                    targetParameter!!.buildTargetClause()
                )
            } else {
                getString(
                    R.string.Summon_a_field_of_radius_d1_at_position_of_s2_to_deal_s3_s4_damage_per_second_for_s5_sec,
                    actionValue7!!.value.toInt(),
                    targetParameter!!.buildTargetClause(),
                    buildExpression(level, property),
                    damageClass!!.description(),
                    buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
                )
            }
            else -> super.localizedDetail(level, property)
        }
    }
}