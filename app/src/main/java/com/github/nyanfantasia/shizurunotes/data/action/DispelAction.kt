package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

class DispelAction : ActionParameter() {
    enum class DispelType(val value: Int) {
        Unknown(0), Buff(1), Debuff(2), Barriers(10);

        fun description(): String {
            return when (this) {
                Buff -> getString(R.string.buffs)
                Debuff -> getString(R.string.debuffs)
                Barriers -> getString(R.string.barriers)
                else -> getString(R.string.unknown)
            }
        }

        companion object {
            fun parse(value: Int): DispelType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var dispelType: DispelType? = null
    private var chanceValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        dispelType = DispelType.parse(actionDetail1)
        chanceValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.Clear_all_s1_on_s2_with_chance_s3,
            dispelType!!.description(),
            targetParameter!!.buildTargetClause(),
            buildExpression(level, chanceValues, RoundingMode.UNNECESSARY, property)
        )
    }
}