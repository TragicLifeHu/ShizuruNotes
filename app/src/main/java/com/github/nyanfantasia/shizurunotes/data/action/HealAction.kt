package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.PropertyKey

class HealAction : ActionParameter() {
    private var healClass: ClassModifier? = null
    private var percentModifier: PercentModifier? = null
    override fun childInit() {
        healClass = ClassModifier.parse(actionDetail1)
        percentModifier = PercentModifier.parse(actionValue1!!.value.toInt())
        when (healClass) {
            ClassModifier.Magic -> {
                actionValues.add(ActionValue(actionValue4!!, actionValue5!!, PropertyKey.MagicStr))
                actionValues.add(ActionValue(actionValue2!!, actionValue3!!, null))
            }
            ClassModifier.Physical -> {
                actionValues.add(ActionValue(actionValue4!!, actionValue5!!, PropertyKey.Atk))
                actionValues.add(ActionValue(actionValue2!!, actionValue3!!, null))
            }
            else -> {}
        }
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.Restore_s1_s2_s3_HP,
            targetParameter!!.buildTargetClause(),
            buildExpression(level, null, null, property,
                isHealing = true,
                isSelfTPRestoring = false,
                hasBracesIfNeeded = false
            ),
            percentModifier!!.description()
        )
    }
}