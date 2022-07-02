package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.action.PercentModifier.Companion.parse
import java.math.RoundingMode

class ChangeParameterFieldAction : AuraAction() {
    override fun childInit() {
        super.childInit()
        actionValues.clear()
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        durationValues.clear()
        durationValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
        super.percentModifier = parse(actionDetail2)
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return if (targetParameter!!.targetType === TargetType.Absolute) {
            getString(
                R.string.Summon_a_field_of_radius_d1_to_s2_s3_s4_s5_for_s6_sec,
                actionValue5!!.value.toInt(),
                auraActionType!!.description(),
                targetParameter!!.buildTargetClause(),
                buildExpression(level, RoundingMode.UP, property),
                auraType!!.description(),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property),
                percentModifier!!.description()
            )
        } else {
            getString(
                R.string.Summon_a_field_of_radius_d1_at_position_of_s2_to_s3_s4_s5_for_s6_sec,
                actionValue5!!.value.toInt(),
                targetParameter!!.buildTargetClause(),
                auraActionType!!.description(),
                buildExpression(level, RoundingMode.UP, property),
                auraType!!.description(),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property),
                percentModifier!!.description()
            )
        }
    }
}