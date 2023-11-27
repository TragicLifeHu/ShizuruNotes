package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class IfContainsUnitGroupAction : ActionParameter() {

    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.Enabled_if_s1_in_group_d2_or_d3,
            targetParameter!!.buildTargetClause(true),
            actionDetail1,
            actionDetail2
        )
    }
}