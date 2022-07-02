package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.PropertyKey
import com.github.nyanfantasia.shizurunotes.utils.Utils.roundIfNeed
import java.math.RoundingMode

/***
 * TODO: Make all Actions that change coefficient values inherit from this class
 */
class GiveValueAction : ActionParameter() {
    private var keyType: PropertyKey? = null
    private var limitValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        actionValues.add(ActionValue(actionValue2!!, actionValue3!!, null))
        limitValues.add(ActionValue(actionValue4!!, actionValue5!!, null))
        keyType = when (actionValue1!!.value.toInt()) {
            7 -> PropertyKey.Atk
            8 -> PropertyKey.MagicStr
            9 -> PropertyKey.Def
            10 -> PropertyKey.MagicDef
            else -> PropertyKey.Unknown
        }
    }

    private fun buildFormulaString(level: Int, property: Property?): String? {
        var result = super.localizedDetail(level, property)
        when (actionValue1!!.value.toInt()) {
            0 -> result = getString(
                R.string.Modifier_add_s1_HP_to_value_d2_of_effect_d3,
                buildExpression(
                    level,
                    null,
                    RoundingMode.UNNECESSARY,
                    property,
                    isHealing = false,
                    isSelfTPRestoring = false,
                    hasBracesIfNeeded = true
                ),
                actionDetail2, actionDetail1 % 10
            )
            1 -> result = getString(
                R.string.Modifier_add_s1_lost_HP_to_value_d2_of_effect_d3,
                buildExpression(
                    level,
                    null,
                    RoundingMode.UNNECESSARY,
                    property,
                    isHealing = false,
                    isSelfTPRestoring = false,
                    hasBracesIfNeeded = true
                ),
                actionDetail2, actionDetail1 % 10
            )
            2 -> {
                /*
                 * TODO: Starting from the appearance,
                 *  manually multiply the value by 2 to meet the in-game value,
                 *  and welcome the boss to propose a well-founded solution.
                 *  See issue#29 for details on this bug
                 */
                var s1 = buildExpression(
                    level,
                    null,
                    RoundingMode.UNNECESSARY,
                    property,
                    isHealing = false,
                    isSelfTPRestoring = false,
                    hasBracesIfNeeded = true
                )
                s1 = try {
                    roundIfNeed(2.0 * s1.toDouble())
                } catch (e: Exception) {
                    "2 * $s1"
                }
                result = getString(
                    R.string.Modifier_add_s1_count_of_defeated_enemies_to_value_d2_of_effect_d3,
                    s1,
                    actionDetail2,
                    actionDetail1 % 10
                )
            }
            4 -> result = getString(
                R.string.Modifier_add_s1_count_of_targets_to_value_d2_of_effect_d3,
                buildExpression(
                    level,
                    null,
                    RoundingMode.UNNECESSARY,
                    property,
                    isHealing = false,
                    isSelfTPRestoring = false,
                    hasBracesIfNeeded = true
                ),
                actionDetail2, actionDetail1 % 10
            )
            5 -> result = getString(
                R.string.Modifier_add_s1_count_of_damaged_to_value_d2_of_effect_d3,
                buildExpression(
                    level,
                    null,
                    RoundingMode.UNNECESSARY,
                    property,
                    isHealing = false,
                    isSelfTPRestoring = false,
                    hasBracesIfNeeded = true
                ),
                actionDetail2, actionDetail1 % 10
            )
            6 -> result = getString(
                R.string.Modifier_add_s1_total_damage_to_value_d2_of_effect_d3,
                buildExpression(
                    level,
                    null,
                    RoundingMode.UNNECESSARY,
                    property,
                    isHealing = false,
                    isSelfTPRestoring = false,
                    hasBracesIfNeeded = true
                ),
                actionDetail2, actionDetail1 % 10
            )
            12 -> result = getString(
                R.string.Modifier_add_s1_count_of_s2_behind_self_to_value_d3_of_effect_d4,
                buildExpression(
                    level,
                    null,
                    RoundingMode.UNNECESSARY,
                    property,
                    isHealing = false,
                    isSelfTPRestoring = false,
                    hasBracesIfNeeded = true
                ),
                targetParameter!!.buildTargetClause(), actionDetail2, actionDetail1 % 10
            )
            13 -> result = getString(
                R.string.Modifier_add_s1_lost_hp_total_hp_of_s2_behind_self_to_value_d3_of_effect_d4,
                buildExpression(
                    level,
                    null,
                    RoundingMode.UNNECESSARY,
                    property,
                    isHealing = false,
                    isSelfTPRestoring = false,
                    hasBracesIfNeeded = true
                ),
                targetParameter!!.buildTargetClause(), actionDetail2, actionDetail1 % 10
            )
            102 -> result = getString(
                R.string.Modifier_add_s1_count_of_omemes_value_d2_of_effect_d3,
                buildExpression(
                    level,
                    null,
                    RoundingMode.UNNECESSARY,
                    property,
                    isHealing = false,
                    isSelfTPRestoring = false,
                    hasBracesIfNeeded = true
                ),
                actionDetail2, actionDetail1 % 10
            )
            else -> if (actionValue1!!.value >= 200 && actionValue1!!.value < 300) {
                result = getString(
                    R.string.Modifier_add_s1_stacks_of_mark_ID_d2_to_value_d3_of_effect_d4,
                    buildExpression(
                        level,
                        null,
                        RoundingMode.UNNECESSARY,
                        property,
                        isHealing = false,
                        isSelfTPRestoring = false,
                        hasBracesIfNeeded = true
                    ),
                    actionValue1!!.value.toInt() % 200, actionDetail2, actionDetail1 % 10
                )
            } else if (actionValue1!!.value in 7.0..10.0) {
                result = getString(
                    R.string.Modifier_add_s1_s2_of_s3_to_value_d4_of_effect_d5,
                    buildExpression(
                        level,
                        null,
                        RoundingMode.UNNECESSARY,
                        property,
                        isHealing = false,
                        isSelfTPRestoring = false,
                        hasBracesIfNeeded = true
                    ),
                    keyType!!.description(),
                    targetParameter!!.buildTargetClause(),
                    actionDetail2,
                    actionDetail1 % 10
                )
            } else if (actionValue1!!.value >= 20 && actionValue1!!.value < 30) {
                result = getString(
                    R.string.Modifier_add_s1_number_on_counter_d2_to_value_d3_of_effect_d4,
                    buildExpression(
                        level,
                        null,
                        RoundingMode.UNNECESSARY,
                        property,
                        isHealing = false,
                        isSelfTPRestoring = false,
                        hasBracesIfNeeded = true
                    ),
                    actionValue1!!.value.toInt() % 10, actionDetail2, actionDetail1 % 10
                )
            }
        }
        if (actionValue4!!.value != 0.0 && actionValue5!!.value != 0.0) {
            result += getString(
                R.string.The_upper_limit_of_this_effect_is_s,
                buildExpression(level, limitValues, null, property)
            )
        }
        return result
    }
}