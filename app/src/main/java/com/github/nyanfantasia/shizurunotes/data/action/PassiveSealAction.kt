package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

internal class PassiveSealAction : ActionParameter() {
    private var sealNumLimit = 0
    private var sealDuration: MutableList<ActionValue> = ArrayList()
    private var lifeTime: MutableList<ActionValue> = ArrayList()
    private var passiveTiming: EPassiveTiming? = null
    private var sealTarget: ESealTarget? = null
    override fun childInit() {
        sealNumLimit = actionValue1!!.value.toInt()
        sealDuration.add(ActionValue(actionValue3!!, actionValue4!!, null))
        lifeTime.add(ActionValue(actionValue5!!, actionValue6!!, null))
        passiveTiming = EPassiveTiming.parse(actionDetail1)
        sealTarget = ESealTarget.parse(actionDetail3)
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.Passive_Whenever_s1_get_s2_seals_s3_with_d4_marks_id_d5_for_s6_sec_caps_at_d7_This_passive_skill_will_listen_for_s8_sec,
            targetParameter!!.buildTargetClause(),
            passiveTiming!!.description(),
            sealTarget!!.description(),
            actionDetail2,
            actionValue2!!.value.toInt(),
            buildExpression(level, sealDuration, RoundingMode.UNNECESSARY, property),
            actionValue1!!.value.toInt(),
            buildExpression(level, lifeTime, RoundingMode.UNNECESSARY, property)
        )
    }

    private enum class EPassiveTiming(val value: Int) {
        Unknown(-1), Buff(1), Damage(2);

        fun description(): String {
            return when (this) {
                Buff -> getString(R.string.buffs)
                Damage -> getString(R.string.damage)
                else -> getString(R.string.Unknown)
            }
        }

        companion object {
            fun parse(value: Int): EPassiveTiming {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private enum class ESealTarget(val value: Int) {
        Unknown(-1), Self(0);

        fun description(): String {
            return when (this) {
                Self -> getString(R.string.self)
                else -> getString(R.string.Unknown)
            }
        }

        companion object {
            fun parse(value: Int): ESealTarget {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }
}