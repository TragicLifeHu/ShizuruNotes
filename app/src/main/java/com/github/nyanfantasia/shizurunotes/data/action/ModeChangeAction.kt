package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils

@Suppress("EnumEntryName")
class ModeChangeAction : ActionParameter() {
    internal enum class ModeChangeType(val value: Int) {
        unknown(0), time(1), energy(2), release(3);

        companion object {
            fun parse(value: Int): ModeChangeType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return unknown
            }
        }
    }

    private var modeChangeType: ModeChangeType? = null
    override fun childInit() {
        modeChangeType = ModeChangeType.parse(actionDetail1)
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return when (modeChangeType) {
            ModeChangeType.time -> getString(
                R.string.Change_attack_pattern_to_d1_for_s2_sec,
                actionDetail2 % 10, actionValue1.valueString()
            )
            ModeChangeType.energy -> getString(
                R.string.Cost_s1_TP_sec_change_attack_pattern_to_d2_until_TP_is_zero,
                Utils.roundDownDouble(actionValue1.value),
                actionDetail2 % 10
            )
            ModeChangeType.release -> getString(
                R.string.Change_attack_pattern_back_to_d_after_effect_over,
                actionDetail2 % 10
            )
            else -> super.localizedDetail(level, property)
        }
    }
}