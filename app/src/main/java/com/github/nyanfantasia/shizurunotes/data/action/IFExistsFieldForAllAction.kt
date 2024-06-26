package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class IFExistsFieldForAllAction : ActionParameter() {

    override fun localizedDetail(level: Int, property: Property?): String? {
        return if (actionDetail2 != 0 && actionDetail3 != 0) getString(
            R.string.Condition_if_the_specific_field_exists_then_use_d1_otherwise_d2,
            actionNum(actionDetail2), actionNum(actionDetail3)
        ) else if (actionDetail2 != 0) getString(
            R.string.Condition_if_the_specific_field_exists_then_use_d,
            actionNum(actionDetail2)
        ) else super.localizedDetail(level, property)
    }
}