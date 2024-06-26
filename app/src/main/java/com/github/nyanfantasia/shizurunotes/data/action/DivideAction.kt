package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

class DivideAction : ActionParameter() {
    override fun childInit() {
        actionValues.add(ActionValue(actionValue2!!, actionValue3!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        var result = super.localizedDetail(level, property)
        if (actionValue1!!.value == 0.0) result = getString(
            R.string.Modifier_multiple_s1_HP_max_HP_to_value_d2_of_effect_d3,
            buildExpression(level, null, RoundingMode.UNNECESSARY, property,
                isHealing = false,
                isSelfTPRestoring = false,
                hasBracesIfNeeded = true
            ),
            actionDetail2, actionNum(actionDetail1)
        )
        else if (actionValue1!!.value == 1.0) result = getString(
            R.string.Modifier_multiple_s1_lost_HP_max_HP_to_value_d2_of_effect_d3,
            buildExpression(level, null, RoundingMode.UNNECESSARY, property,
                isHealing = false,
                isSelfTPRestoring = false,
                hasBracesIfNeeded = true
            ),
            actionDetail2, actionNum(actionDetail1)
        )
        else if (actionValue1!!.value == 2.0) result = getString(
            R.string.Modifier_multiple_s1_count_of_defeated_enemies_to_value_d2_of_effect_d3,
            buildExpression(level, null, RoundingMode.UNNECESSARY, property,
                isHealing = false,
                isSelfTPRestoring = false,
                hasBracesIfNeeded = true
            ),
            actionDetail2, actionNum(actionDetail1)
        )
        else if (actionValue1!!.value == 4.0) result = getString(
            R.string.Modifier_multiple_s1_count_of_targets_to_value_d2_of_effect_d3,
            buildExpression(level, null, RoundingMode.UNNECESSARY, property,
                isHealing = false,
                isSelfTPRestoring = false,
                hasBracesIfNeeded = true
            ),
            actionDetail2, actionNum(actionDetail1)
        )
        else if (actionValue1!!.value >= 200 && actionValue1!!.value < 300) result = getString(
            R.string.Modifier_multiple_s1_stacks_of_mark_ID_d2_to_value_d3_of_effect_d4,
            buildExpression(level, null, RoundingMode.UNNECESSARY, property,
                isHealing = false,
                isSelfTPRestoring = false,
                hasBracesIfNeeded = true
            ),
            actionValue1!!.value.toInt() % 200, actionDetail2, actionNum(actionDetail1)
        )
        return result
    }
}