package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils.roundDouble
import java.math.RoundingMode

class LogBarrierAction : ActionParameter() {
    enum class BarrierType(val value: Int) {
        Physical(1), Magic(2), All(3);

        companion object {
            fun parse(value: Int): BarrierType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return All
            }
        }
    }

    private var barrierType: BarrierType? = null
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        barrierType = BarrierType.parse(
            actionValue1!!.value.toInt()
        )
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        durationValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.Cast_a_barrier_on_s1_to_reduce_damage_over_s2_with_coefficient_s3_the_greater_the_less_reduced_amount_for_s4_s,
            targetParameter!!.buildTargetClause(),
            roundDouble(actionValue5!!.value),
            buildExpression(level, RoundingMode.UNNECESSARY, property),
            buildExpression(level, durationValues, null, property)
        )
    }
}