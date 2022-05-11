package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

@Suppress("EnumEntryName")
class SummonAction : ActionParameter() {
    internal enum class Side(val value: Int) {
        unknown(-1), ours(1), other(2);

        fun description(): String {
            return when (this) {
                ours -> getString(
                    R.string.own_side
                )
                other -> getString(
                    R.string.opposite
                )
                else -> getString(R.string.unknown)
            }
        }

        companion object {
            fun parse(value: Int): Side {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return unknown
            }
        }
    }

    internal enum class UnitType(val value: Int) {
        unknown(-1), normal(1), phantom(2);

        fun description(): String {
            return when (this) {
                normal -> getString(R.string.normal_type)
                phantom -> getString(R.string.phantom_type)
                else -> getString(R.string.unknown)
            }
        }

        companion object {
            fun parse(value: Int): UnitType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return unknown
            }
        }
    }

    private var side: Side? = null
    private var unitType: UnitType? = null
    override fun childInit() {
        side = Side.parse(actionDetail3)
        unitType = UnitType.parse(actionValue5.value.toInt())
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return if (actionValue7.value > 0) {
            getString(
                R.string.At_d1_in_front_of_s2_summon_a_minion_id_d3,
                actionValue7.value.toInt(),
                targetParameter.buildTargetClause(),
                actionDetail2
            )
        } else if (actionValue7.value < 0) {
            getString(
                R.string.At_d1_behind_of_s2_summon_a_minion_id_d3,
                -actionValue7.value.toInt(),
                targetParameter.buildTargetClause(),
                actionDetail2
            )
        } else {
            getString(
                R.string.At_the_position_of_s1_summon_a_minion_id_d2,
                targetParameter.buildTargetClause(), actionDetail2
            )
        }
    }
}