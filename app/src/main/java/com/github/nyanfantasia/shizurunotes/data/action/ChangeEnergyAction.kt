package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

class ChangeEnergyAction : ActionParameter() {
    override fun childInit() {
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return when (actionDetail1) {
            1 -> if (targetParameter!!.targetType === TargetType.Self) {
                getString(
                    R.string.Restore_s1_s2_TP,
                    targetParameter!!.buildTargetClause(),
                    buildExpression(
                        level,
                        null,
                        RoundingMode.CEILING,
                        property,
                        isHealing = false,
                        isSelfTPRestoring = true,
                        hasBracesIfNeeded = false
                    )
                )
            } else {
                getString(
                    R.string.Restore_s1_s2_TP,
                    targetParameter!!.buildTargetClause(),
                    buildExpression(level, RoundingMode.CEILING, property)
                )
            }
            else -> getString(
                R.string.Make_s1_lose_s2_TP,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, RoundingMode.CEILING, property)
            )
        }
    }
}