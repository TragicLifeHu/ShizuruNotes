package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.PropertyKey

class KnightGuardAction : ActionParameter() {
    enum class GuardType(val value: Int) {
        Physics(1), Magic(2);

        companion object {
            fun parse(value: Int): GuardType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return Physics
            }
        }
    }

    private var guardType: GuardType? = null
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        guardType = GuardType.parse(actionValue1!!.value.toInt())
        when (guardType) {
            GuardType.Magic -> {
                actionValues.add(ActionValue(actionValue4!!, actionValue5!!, PropertyKey.MagicStr))
                actionValues.add(ActionValue(actionValue2!!, actionValue3!!, null))
            }
            GuardType.Physics -> {
                actionValues.add(ActionValue(actionValue4!!, actionValue5!!, PropertyKey.Atk))
                actionValues.add(ActionValue(actionValue2!!, actionValue3!!, null))
            }
            else -> {
                actionValues.add(ActionValue(actionValue4!!, actionValue5!!, PropertyKey.Atk))
                actionValues.add(ActionValue(actionValue2!!, actionValue3!!, null))
            }
        }
        durationValues.add(ActionValue(actionValue6!!, actionValue7!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.When_s1_HP_reaches_0_restore_s2_HP_once_in_next_s3_sec,
            targetParameter!!.buildTargetClause(),
            buildExpression(level, property),
            buildExpression(level, durationValues, null, property)
        )
    }
}