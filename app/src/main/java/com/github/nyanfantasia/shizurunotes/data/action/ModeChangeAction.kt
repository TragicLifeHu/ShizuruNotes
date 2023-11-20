package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils

class ModeChangeAction : ActionParameter() {
    internal enum class ModeChangeType(val value: Int) {
        Unknown(0), Time(1), Energy(2), Release(3);

        companion object {
            fun parse(value: Int): ModeChangeType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var modeChangeType: ModeChangeType? = null
    override fun childInit() {
        modeChangeType = ModeChangeType.parse(actionDetail1)
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (modeChangeType) {
            ModeChangeType.Time -> getString(
                R.string.Change_attack_pattern_to_d1_for_s2_sec,
                actionNum(actionDetail2), actionValue1!!.valueString()
            )
            ModeChangeType.Energy -> getString(
                R.string.Cost_s1_TP_sec_change_attack_pattern_to_d2_until_TP_is_zero,
                Utils.roundDownDouble(actionValue1!!.value),
                actionNum(actionDetail2)
            )
            ModeChangeType.Release -> getString(
                R.string.Change_attack_pattern_back_to_d_after_effect_over,
                actionNum(actionDetail2)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}