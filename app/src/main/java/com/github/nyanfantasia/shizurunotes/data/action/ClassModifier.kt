package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N

enum class ClassModifier(val value: Int) {
    Unknown(0), Physical(1), Magic(2), InevitablePhysical(3);

    fun description(): String {
        return when (this) {
            Magic -> I18N.getString(R.string.magic)
            Physical -> I18N.getString(R.string.physical)
            InevitablePhysical -> I18N.getString(
                R.string.inevitable_physical
            )
            else -> I18N.getString(R.string.unknown)
        }
    }

    companion object {
        @JvmStatic
        fun parse(value: Int): ClassModifier {
            for (item in values()) {
                if (item.value == value) return item
            }
            return Unknown
        }
    }
}