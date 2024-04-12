package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.user.UserSettings
import com.github.nyanfantasia.shizurunotes.user.UserSettings.Companion.get
import java.math.RoundingMode

class RatioDamageAction : ActionParameter() {
    enum class HpType(val value: Int) {
        Unknown(0), Max(1), Current(2), OriginalMax(3);

        companion object {
            fun parse(value: Int): HpType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var hpType: HpType? = null
    override fun childInit() {
        super.childInit()
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        hpType = HpType.parse(actionDetail1)
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        var r = buildExpression(level, RoundingMode.UNNECESSARY, property)
        if (get().getExpression() != UserSettings.EXPRESSION_VALUE) {
            r = String.format("(%s)", r)
        }
        return when (hpType) {
            HpType.Max -> getString(
                R.string.Deal_damage_equal_to_s1_of_target_max_HP_to_s2,
                r, targetParameter!!.buildTargetClause()
            )
            HpType.Current -> getString(
                R.string.Deal_damage_equal_to_s1_of_target_current_HP_to_s2,
                r, targetParameter!!.buildTargetClause()
            )
            HpType.OriginalMax -> getString(
                R.string.Deal_damage_equal_to_s1_of_targets_original_max_HP_to_s2,
                r, targetParameter!!.buildTargetClause()
            )
            else -> super.localizedDetail(level, property)
        }
    }
}