package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils.roundDouble

open class BarrierAction : ActionParameter() {
    enum class BarrierType(val value: Int) {
        Unknown(0),
        PhysicalGuard(1),
        MagicGuard(2),
        PhysicalDrain(3),
        MagicDrain(4),
        BothGuard(5),
        BothDrain(6);

        companion object {
            fun parse(value: Int): BarrierType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    protected var barrierType: BarrierType? = null
    override fun childInit() {
        barrierType = BarrierType.parse(actionDetail1)
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (barrierType) {
            BarrierType.PhysicalGuard -> getString(
                R.string.Cast_a_barrier_on_s1_to_nullify_s2_physical_damage_for_s3_sec,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, property),
                roundDouble(actionValue3!!.value)
            )
            BarrierType.MagicGuard -> getString(
                R.string.Cast_a_barrier_on_s1_to_nullify_s2_magic_damage_for_s3_sec,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, property),
                roundDouble(actionValue3!!.value)
            )
            BarrierType.PhysicalDrain -> getString(
                R.string.Cast_a_barrier_on_s1_to_absorb_s2_physical_damage_for_s3_sec,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, property),
                roundDouble(actionValue3!!.value)
            )
            BarrierType.MagicDrain -> getString(
                R.string.Cast_a_barrier_on_s1_to_absorb_s2_magic_damage_for_s3_sec,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, property),
                roundDouble(actionValue3!!.value)
            )
            BarrierType.BothDrain -> getString(
                R.string.Cast_a_barrier_on_s1_to_absorb_s2_physical_and_magic_damage_for_s3_sec,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, property),
                roundDouble(actionValue3!!.value)
            )
            BarrierType.BothGuard -> getString(
                R.string.Cast_a_barrier_on_s1_to_nullify_s2_physical_and_magic_damage_for_s3_sec,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, property),
                roundDouble(actionValue3!!.value)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}