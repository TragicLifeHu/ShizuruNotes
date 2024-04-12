package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils.roundDownDouble

class MoveAction : ActionParameter() {
    internal enum class MoveType(val value: Int) {
        Unknown(0),
        TargetReturn(1),
        AbsoluteReturn(2),
        Target(3),
        Absolute(4),
        TargetByVelocity(5),
        AbsoluteByVelocity(6),
        AbsoluteWithoutDirection(7);

        companion object {
            fun parse(value: Int): MoveType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var moveType: MoveType? = null
    override fun childInit() {
        moveType = MoveType.parse(actionDetail1)
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (moveType) {
            MoveType.TargetReturn -> getString(
                R.string.Change_self_position_to_s_then_return,
                targetParameter!!.buildTargetClause()
            )
            MoveType.AbsoluteReturn -> if (actionValue1!!.value > 0) getString(
                R.string.Change_self_position_s_forward_then_return,
                roundDownDouble(
                    actionValue1!!.value
                )
            ) else getString(
                R.string.Change_self_position_s_backward_then_return,
                roundDownDouble(-actionValue1!!.value)
            )
            MoveType.Target -> getString(
                R.string.Change_self_position_to_s,
                targetParameter!!.buildTargetClause()
            )
            MoveType.Absolute, MoveType.AbsoluteWithoutDirection -> if (actionValue1!!.value > 0) getString(
                R.string.Change_self_position_s_forward,
                roundDownDouble(
                    actionValue1!!.value
                )
            ) else getString(
                R.string.Change_self_position_s_backward,
                roundDownDouble(-actionValue1!!.value)
            )
            MoveType.TargetByVelocity -> if (actionValue1!!.value > 0) getString(
                R.string.Move_to_s1_in_front_of_s2_with_velocity_s3_sec,
                roundDownDouble(
                    actionValue1!!.value
                ),
                targetParameter!!.buildTargetClause(),
                actionValue2!!.valueString()
            ) else getString(
                R.string.Move_to_s1_behind_of_s2_with_velocity_s3_sec,
                roundDownDouble(-actionValue1!!.value),
                targetParameter!!.buildTargetClause(),
                actionValue2!!.valueString()
            )
            MoveType.AbsoluteByVelocity -> if (actionValue1!!.value > 0) getString(
                R.string.Move_forward_s1_with_velocity_s2_sec,
                roundDownDouble(
                    actionValue1!!.value
                ),
                actionValue2!!.valueString()
            ) else getString(
                R.string.Move_backward_s1_with_velocity_s2_sec,
                roundDownDouble(-actionValue1!!.value),
                actionValue2!!.valueString()
            )
            else -> super.localizedDetail(level, property)
        }
    }
}