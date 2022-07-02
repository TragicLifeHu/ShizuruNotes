package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Ailment
import com.github.nyanfantasia.shizurunotes.data.Ailment.*
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.user.UserSettings
import com.github.nyanfantasia.shizurunotes.user.UserSettings.Companion.get
import com.github.nyanfantasia.shizurunotes.utils.Utils.roundIfNeed
import java.math.RoundingMode

class AilmentAction : ActionParameter() {
    private var ailment: Ailment? = null
    private val chanceValues: MutableList<ActionValue> = ArrayList()
    private var durationValues: MutableList<ActionValue>? = null
    override fun childInit() {
        ailment = Ailment(rawActionType, actionDetail1)
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        chanceValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
        durationValues = chanceValues
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (ailment!!.ailmentType) {
            AilmentType.Action -> {
                var str: String = when (ailment!!.ailmentDetail!!.detail as ActionDetail?) {
                    ActionDetail.Haste, ActionDetail.Slow -> if (get()
                            .getExpression() == UserSettings.EXPRESSION_ORIGINAL
                    ) {
                        getString(
                            R.string.Multiple_attack_speed_of_s1_with_s2_for_s3_sec,
                            targetParameter!!.buildTargetClause(),
                            buildExpression(
                                level,
                                actionValues,
                                RoundingMode.UNNECESSARY,
                                property
                            ) + " * 100",
                            buildExpression(
                                level,
                                durationValues,
                                RoundingMode.UNNECESSARY,
                                property
                            )
                        )
                    } else {
                        getString(
                            R.string.Multiple_attack_speed_of_s1_with_s2_for_s3_sec,
                            targetParameter!!.buildTargetClause(),
                            roundIfNeed(
                                buildExpression(
                                    level,
                                    actionValues,
                                    RoundingMode.UNNECESSARY,
                                    property
                                ).toDouble() * 100
                            ),
                            buildExpression(
                                level,
                                durationValues,
                                RoundingMode.UNNECESSARY,
                                property
                            )
                        )
                    }
                    ActionDetail.Sleep -> getString(
                        R.string.Make_s1_fall_asleep_for_s2_sec,
                        targetParameter!!.buildTargetClause(),
                        buildExpression(
                            level,
                            durationValues,
                            RoundingMode.UNNECESSARY,
                            property
                        )
                    )
                    ActionDetail.Faint -> getString(
                        R.string.Make_s1_fall_into_faint_for_s2_sec,
                        targetParameter!!.buildTargetClause(),
                        buildExpression(
                            level,
                            durationValues,
                            RoundingMode.UNNECESSARY,
                            property
                        )
                    )
                    ActionDetail.TimeStop -> getString(
                        R.string.Stop_s1_for_s2_sec,
                        targetParameter!!.buildTargetClause(),
                        buildExpression(
                            level,
                            durationValues,
                            RoundingMode.UNNECESSARY,
                            property
                        )
                    )
                    else -> getString(
                        R.string.s1_s2_for_s3_sec,
                        ailment!!.description(),
                        targetParameter!!.buildTargetClause(),
                        buildExpression(
                            level,
                            durationValues,
                            RoundingMode.UNNECESSARY,
                            property
                        )
                    )
                }
                if (actionDetail2 == 1) {
                    str += getString(R.string.This_effect_will_be_released_when_taking_damaged)
                }
                str
            }
            AilmentType.Dot -> {
                var r: String = when (ailment!!.ailmentDetail!!.detail as DotDetail?) {
                    DotDetail.Poison -> getString(
                        R.string.Poison_s1_and_deal_s2_damage_per_second_for_s3_sec,
                        targetParameter!!.buildTargetClause(),
                        buildExpression(level, property),
                        buildExpression(level, durationValues, RoundingMode.HALF_UP, property)
                    )
                    DotDetail.ViolentPoison -> getString(
                        R.string.Poison_s1_violently_and_deal_s2_damage_per_second_for_s3_sec,
                        targetParameter!!.buildTargetClause(),
                        buildExpression(level, property),
                        buildExpression(
                            level,
                            durationValues,
                            RoundingMode.HALF_UP,
                            property
                        )
                    )
                    else -> getString(
                        R.string.s1_s2_and_deal_s3_damage_per_second_for_s4_sec,
                        ailment!!.description(),
                        targetParameter!!.buildTargetClause(),
                        buildExpression(level, property),
                        buildExpression(level, durationValues, RoundingMode.HALF_UP, property)
                    )
                }
                if (actionValue5!!.value > 0) {
                    r += getString(
                        R.string.DMG_shall_be_increased_by_s_percents_of_base_DMG_through_each_tick,
                        actionValue5!!.valueString()
                    )
                }
                r
            }
            AilmentType.Silence -> getString(
                R.string.Silence_s1_with_s2_chance_for_s3_sec,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, chanceValues, RoundingMode.UNNECESSARY, property),
                buildExpression(level, property)
            )
            AilmentType.Darken -> getString(
                R.string.Blind_s1_with_s2_chance_for_s3_sec_physical_attack_has_d4_chance_to_miss,
                targetParameter!!.buildTargetClause(),
                buildExpression(level, chanceValues, RoundingMode.UNNECESSARY, property),
                buildExpression(level, RoundingMode.UNNECESSARY, property),
                100 - actionDetail1
            )
            else -> super.localizedDetail(level, property)
        }
    }
}