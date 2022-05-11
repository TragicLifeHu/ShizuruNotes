package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import kotlin.math.roundToInt

@Suppress("EnumEntryName")
class RevivalAction : ActionParameter() {
    internal enum class RevivalType(val value: Int) {
        unknown(0), normal(1), phoenix(2);

        companion object {
            fun parse(value: Int): RevivalType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return unknown
            }
        }
    }

    private var revivalType: RevivalType? = null
    override fun childInit() {
        revivalType = RevivalType.parse(actionDetail1)
    }

    override fun localizedDetail(level: Int, property: Property): String {
        return when (revivalType) {
            RevivalType.normal -> getString(
                R.string.Revive_s1_with_d2_HP,
                targetParameter.buildTargetClause(), (actionValue2.value * 100).roundToInt()
            )
            else -> super.localizedDetail(level, property)
        }
    }
}