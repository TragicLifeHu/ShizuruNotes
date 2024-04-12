package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class KnockAction : ActionParameter() {
    internal enum class KnockType(val value: Int) {
        Unknown(0),
        UpDown(1),
        Up(2),
        Back(3),
        MoveTarget(4),
        MoveTargetParabolic(5),
        BackLimited(6),
        DragForwardCaster(
            8
        ),
        KnockBackGiveValue(9);

        companion object {
            fun parse(value: Int): KnockType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var knockType: KnockType? = null
    override fun childInit() {
        knockType = KnockType.parse(actionDetail1)
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (knockType) {
            KnockType.UpDown -> getString(
                R.string.Knock_s1_up_d2,
                targetParameter!!.buildTargetClause(),
                actionValue1!!.value.toInt()
            )
            KnockType.Back, KnockType.BackLimited -> if (actionValue1!!.value >= 0) getString(
                R.string.Knock_s1_away_d2,
                targetParameter!!.buildTargetClause(),
                actionValue1!!.value.toInt()
            ) else getString(
                R.string.Draw_s1_toward_self_d2,
                targetParameter!!.buildTargetClause(),
                -actionValue1!!.value.toInt()
            )
            KnockType.DragForwardCaster -> getString(
                R.string.drag_s1_to_a_position_s2_forward_of_the_caster,
                targetParameter!!.buildTargetClause(),
                actionValue1!!.value.toInt()
            )
            KnockType.KnockBackGiveValue -> getString(
                R.string.Knock_s1_away_s2,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, property)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}