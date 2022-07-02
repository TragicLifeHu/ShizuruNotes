package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

class NoDamageAction : ActionParameter() {
    internal enum class NoDamageType(val value: Int) {
        Unknown(0),
        NoDamage(1),
        DodgePhysics(2),
        DodgeAll(3),
        Abnormal(4),
        Debuff(5),
        Break(6);

        companion object {
            fun parse(value: Int): NoDamageType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var noDamageType: NoDamageType? = null
    override fun childInit() {
        noDamageType = NoDamageType.parse(actionDetail1)
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (noDamageType) {
            NoDamageType.NoDamage -> getString(
                R.string.Make_s1_to_be_invulnerable_for_s2_sec,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, RoundingMode.UNNECESSARY, property)
            )
            NoDamageType.DodgePhysics -> getString(
                R.string.Make_s1_to_be_invulnerable_to_physical_damage_for_s2_sec,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, RoundingMode.UNNECESSARY, property)
            )
            NoDamageType.Break -> getString(
                R.string.Make_s1_to_be_invulnerable_to_break_for_s2_sec,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, RoundingMode.UNNECESSARY, property)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}