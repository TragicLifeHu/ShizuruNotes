package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class ReflexiveAction : ActionParameter() {
    internal enum class ReflexiveType(val value: Int) {
        Unknown(0), Normal(1), Search(2), Position(3);

        companion object {
            fun parse(value: Int): ReflexiveType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var reflexiveType: ReflexiveType? = null
    override fun childInit() {
        reflexiveType = ReflexiveType.parse(actionDetail1)
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return if (targetParameter!!.targetType === TargetType.Absolute) getString(
            R.string.Change_the_perspective_to_s1_d2,
            targetParameter!!.buildTargetClause(),
            actionValue1!!.value.toInt()
        ) else if (reflexiveType == ReflexiveType.Search) getString(
            R.string.Scout_and_change_the_perspective_on_s,
            targetParameter!!.buildTargetClause()
        ) else getString(
            R.string.Change_the_perspective_on_s,
            targetParameter!!.buildTargetClause()
        )
    }
}