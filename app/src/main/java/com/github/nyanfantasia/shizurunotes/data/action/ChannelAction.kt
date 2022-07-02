package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.RoundingMode

class ChannelAction : AuraAction() {
    enum class ReleaseType(val value: Int) {
        Damage(1), Unknown(2);

        companion object {
            fun parse(value: Int): ReleaseType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    private var releaseType: ReleaseType? = null
    override fun childInit() {
        super.childInit()
        releaseType = ReleaseType.parse(actionDetail2)
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return when (releaseType) {
            ReleaseType.Damage -> getString(
                R.string.Channeling_for_s1_sec_disrupted_by_taking_damage_d2_times_s3_s4_s5_s6_s7,
                buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property),
                actionDetail3,
                auraActionType!!.description(),
                targetParameter!!.buildTargetClause(),
                buildExpression(level, RoundingMode.UP, property),
                percentModifier!!.description(),
                auraType!!.description()
            )
            else -> super.localizedDetail(level, property)
        }
    }
}