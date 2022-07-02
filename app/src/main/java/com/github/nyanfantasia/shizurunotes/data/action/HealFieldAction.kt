package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.PropertyKey
import com.github.nyanfantasia.shizurunotes.data.action.PercentModifier.Companion.parse
import java.math.RoundingMode

class HealFieldAction : ActionParameter() {
    private var healClass: ClassModifier? = null
    private var percentModifier: PercentModifier? = null
    private var fieldType: FieldType? = null
    private val durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        healClass = if (actionDetail1 % 2 == 0) ClassModifier.Magic else ClassModifier.Physical
        percentModifier = parse(actionDetail2)
        fieldType =
            if (actionDetail1 <= 2) FieldType.Normal else FieldType.Repeat
        when (healClass) {
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
                    R.string.Summon_a_healing_field_of_radius_d1_to_heal_s2_s3_s4_HP_per_second_for_5s_sec,
                    actionValue7!!.value.toInt(),
                    targetParameter!!.buildTargetClause(),
                    buildExpression(level, property),
                    percentModifier!!.description(),
                    buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
                )
            } else {
                getString(
                    R.string.Summon_a_healing_field_of_radius_d1_at_position_of_s2_to_heal_s3_s4_HP_per_second_for_s5_sec,
                    actionValue7!!.value.toInt(),
                    targetParameter!!.buildTargetClause(),
                    buildExpression(level, property),
                    percentModifier!!.description(),
                    buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
                )
            }
            else -> super.localizedDetail(level, property)
        }
    }
}

enum class FieldType {
    Normal, Repeat
}