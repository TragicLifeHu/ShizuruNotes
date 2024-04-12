package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class DamageCutAction : ActionParameter() {
    enum class DamageType(val value: Int) {
        Physical(1), Magic(2), All(3);

        companion object {
            fun parse(value: Int): DamageType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return All
            }
        }
    }

    private var damageType: DamageType? = null
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        damageType = DamageType.parse(actionDetail1)
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        durationValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (damageType) {
            DamageType.Physical -> getString(
                R.string.Reduce_s1_physical_damage_taken_by_s2_for_s3_sec,
                buildExpression(level, actionValues, null, property),
                targetParameter!!.buildTargetClause(),
                buildExpression(level, durationValues, null, property)
            )
            DamageType.Magic -> getString(
                R.string.Reduce_s1_magic_damage_taken_by_s2_for_s3_sec,
                buildExpression(level, actionValues, null, property),
                targetParameter!!.buildTargetClause(),
                buildExpression(level, durationValues, null, property)
            )
            DamageType.All -> getString(
                R.string.Reduce_s1_all_damage_taken_by_s2_for_s3_sec,
                buildExpression(level, actionValues, null, property),
                targetParameter!!.buildTargetClause(),
                buildExpression(level, durationValues, null, property)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}