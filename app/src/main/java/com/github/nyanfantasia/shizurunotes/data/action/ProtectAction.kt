package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode


class ProtectAction : ActionParameter() {
    private var counterValues: MutableList<ActionValue> = ArrayList()
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        counterValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        durationValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.Protect_s1_from_certain_skill_max_s2_for_s3_sec,
            targetParameter!!.buildTargetClause(),
            buildExpression(level, counterValues, RoundingMode.FLOOR, property),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        )
    }
}