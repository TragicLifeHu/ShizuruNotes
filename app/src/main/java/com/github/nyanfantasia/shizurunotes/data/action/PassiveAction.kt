package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.PropertyKey

class PassiveAction : ActionParameter() {
    private var propertyKey: PropertyKey? = null
    override fun childInit() {
        propertyKey = when (actionDetail1) {
            1 -> PropertyKey.Hp
            2 -> PropertyKey.Atk
            3 -> PropertyKey.Def
            4 -> PropertyKey.MagicStr
            5 -> PropertyKey.MagicDef
            else -> PropertyKey.Unknown
        }
        actionValues.add(ActionValue(actionValue2, actionValue3, null))
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return getString(
            R.string.Raise_s1_s2,
            buildExpression(level, property),
            propertyKey!!.description()
        )
    }

    fun propertyItem(level: Int): Property {
        return Property.getPropertyWithKeyAndValue(
            null,
            propertyKey,
            actionValue2.value + actionValue3.value * level
        )
    }
}