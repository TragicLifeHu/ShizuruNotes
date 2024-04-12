package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class SummonAction : ActionParameter() {
    internal enum class Side(val value: Int) {
        Unknown(-1), Ours(1), Other(2);

        fun description(): String {
            return when (this) {
                Ours -> getString(
                    R.string.own_side
                )
                Other -> getString(
                    R.string.opposite
                )
                else -> getString(R.string.unknown)
            }
        }

        companion object {
            fun parse(value: Int): Side {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    internal enum class UnitType(val value: Int) {
        Unknown(-1), Normal(1), Phantom(2);

        fun description(): String {
            return when (this) {
                Normal -> getString(R.string.normal_type)
                Phantom -> getString(R.string.phantom_type)
                else -> getString(R.string.unknown)
            }
        }

        companion object {
            fun parse(value: Int): UnitType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var side: Side? = null
    private var unitType: UnitType? = null
    override fun childInit() {
        side = Side.parse(actionDetail3)
        unitType = UnitType.parse(actionValue5!!.value.toInt())
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return if (actionValue7!!.value > 0) {
            getString(
                R.string.At_d1_in_front_of_s2_summon_a_minion_id_d3,
                actionValue7!!.value.toInt(),
                targetParameter!!.buildTargetClause(),
                actionDetail2
            )
        } else if (actionValue7!!.value < 0) {
            getString(
                R.string.At_d1_behind_of_s2_summon_a_minion_id_d3,
                -actionValue7!!.value.toInt(),
                targetParameter!!.buildTargetClause(),
                actionDetail2
            )
        } else {
            getString(
                R.string.At_the_position_of_s1_summon_a_minion_id_d2,
                targetParameter!!.buildTargetClause(), actionDetail2
            )
        }
    }
}