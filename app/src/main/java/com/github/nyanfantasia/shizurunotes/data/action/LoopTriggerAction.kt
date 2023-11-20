package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class LoopTriggerAction : ActionParameter() {
    enum class TriggerType(val value: Int) {
        Unknown(0),
        Dodge(1),
        Damaged(2),
        Hp(3),
        Dead(4),
        CriticalDamaged(5),
        GetCriticalDamagedWithSummon(6);

        companion object {
            fun parse(value: Int): TriggerType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var triggerType: TriggerType? = null
    override fun childInit() {
        super.childInit()
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        triggerType = TriggerType.parse(actionDetail1)
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (triggerType) {
            TriggerType.Damaged -> getString(
                R.string.Condition_s1_chance_use_d2_when_takes_damage_within_s3_sec,
                buildExpression(level, property), actionNum(actionDetail2), actionValue4!!.valueString()
            )
            else -> super.localizedDetail(level, property)
        }
    }
}