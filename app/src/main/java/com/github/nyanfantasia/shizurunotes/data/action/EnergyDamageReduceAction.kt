package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode
import java.util.ArrayList

class EnergyDamageReduceAction : ActionParameter() {
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        durationValues.add(ActionValue(actionValue2!!, actionValue3!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return I18N.getString(
            R.string.Reduces_incoming_energy_damage_down_to_s1_percent_of_s2_for_s3_sec,
            actionValue1!!.valueString(),
            targetParameter!!.buildTargetClause(),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        )
    }
}