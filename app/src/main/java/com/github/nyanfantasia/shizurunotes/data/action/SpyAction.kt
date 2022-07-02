package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class SpyAction : ActionParameter() {
    enum class CancelType(val value: Int) {
        None(0), Damaged(1);

        companion object {
            fun parse(value: Int): CancelType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return None
            }
        }
    }

    private var cancelType: CancelType? = null
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        super.childInit()
        cancelType = CancelType.parse(actionDetail2)
        durationValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (cancelType) {
            CancelType.Damaged -> getString(
                R.string.Make_s1_invisible_for_s2_cancels_on_taking_damage,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, actionValues, null, property)
            )
            else -> super.localizedDetail(level, property)
        }
    }
}