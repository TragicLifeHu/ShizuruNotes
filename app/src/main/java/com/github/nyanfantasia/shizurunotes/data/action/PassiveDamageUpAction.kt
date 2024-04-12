package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils.roundIfNeed

internal class PassiveDamageUpAction : ActionParameter() {
    private var debuffDamageUpValue = 0.0
    private var debuffDamageUpLimitValue = 0.0
    private var debuffDamageUpTimer = 0.0
    private var countType: ECountType? = null
    private var effectType: EEffectType? = null
    override fun childInit() {
        debuffDamageUpLimitValue = actionValue2!!.value
        debuffDamageUpValue = actionValue1!!.value
        debuffDamageUpTimer = actionValue3!!.value
        countType = ECountType.parse(actionDetail1)
        effectType = EEffectType.parse(actionDetail2)
        actionValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (countType) {
            ECountType.Debuff -> getString(
                R.string.Make_s1_damage_changes_into_s234_times_caps_to_s5_times_dur_s6_sec,
                targetParameter!!.buildTargetClause(),
                effectType!!.description(),
                roundIfNeed(debuffDamageUpValue),
                countType!!.description(),
                roundIfNeed(debuffDamageUpLimitValue),
                buildExpression(level, property)
            )
            else -> localizedDetail(level, property)
        }
    }

    private enum class ECountType(val value: Int) {
        Unknown(-1), Debuff(1);

        fun description(): String {
            return when (this) {
                Debuff -> getString(R.string.debuff)
                else -> getString(R.string.Unknown)
            }
        }

        companion object {
            fun parse(value: Int): ECountType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private enum class EEffectType(val value: Int) {
        Unknown(-1), Add(1), Subtract(2);

        fun description(): String {
            return when (this) {
                Add -> getString(R.string.add)
                Subtract -> getString(R.string.subtract)
                else -> getString(R.string.Unknown)
            }
        }

        companion object {
            fun parse(value: Int): EEffectType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }
}