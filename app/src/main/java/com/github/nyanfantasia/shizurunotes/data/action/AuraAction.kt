package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.PropertyKey
import com.github.nyanfantasia.shizurunotes.data.action.PercentModifier.Companion.parse
import com.github.nyanfantasia.shizurunotes.user.UserSettings
import com.github.nyanfantasia.shizurunotes.user.UserSettings.Companion.get
import java.math.RoundingMode

open class AuraAction : ActionParameter() {
    enum class AuraType(val value: Int) {
        None(-1),
        Atk(1),
        Def(2),
        MagicStr(3),
        MagicDef(4),
        Dodge(5),
        PhysicalCritical(6),
        MagicCritical(7),
        EnergyRecoverRate(8),
        LifeSteal(9),
        MoveSpeed(10),
        PhysicalCriticalDamage(11),
        MagicCriticalDamage(12),
        Accuracy(13),
        ReceivedCriticalDamage(14),
        ReceivedDamage(15),
        ReceivedPhysicalDamage(16),
        ReceivedMagicDamage(17),
        MaxHp(100);

        fun description(): String {
            return when (this) {
                Atk -> PropertyKey.Atk.description()
                Def -> PropertyKey.Def.description()
                MagicStr -> PropertyKey.MagicStr.description()
                MagicDef -> PropertyKey.MagicDef.description()
                Dodge -> PropertyKey.Dodge.description()
                PhysicalCritical -> PropertyKey.PhysicalCritical.description()
                MagicCritical -> PropertyKey.MagicCritical.description()
                EnergyRecoverRate -> PropertyKey.EnergyRecoveryRate.description()
                LifeSteal -> PropertyKey.LifeSteal.description()
                MoveSpeed -> getString(R.string.Move_Speed)
                PhysicalCriticalDamage -> getString(
                    R.string.Physical_Critical_Damage
                )
                MagicCriticalDamage -> getString(
                    R.string.Magic_Critical_Damage
                )
                Accuracy -> PropertyKey.Accuracy.description()
                ReceivedCriticalDamage -> getString(
                    R.string.Received_Critical_Damage
                )
                ReceivedDamage -> getString(R.string.received_damage)
                ReceivedPhysicalDamage -> getString(
                    R.string.received_physical_damage
                )
                ReceivedMagicDamage -> getString(
                    R.string.received_magic_damage
                )
                MaxHp -> getString(R.string.max_HP)
                else -> ""
            }
        }

        companion object {
            fun parse(value: Int): AuraType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return None
            }
        }
    }

    enum class AuraActionType {
        Raise, Reduce;

        fun description(): String {
            return when (this) {
                Raise -> getString(R.string.Raise)
                Reduce -> getString(R.string.Reduce)
            }
        }

        fun toggle(): AuraActionType {
            return when (this) {
                Raise -> Reduce
                Reduce -> Raise
            }
        }

        companion object {
            fun parse(value: Int): AuraActionType {
                return if (value % 10 == 1) Reduce else Raise
            }
        }
    }

    enum class BreakType(val value: Int) {
        Unknown(-1), Normal(1), Break(2);

        companion object {
            fun parse(value: Int): BreakType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    @JvmField
    protected var percentModifier: PercentModifier? = null
    @JvmField
    protected var durationValues: MutableList<ActionValue> = ArrayList()
    @JvmField
    protected var auraActionType: AuraActionType? = null
    @JvmField
    protected var auraType: AuraType? = null
    private var breakType: BreakType? = null
    private var isConstant = false
    override fun childInit() {
        percentModifier = parse(actionValue1!!.value.toInt())
        actionValues.add(ActionValue(actionValue2!!, actionValue3!!, null))
        durationValues.add(ActionValue(actionValue4!!, actionValue5!!, null))
        auraActionType = AuraActionType.parse(actionDetail1)
        if (actionDetail1 == 1) {
            auraType = AuraType.MaxHp
        } else if (actionDetail1 >= 1000) {
            auraType = AuraType.parse(actionDetail1 % 1000 / 10)
            isConstant = true
        } else {
            auraType = AuraType.parse(actionDetail1 / 10)
        }
        breakType = BreakType.parse(actionDetail2)
        if (auraType == AuraType.ReceivedCriticalDamage || auraType == AuraType.ReceivedMagicDamage || auraType == AuraType.ReceivedPhysicalDamage || auraType == AuraType.ReceivedDamage) {
            auraActionType = auraActionType!!.toggle()
            percentModifier = PercentModifier.Percent
        }
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        var r = buildExpression(level, RoundingMode.UP, property)
        if (percentModifier === PercentModifier.Percent && get().getExpression() != UserSettings.EXPRESSION_VALUE) {
            r = String.format("(%s)", r)
        }
        return when (breakType) {
            BreakType.Break -> getString(
                R.string.s1_s2_s3_s4_s5_during_break,
                auraActionType!!.description(),
                targetParameter!!.buildTargetClause(),
                r,
                percentModifier!!.description(),
                auraType!!.description()
            )
            else -> {
                getString(
                    R.string.s1_s2_s3_s4_s5_for_s6_sec,
                    auraActionType!!.description(),
                    targetParameter!!.buildTargetClause(),
                    r,
                    percentModifier!!.description(),
                    auraType!!.description(),
                    buildExpression(
                        level,
                        durationValues,
                        RoundingMode.UNNECESSARY,
                        property
                    ),
                    if (isConstant) getString(R.string.this_buff_is_constant) else ""
                )
            }
        }
    }
}