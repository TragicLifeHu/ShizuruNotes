package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

class CountBlindAction : ActionParameter() {
    enum class CountType(val value: Int) {
        Unknown(-1), Time(1), Count(2);

        companion object {
            fun parse(value: Int): CountType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var countType: CountType? = null
    override fun childInit() {
        countType = CountType.parse(actionValue1!!.value.toInt())
        actionValues.add(ActionValue(actionValue2!!, actionValue3!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (countType) {
            CountType.Time -> getString(
                R.string.In_nex_s1_sec_s2_physical_attacks_will_miss,
                buildExpression(level, RoundingMode.UNNECESSARY, property),
                targetParameter!!.buildTargetClause()
            )
            CountType.Count -> getString(
                R.string.In_next_s1_attacks_s2_physical_attacks_will_miss,
                buildExpression(level, property), targetParameter!!.buildTargetClause()
            )
            else -> super.localizedDetail(level, property)
        }
    }
}