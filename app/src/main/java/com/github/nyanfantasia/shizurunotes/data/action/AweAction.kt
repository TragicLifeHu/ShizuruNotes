package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

class AweAction : ActionParameter() {
    enum class AweType(val value: Int) {
        Unknown(-1), UBOnly(0), UBAndSkill(1);

        companion object {
            fun parse(value: Int): AweType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var aweType: AweType? = null
    private var durationValues: MutableList<ActionValue> = ArrayList()
    private var percentValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        durationValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
        percentValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        aweType = AweType.parse(actionDetail1)
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (aweType) {
            AweType.UBAndSkill -> getString(
                R.string.Reduce_s1_damage_or_instant_healing_effect_of_union_burst_and_main_skills_cast_by_s2_for_s3_sec,
                buildExpression(level, percentValues, RoundingMode.UNNECESSARY, property),
                targetParameter!!.buildTargetClause(),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
            )
            AweType.UBOnly -> getString(
                R.string.Reduce_s1_damage_or_instant_healing_effect_of_union_burst_cast_by_s2_for_s3_sec,
                buildExpression(level, percentValues, RoundingMode.UNNECESSARY, property),
                targetParameter!!.buildTargetClause(),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}