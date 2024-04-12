package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.PropertyKey
import com.github.nyanfantasia.shizurunotes.data.action.ClassModifier.Companion.parse
import java.math.RoundingMode

class RegenerationAction : ActionParameter() {
    enum class RegenerationType(val value: Int) {
        Unknown(-1), Hp(1), Tp(2);

        fun description(): String {
            return when (this) {
                Hp -> getString(R.string.HP)
                Tp -> getString(R.string.TP)
                else -> getString(R.string.Unknown)
            }
        }

        companion object {
            fun parse(value: Int): RegenerationType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var healClass: ClassModifier? = null
    private var regenerationType: RegenerationType? = null
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        healClass = parse(actionDetail1)
        regenerationType = RegenerationType.parse(actionDetail2)
        durationValues.add(ActionValue(actionValue5!!, actionValue6!!, null))
        when (healClass) {
            ClassModifier.Magic -> {
                actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
                actionValues.add(ActionValue(actionValue3!!, actionValue4!!, PropertyKey.MagicStr))
            }
            ClassModifier.Physical -> {
                actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
                actionValues.add(ActionValue(actionValue3!!, actionValue4!!, PropertyKey.Atk))
            }
            else -> {
                actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
                actionValues.add(ActionValue(actionValue3!!, actionValue4!!, PropertyKey.Atk))
            }
        }
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.Restore_s1_s2_s3_per_second_for_s4_sec,
            targetParameter!!.buildTargetClause(),
            buildExpression(level, property),
            regenerationType!!.description(),
            buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
        )
    }
}