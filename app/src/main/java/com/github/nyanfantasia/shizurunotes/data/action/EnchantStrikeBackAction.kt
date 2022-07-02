package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

class EnchantStrikeBackAction : BarrierAction() {

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (barrierType) {
            BarrierType.PhysicalGuard -> getString(
                R.string.Cast_a_barrier_on_s1_to_strike_back_s2_damage_when_taking_physical_damage,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, RoundingMode.CEILING, property),
                actionValue3!!.valueString()
            )
            BarrierType.MagicGuard -> getString(
                R.string.Cast_a_barrier_on_s1_to_strike_back_s2_damage_when_taking_magic_damage,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, RoundingMode.CEILING, property),
                actionValue3!!.valueString()
            )
            BarrierType.PhysicalDrain -> getString(
                R.string.Cast_a_barrier_on_s1_to_strike_back_s2_damage_and_recover_the_same_HP_when_taking_physical_damage,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, RoundingMode.CEILING, property),
                actionValue3!!.valueString()
            )
            BarrierType.MagicDrain -> getString(
                R.string.Cast_a_barrier_on_s1_to_strike_back_s2_damage_and_recover_the_same_hp_when_taking_magic_damage,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, RoundingMode.CEILING, property),
                actionValue3!!.valueString()
            )
            BarrierType.BothGuard -> getString(
                R.string.Cast_a_barrier_on_s1_to_strike_back_s2_damage_when_taking_damage,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, RoundingMode.CEILING, property),
                actionValue3!!.valueString()
            )
            BarrierType.BothDrain -> getString(
                R.string.Cast_a_barrier_on_s1_to_strike_back_s2_damage_and_recover_the_same_HP_when_taking_damage,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, RoundingMode.CEILING, property),
                actionValue3!!.valueString()
            )
            else -> super.localizedDetail(level, property)
        }
    }
}