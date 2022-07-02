package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils.roundDouble

class ChangePatternAction : ActionParameter() {
    override fun childInit() {
        super.childInit()
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (actionDetail1) {
            1 -> if (actionValue1!!.value > 0) {
                getString(
                    R.string.Change_attack_pattern_to_d1_for_s2_sec,
                    actionDetail2 % 10, roundDouble(
                        actionValue1!!.value
                    )
                )
            } else {
                getString(
                    R.string.Change_attack_pattern_to_d,
                    actionDetail2 % 10
                )
            }
            2 -> getString(
                R.string.Change_skill_visual_effect_for_s_sec,
                roundDouble(actionValue1!!.value)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}