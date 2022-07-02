package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.PropertyKey
import com.github.nyanfantasia.shizurunotes.utils.Utils.roundIfNeed

class DamageAction : ActionParameter() {
    private var damageClass: ClassModifier? = null
    private var criticalModifier: CriticalModifier? = null
    private var decideTargetAtkType: DecideTargetAtkType? = null

    enum class DecideTargetAtkType(val value: Int) {
        BySource(0), LowerDef(1);

        companion object {
            fun parse(value: Int): DecideTargetAtkType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return BySource
            }
        }
    }

    override fun childInit() {
        damageClass = ClassModifier.parse(actionDetail1)
        criticalModifier = CriticalModifier.parse(actionValue5!!.value.toInt())
        decideTargetAtkType = DecideTargetAtkType.parse(actionDetail2)
        when (damageClass) {
            ClassModifier.Magic -> {
                actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
                actionValues.add(ActionValue(actionValue3!!, actionValue4!!, PropertyKey.MagicStr))
            }
            ClassModifier.Physical, ClassModifier.InevitablePhysical -> {
                actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
                actionValues.add(ActionValue(actionValue3!!, actionValue4!!, PropertyKey.Atk))
            }
            else -> {}
        }
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        val string = StringBuilder()
        when (criticalModifier) {
            CriticalModifier.Normal -> string.append(
                getString(
                    R.string.Deal_s1_s2_damage_to_s3,
                    buildExpression(level, property),
                    damageClass!!.description(),
                    targetParameter!!.buildTargetClause()
                )
            )
            CriticalModifier.Critical -> string.append(
                getString(
                    R.string.Deal_s1_s2_damage_to_s3_and_this_attack_is_ensured_critical,
                    buildExpression(level, property),
                    damageClass!!.description(),
                    targetParameter!!.buildTargetClause(),
                    roundIfNeed(
                        actionValue5!!.value
                    )
                )
            )
            else -> string.append(
                getString(
                    R.string.Deal_s1_s2_damage_to_s3_and_this_attack_is_ensured_critical,
                    buildExpression(level, property),
                    damageClass!!.description(),
                    targetParameter!!.buildTargetClause(),
                    roundIfNeed(
                        actionValue5!!.value
                    )
                )
            )
        }
        if (actionValue6!!.value != 0.0) {
            string.append(
                getString(
                    R.string.Critical_damage_is_s_times_as_normal_damage,
                    2 * actionValue6!!.value
                )
            )
        }
        if (decideTargetAtkType == DecideTargetAtkType.LowerDef) {
            string.append(getString(R.string.This_damage_type_is_judged_by_the_lower_defence_value_of_targeted_enemy))
        }
        return string.toString()
    }
}