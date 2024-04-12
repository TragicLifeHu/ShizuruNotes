package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

class CharmAction : ActionParameter() {
    internal enum class CharmType(val value: Int) {
        Unknown(-1), Charm(0), Confusion(1);

        companion object {
            fun parse(value: Int): CharmType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private val chanceValues: MutableList<ActionValue> = ArrayList()
    private val durationValues: MutableList<ActionValue> = ArrayList()
    private var charmType: CharmType? = null
    override fun childInit() {
        charmType = CharmType.parse(actionDetail1)
        durationValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        when (charmType) {
            CharmType.Charm -> chanceValues.add(
                ActionValue(
                    actionValue3!!.value,
                    actionValue4!!.value * 100,
                    EActionValue.VALUE3,
                    EActionValue.VALUE4,
                    null
                )
            )
            else -> chanceValues.add(
                ActionValue(
                    actionValue3!!.value * 100,
                    actionValue4!!.value * 100,
                    EActionValue.VALUE3,
                    EActionValue.VALUE4,
                    null
                )
            )
        }
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (charmType) {
            CharmType.Charm -> getString(
                R.string.Charm_s1_with_s2_chance_for_s3_sec,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, chanceValues, RoundingMode.UNNECESSARY, property),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
            )
            CharmType.Confusion -> getString(
                R.string.Confuse_s1_with_s2_chance_for_s3_sec,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, chanceValues, RoundingMode.UNNECESSARY, property),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}