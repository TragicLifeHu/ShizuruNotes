package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils.roundIfNeed
import java.math.RoundingMode

class ActionByHitCountAction : ActionParameter() {
    internal enum class ConditionType(val value: Int) {
        Unknown(0),
        Damage(1),
        Target(2),
        Hit(3),
        Critical(4);

        companion object {
            fun parse(value: Int): ConditionType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var conditionType: ConditionType? = null
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        conditionType = ConditionType.parse(actionDetail1)
        durationValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        val limitation: String = if (actionValue5!!.value > 0) {
            getString(
                R.string.max_s_times,
                roundIfNeed(actionValue5!!.value)
            )
        } else {
            ""
        }
        return when (conditionType) {
            ConditionType.Hit -> getString(
                R.string.Use_d1_s2_every_s3_hits_in_next_s4_sec,
                actionDetail2 % 10,
                limitation,
                roundIfNeed(actionValue1!!.value),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}