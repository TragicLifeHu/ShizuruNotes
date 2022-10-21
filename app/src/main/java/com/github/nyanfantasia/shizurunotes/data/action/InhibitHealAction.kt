package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.utils.Utils
import java.math.RoundingMode

class InhibitHealAction : ActionParameter() {
    enum class InhibitType(val value: Int) {
        Inhibit(0),
        Decrease(1);

        companion object {
            fun parse(value: Int): InhibitType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return Inhibit
            }
        }
    }

    protected var durationValues: MutableList<ActionValue> = ArrayList()
    protected var inhibitType: InhibitType? = null
    override fun childInit() {
        super.childInit()
        actionValues.add(ActionValue(actionValue1!!, actionValue4!!, null))
        durationValues.add(ActionValue(actionValue2!!, actionValue3!!, null))
        inhibitType = InhibitType.parse(actionDetail1)
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (inhibitType) {
            InhibitType.Inhibit -> getString(
                R.string.When_s1_receive_healing_deal_s2_healing_amount_damage_instead_last_for_s3_sec_or_unlimited_time_if_triggered_by_field,
                targetParameter!!.buildTargetClause(),
                actionValue1!!.valueString(),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
            )
            InhibitType.Decrease -> getString(
                R.string.Decreases_s1_healing_received_by_s2_last_for_s3_sec_or_unlimited_time_if_triggered_by_field,
                Utils.roundIfNeed(actionValue1!!.value * 100),
                targetParameter!!.buildTargetClause(),
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}