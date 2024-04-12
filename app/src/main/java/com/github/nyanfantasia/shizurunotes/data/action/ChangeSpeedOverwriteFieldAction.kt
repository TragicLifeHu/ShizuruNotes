package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils
import java.math.RoundingMode


class ChangeSpeedOverwriteFieldAction : ActionParameter() {
    private enum class SpeedChangeType(val value: Int) {
        Slow(1), Haste(2);

        fun description(): String {
            return if (value == 1) {
                I18N.getString(R.string.Reduce)
            } else {
                I18N.getString(R.string.Raise)
            }
        }

        companion object {
            fun parse(value: Int): SpeedChangeType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Haste
            }
        }
    }

    private var durationValues: MutableList<ActionValue> = ArrayList()
    private var speedChangeType: SpeedChangeType? = null
    override fun childInit() {
        super.childInit()
        speedChangeType = SpeedChangeType.parse(actionDetail1)
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        durationValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return I18N.getString(
            R.string.Deploys_a_filed_of_radius_d1_which_s2_attack_speed_of_s3_for_s4_sec,
            actionValue5!!.value.toInt(),
            speedChangeType!!.description(),
            Utils.roundDouble(
                buildExpression(
                    level,
                    RoundingMode.UNNECESSARY,
                    property,
                    true
                ).toDouble() * 100
            ),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        )
    }
}
