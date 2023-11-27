package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode


class EveryAttackCriticalAction : ActionParameter() {
    enum class CriticalType(val value: Int) {
        Physical(1),
        Magic(2),
        Both(3);

        fun description(): String {
            return when (this) {
                Physical -> getString(R.string.physical)
                Magic -> getString(R.string.magic)
                Both -> getString(R.string.both)
            }
        }

        companion object {
            fun parse(value: Int): CriticalType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return Physical
            }
        }
    }

    private var criticalType: CriticalType? = null
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        criticalType = CriticalType.parse(actionDetail1)
        durationValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.Make_s1_dmg_dealt_by_self_promised_to_get_critical_for_s2_sec,
            criticalType!!.description(),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        )
    }
}