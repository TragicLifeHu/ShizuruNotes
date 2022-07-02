package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import java.math.BigDecimal
import java.math.RoundingMode

class ChangeSpeedOverlapAction : ActionParameter() {
    enum class SpeedChangeType(val value: Int) {
        Slow(1), Haste(2);

        companion object {
            fun parse(value: Int): SpeedChangeType {
                for (item in values()) {
                    if (item.value == value) return item
                }
                return Haste
            }
        }
    }

    private var speedChangeType: SpeedChangeType? = null
    private var durationValues: MutableList<ActionValue> = ArrayList()
    override fun childInit() {
        speedChangeType = SpeedChangeType.parse(actionDetail1)
        actionValues.add(ActionValue(actionValue1!!, actionValue2!!, null))
        durationValues.add(ActionValue(actionValue3!!, actionValue4!!, null))
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        return try {
            when (speedChangeType) {
                SpeedChangeType.Slow -> {
                    getString(
                        R.string.Decrease_s1_ATK_SPD_by_s2_for_s3_sec,
                        targetParameter!!.buildTargetClause(),
                        BigDecimal.valueOf(
                            buildExpression(
                                level,
                                RoundingMode.UNNECESSARY,
                                property
                            ).toDouble()
                        ).multiply(
                            BigDecimal.valueOf(100.0)
                        ).stripTrailingZeros().toPlainString(),
                        buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
                    )
                }
                SpeedChangeType.Haste -> {
                    getString(
                        R.string.Increase_s1_ATK_SPD_by_s2_for_s3_sec,
                        targetParameter!!.buildTargetClause(),
                        BigDecimal.valueOf(
                            buildExpression(
                                level,
                                RoundingMode.UNNECESSARY,
                                property
                            ).toDouble()
                        ).multiply(
                            BigDecimal.valueOf(100.0)
                        ).stripTrailingZeros().toPlainString(),
                        buildExpression(level, durationValues, RoundingMode.UNNECESSARY, property)
                    )
                }
                else -> {
                    super.localizedDetail(level, property)
                }
            }
        } catch (ex: Exception) {
            super.localizedDetail(level, property)
        }
    }
}