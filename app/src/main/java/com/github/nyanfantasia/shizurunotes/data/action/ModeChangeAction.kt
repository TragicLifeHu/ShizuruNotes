package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils


class ModeChangeAction : ActionParameter() {
    enum class ModeChangeType(val value: Int) {
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

    enum class AdditionalAbnormalType(val value: Int) {
        None(0), Flight(1);

        fun description(): String {
            return when (this) {
                Flight -> getString(R.string.flight)
                else -> getString(R.string.none)
            }
        }

        companion object {
            fun parse(value: Int): AdditionalAbnormalType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return None
            }
        }
    }

    private var modeChangeType: ModeChangeType? = null
    private var abnormalType: AdditionalAbnormalType? = null
    override fun childInit() {
        modeChangeType = ModeChangeType.parse(actionDetail1)
        abnormalType = AdditionalAbnormalType.parse(actionValue5!!.valueString().toInt())
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        val additionalDescription =
            if (abnormalType == AdditionalAbnormalType.None) "" else getString(
                R.string.Applies_s1_status_to_self,
                abnormalType!!.description()
            )
        return when (modeChangeType) {
            ModeChangeType.Time -> getString(
                R.string.Change_attack_pattern_to_d1_for_s2_sec,
                actionNum(actionDetail2),
                actionValue1!!.valueString()
            ) + additionalDescription

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