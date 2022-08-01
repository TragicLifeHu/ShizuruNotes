package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.action.IfType.Companion.parse

class IfForAllAction : ActionParameter() {
    private var trueClause: String? = null
    private var falseClause: String? = null
    private var ifType: IfType? = null
    override fun childInit() {
        ifType = parse(actionDetail1)
        if (actionDetail2 != 0) {
            if (actionDetail1 == 710 || actionDetail1 == 100 || actionDetail1 == 1700 || actionDetail1 == 1601) {
                val ifType = parse(actionDetail1)
                trueClause = getString(
                    R.string.use_d1_to_s2_if_s3,
                    actionDetail2 % 100,
                    targetParameter!!.buildTargetClause(true),
                    ifType.description()
                )
            } else if (actionDetail1 in 0..99) {
                trueClause = getString(
                    R.string.d1_chance_use_d2,
                    actionDetail1, actionDetail2 % 10
                )
            } else if (actionDetail1 in 500..512) {
                trueClause = getString(
                    R.string.use_d1_if_s2_has_s3,
                    actionDetail2 % 10,
                    targetParameter!!.buildTargetClause(),
                    ifType!!.description()
                )
            } else if (actionDetail1 == 599) {
                trueClause = getString(
                    R.string.use_d1_if_s2_has_any_dot_debuff,
                    actionDetail2 % 10, targetParameter!!.buildTargetClause()
                )
            } else if (actionDetail1 in 600..699) {
                trueClause = getString(
                    R.string.use_d1_if_s2_is_in_state_of_ID_d3_with_stacks_greater_than_or_equal_to_d4,
                    actionDetail2 % 10,
                    targetParameter!!.buildTargetClause(true),
                    actionDetail1 - 600,
                    actionValue3!!.value.toInt()
                )
            } else if (actionDetail1 == 700) {
                trueClause = getString(
                    R.string.use_d1_if_s2_is_alone,
                    actionDetail2 % 10, targetParameter!!.buildTargetClause(true)
                )
            } else if (actionDetail1 in 701..709) {
                trueClause = getString(
                    R.string.use_d1_if_the_count_of_s2_except_stealth_units_is_d3,
                    actionDetail2 % 10, targetParameter!!.buildTargetClause(), actionDetail1 - 700
                )
            } else if (actionDetail1 == 720) {
                trueClause = getString(
                    R.string.use_d1_if_among_s2_exists_unit_ID_d3,
                    actionDetail2 % 10,
                    targetParameter!!.buildTargetClause(),
                    actionValue3!!.value.toInt()
                )
            } else if (actionDetail1 == 721) {
                trueClause = getString(
                    R.string.use_d1_to_s2_in_state_of_ID_d3,
                    actionDetail2 % 10,
                    targetParameter!!.buildTargetClause(true),
                    actionValue3!!.value.toInt()
                )
            } else if (actionDetail1 in 901..999) {
                trueClause = getString(
                    R.string.use_d1_if_s2_HP_is_below_d3,
                    actionDetail2 % 10,
                    targetParameter!!.buildTargetClause(true),
                    actionDetail1 - 900
                )
            } else if (actionDetail1 == 1000) {
                trueClause = getString(
                    R.string.if_target_is_defeated_by_the_last_effect_then_use_d,
                    actionDetail2 % 10
                )
            } else if (actionDetail1 == 1001) {
                trueClause = getString(
                    R.string.Use_s_if_this_skill_get_critical,
                    actionDetail2 % 10
                )
            } else if (actionDetail1 in 1200..1299) {
                trueClause = getString(
                    R.string.counter_d3_is_greater_than_or_equal_to_d1_then_use_d2,
                    actionDetail1 % 10, actionDetail2 % 10, actionDetail1 % 100 / 10
                )
            } else if(actionDetail1 == 1800){
                trueClause = getString(
                    R.string.Performs_d1_if_s2_is_a_multi_target_unit,
                    actionDetail2 % 10, targetParameter!!.buildTargetClause()
                )
            } else if (actionDetail1 in 6000..6999 && actionValue3!!.value == 0.0) {
                trueClause = getString(
                    R.string.use_d1_to_s2_in_state_of_ID_d3,
                    actionDetail2 % 10,
                    targetParameter!!.buildTargetClause(true),
                    actionDetail1 - 6000
                )
            } else if (actionDetail1 in 6000..6999) {
                trueClause = getString(
                    R.string.use_d1_if_s2_is_in_state_of_ID_d3_with_stacks_greater_than_or_equal_to_d4,
                    actionDetail2 % 10,
                    targetParameter!!.buildTargetClause(true),
                    actionDetail1 - 6000,
                    actionValue3!!.value.toInt()
                )
            }
        } else if (actionDetail3 == 0) {
            trueClause = getString(R.string.no_effect)
        }
        if (actionDetail3 != 0) {
            if (actionDetail1 == 710 || actionDetail1 == 100 || actionDetail1 == 1700 || actionDetail1 == 1601) {
                val ifType = parse(actionDetail1)
                falseClause = getString(
                    R.string.use_d1_to_s2_if_not_s3,
                    actionDetail3 % 100,
                    targetParameter!!.buildTargetClause(true),
                    ifType.description()
                )
            } else if (actionDetail1 in 0..99) {
                falseClause = getString(
                    R.string.d1_chance_use_d2,
                    100 - actionDetail1, actionDetail3 % 10
                )
            } else if (actionDetail1 in 500..512) {
                falseClause = getString(
                    R.string.use_d1_if_s2_does_not_have_s3,
                    actionDetail3 % 10,
                    targetParameter!!.buildTargetClause(),
                    ifType!!.description()
                )
            } else if (actionDetail1 == 599) {
                falseClause = getString(
                    R.string.use_d1_if_s2_has_no_dot_debuff,
                    actionDetail3 % 10, targetParameter!!.buildTargetClause()
                )
            } else if (actionDetail1 in 600..699) {
                falseClause = getString(
                    R.string.use_d1_if_s2_is_not_in_state_of_ID_d3_with_stacks_greater_than_or_equal_to_d4,
                    actionDetail3 % 10,
                    targetParameter!!.buildTargetClause(true),
                    actionDetail1 - 600,
                    actionValue3!!.value.toInt()
                )
            } else if (actionDetail1 == 700) {
                falseClause = getString(
                    R.string.use_d1_if_s2_is_not_alone,
                    actionDetail3 % 10, targetParameter!!.buildTargetClause(true)
                )
            } else if (actionDetail1 in 701..709) {
                falseClause = getString(
                    R.string.use_d1_if_the_count_of_s2_except_stealth_units_is_not_d3,
                    actionDetail3 % 10, targetParameter!!.buildTargetClause(), actionDetail1 - 700
                )
            } else if (actionDetail1 == 720) {
                falseClause = getString(
                    R.string.use_d1_if_among_s2_does_not_exists_unit_ID_d3,
                    actionDetail3 % 10,
                    targetParameter!!.buildTargetClause(),
                    actionValue3!!.value.toInt()
                )
            } else if (actionDetail1 == 721) {
                falseClause = getString(
                    R.string.use_d1_to_s2_if_not_in_state_of_ID_d3,
                    actionDetail3 % 10,
                    targetParameter!!.buildTargetClause(true),
                    actionValue3!!.value.toInt()
                )
            } else if (actionDetail1 in 901..999) {
                falseClause = getString(
                    R.string.use_d1_if_s2_HP_is_not_below_d3,
                    actionDetail3 % 10,
                    targetParameter!!.buildTargetClause(true),
                    actionDetail1 - 900
                )
            } else if (actionDetail1 == 1000) {
                falseClause = getString(
                    R.string.if_target_is_not_defeated_by_the_last_effect_then_use_d,
                    actionDetail3 % 10
                )
            } else if (actionDetail1 == 1001) {
                falseClause = getString(
                    R.string.Use_s_if_this_skill_not_get_critical,
                    actionDetail3 % 10
                )
            } else if (actionDetail1 in 1200..1299) {
                falseClause = getString(
                    R.string.counter_d3_is_less_than_d1_then_use_d2,
                    actionDetail1 % 10, actionDetail3 % 10, actionDetail1 % 100 / 10
                )
            } else if(actionDetail1 == 1800){
                trueClause = getString(
                    R.string.Performs_d1_if_s2_is_not_a_multi_target_unit,
                    actionDetail3 % 10, targetParameter!!.buildTargetClause()
                )
            } else if (actionDetail1 in 6000..6999 && actionValue3!!.value == 0.0) {
                falseClause = getString(
                    R.string.use_d1_to_s2_if_not_in_state_of_ID_d3,
                    actionDetail3 % 10,
                    targetParameter!!.buildTargetClause(true),
                    actionDetail1 - 6000
                )
            } else if (actionDetail1 in 6000..6999) {
                falseClause = getString(
                    R.string.use_d1_if_s2_is_not_in_state_of_ID_d3_with_stacks_greater_than_or_equal_to_d4,
                    actionDetail3 % 10,
                    targetParameter!!.buildTargetClause(true),
                    actionDetail1 - 6000,
                    actionValue3!!.value.toInt()
                )
            }
        } else if (actionDetail2 == 0) {
            falseClause = getString(R.string.no_effect)
        }
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return if (trueClause != null && falseClause != null) getString(
            R.string.Exclusive_condition_s,
            trueClause + falseClause
        ) else if (trueClause != null) getString(
            R.string.Exclusive_condition_s,
            trueClause
        ) else if (falseClause != null) getString(
            R.string.Exclusive_condition_s,
            falseClause
        ) else super.localizedDetail(level, property)
    }
}