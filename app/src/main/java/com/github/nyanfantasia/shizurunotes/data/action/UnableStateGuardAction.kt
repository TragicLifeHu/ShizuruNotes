package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

class UnableStateGuardAction : ActionParameter() {
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        durationValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        var amount = buildExpression(level, property)
        if (amount.toInt() < 0) {
            amount = (Int.MAX_VALUE.toLong() - Int.MIN_VALUE + amount.toInt()).toString()
        }
        return I18N.getString(
            R.string.Enable_s1_to_resist_all_sorts_of_incapacity_efficacies_up_to_s2_times_in_a_period_of_s3_sec,
            targetParameter!!.buildTargetClause(),
            amount,
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        )
    }
}
