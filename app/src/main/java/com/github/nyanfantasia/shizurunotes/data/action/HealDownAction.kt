package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.action.PercentModifier.Companion.parse
import java.math.RoundingMode

internal class HealDownAction : ActionParameter() {
    private var percentModifier: PercentModifier? = null
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        percentModifier = parse(actionValue1!!.value.toInt())
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        durationValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.Multiple_heal_effects_from_s1_with_s2_for_s3_sec,
            targetParameter!!.buildTargetClause(),
            buildExpression(level, RoundingMode.UNNECESSARY, property),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        )
    }
}