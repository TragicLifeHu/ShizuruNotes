package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode


class EnvironmentAction : ActionParameter() {
    enum class EnvironmentType(val value: Int) {
        Unknown(-1), Thundering(137);

        fun description(): String {
            return when (this) {
                Thundering -> getString(R.string.thundering)
                else -> getString(R.string.Unknown)
            }
        }

        companion object {
            fun parse(value: Int): EnvironmentType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var environmentType: EnvironmentType? = null
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        environmentType = EnvironmentType.parse(actionDetail2)
        durationValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.Summon_a_field_of_s1_environment_for_s2_for_s3_sec,
            environmentType!!.description(),
            targetParameter!!.buildTargetClause(),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        )
    }
}