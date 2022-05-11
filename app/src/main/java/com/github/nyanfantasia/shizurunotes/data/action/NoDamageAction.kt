package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

@Suppress("EnumEntryName")
class NoDamageAction : ActionParameter() {
    internal enum class NoDamageType(val value: Int) {
        unknown(0),
        noDamage(1),
        dodgePhysics(2),
        dodgeAll(3),
        abnormal(4),
        debuff(5),
        Break(6);

        companion object {
            fun parse(value: Int): NoDamageType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return unknown
            }
        }
    }

    private var noDamageType: NoDamageType? = null
    override fun childInit() {
        noDamageType = NoDamageType.parse(actionDetail1)
        actionValues.add(ActionValue(actionValue1, actionValue2, null))
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return when (noDamageType) {
            NoDamageType.noDamage -> getString(
                R.string.Make_s1_to_be_invulnerable_for_s2_sec,
                targetParameter.buildTargetClause(),
                buildExpression(level, RoundingMode.UNNECESSARY, property)
            )
            NoDamageType.dodgePhysics -> getString(
                R.string.Make_s1_to_be_invulnerable_to_physical_damage_for_s2_sec,
                targetParameter.buildTargetClause(),
                buildExpression(level, RoundingMode.UNNECESSARY, property)
            )
            NoDamageType.Break -> getString(
                R.string.Make_s1_to_be_invulnerable_to_break_for_s2_sec,
                targetParameter.buildTargetClause(),
                buildExpression(level, RoundingMode.UNNECESSARY, property)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}