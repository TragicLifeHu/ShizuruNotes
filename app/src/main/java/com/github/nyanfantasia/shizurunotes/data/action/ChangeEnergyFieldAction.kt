package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

class ChangeEnergyFieldAction : ActionParameter() {
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        durationValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (actionDetail1) {
            1 -> getString(
                R.string.Summon_a_field_with_radius_d1_at_position_s2_which_continuous_restore_tp_s3_of_units_within_the_field_for_s4_sec,
                actionValue5!!.value.toInt(),
                targetParameter!!.buildTargetClause(),
                buildExpression(
                    level,
                    actionValues,
                    RoundingMode.CEILING,
                    property,
                    false,
                    targetParameter!!.targetType === TargetType.Self,
                    false
                ),
                buildExpression(level, durationValues, null, property)
            )
            2 -> getString(
                R.string.Summon_a_field_with_radius_d1_at_position_s2_which_continuous_lose_tp_s3_of_units_within_the_field_for_s4_sec,
                actionValue5!!.value.toInt(),
                targetParameter!!.buildTargetClause(),
                buildExpression(
                    level,
                    actionValues,
                    RoundingMode.CEILING,
                    property,
                    false,
                    targetParameter!!.targetType === TargetType.Self,
                    false
                ),
                buildExpression(level, durationValues, null, property)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}