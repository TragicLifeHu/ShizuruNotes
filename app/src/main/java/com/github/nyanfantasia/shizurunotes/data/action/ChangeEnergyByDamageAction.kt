package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property


class ChangeEnergyByDamageAction : ActionParameter() {
    override fun childInit() {
        super.childInit()
        actionValues.add(
            ActionValue(
                actionValue1!!.value,
                actionValue2!!.value,
                EActionValue.VALUE1,
                EActionValue.VALUE2,
                null
            )
        )
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        when (actionDetail1) {
            1 -> return getString(
                R.string.Adds_s1_marks_to_s5_max_s2_id_d3_lasts_for_s4_sec_removes_1_mark_while_taking_dmg_and_restores_s6_tp,
                actionValue3!!.valueString(),
                actionValue4!!.valueString(),
                actionDetail2,
                actionValue5!!.valueString(),
                targetParameter!!.buildTargetClause(),
                buildExpression(level, actionValues, null, property)
            )
        }
        return super.localizedDetail(level, property)
    }
}