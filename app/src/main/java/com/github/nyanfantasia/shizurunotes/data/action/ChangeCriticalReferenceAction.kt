package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property


class ChangeCriticalReferenceAction : ActionParameter() {
    enum class CriticalReferenceType(val value: Int) {
        Unknown(-1),
        Normal(1),
        PhysicalCrit(2),
        MagicCrit(3),
        SumCrit(4);

        fun description(): String {
            return when (this) {
                Normal -> getString(R.string.normal)
                PhysicalCrit -> getString(R.string.Physical_Critical)
                MagicCrit -> getString(R.string.Magic_Critical)
                SumCrit -> getString(R.string.Sum_of_phy_and_mag)
                else -> getString(R.string.unknown)
            }
        }

        companion object {
            fun parse(value: Int): CriticalReferenceType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var referenceType: CriticalReferenceType? = null
    override fun childInit() {
        super.childInit()
        referenceType = CriticalReferenceType.parse(actionDetail2)
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return if (referenceType == CriticalReferenceType.Normal) {
            getString(R.string.no_effect)
        } else getString(
            R.string.Use_critical_reference_s1_for_skill_d2,
            referenceType!!.description(),
            actionNum(actionDetail1)
        )
    }
}