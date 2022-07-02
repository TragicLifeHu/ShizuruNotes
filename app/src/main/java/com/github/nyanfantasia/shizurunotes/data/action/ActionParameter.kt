package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property
import com.github.nyanfantasia.shizurunotes.data.PropertyKey
import com.github.nyanfantasia.shizurunotes.data.Skill
import com.github.nyanfantasia.shizurunotes.data.action.ActionType.Companion.parse
import com.github.nyanfantasia.shizurunotes.user.UserSettings
import com.github.nyanfantasia.shizurunotes.user.UserSettings.Companion.get
import com.github.nyanfantasia.shizurunotes.utils.Utils.roundIfNeed
import java.math.BigDecimal
import java.math.RoundingMode

open class ActionParameter {
    private var isEnemySkill = false
    private var dependActionId = 0
    var childrenAction: List<Skill.Action>? = null
    private var actionId = 0
    private var classId = 0
    @JvmField
    var rawActionType = 0
    @JvmField
    var actionDetail1 = 0
    @JvmField
    var actionDetail2 = 0
    @JvmField
    var actionDetail3 = 0
    private var actionDetails = ArrayList<Int>()

    class DoubleValue(var value: Double, index: EActionValue) {
        var description: String
        fun valueString(): String {
            return roundIfNeed(value)
        }

        init {
            description = index.description()
        }
    }

    enum class EActionValue {
        VALUE1, VALUE2, VALUE3, VALUE4, VALUE5, VALUE6, VALUE7, VALUE_NULL;

        fun description(): String {
            return when (this) {
                VALUE1 -> getString(R.string.value1)
                VALUE2 -> getString(R.string.value2)
                VALUE3 -> getString(R.string.value3)
                VALUE4 -> getString(R.string.value4)
                VALUE5 -> getString(R.string.value5)
                VALUE6 -> getString(R.string.value6)
                VALUE7 -> getString(R.string.value7)
                else -> "NULL"
            }
        }
    }

    var actionValue1: DoubleValue? = null
    var actionValue2: DoubleValue? = null
    var actionValue3: DoubleValue? = null
    var actionValue4: DoubleValue? = null
    var actionValue5: DoubleValue? = null
    var actionValue6: DoubleValue? = null
    var actionValue7: DoubleValue? = null
    private var rawActionValues = ArrayList<Double>()
    var actionType: ActionType? = null
    var targetParameter: TargetParameter? = null
    fun init(
        isEnemySkill: Boolean,
        actionId: Int,
        dependActionId: Int,
        classId: Int,
        actionType: Int,
        actionDetail1: Int,
        actionDetail2: Int,
        actionDetail3: Int,
        actionValue1: Double,
        actionValue2: Double,
        actionValue3: Double,
        actionValue4: Double,
        actionValue5: Double,
        actionValue6: Double,
        actionValue7: Double,
        targetAssignment: Int,
        targetArea: Int,
        targetRange: Int,
        targetType: Int,
        targetNumber: Int,
        targetCount: Int,
        dependAction: Skill.Action?,
        childrenAction: List<Skill.Action>?
    ): ActionParameter {
        this.isEnemySkill = isEnemySkill
        this.actionId = actionId
        this.dependActionId = dependActionId
        this.classId = classId
        rawActionType = actionType
        this.actionType = parse(actionType)
        this.actionDetail1 = actionDetail1
        this.actionDetail2 = actionDetail2
        this.actionDetail3 = actionDetail3
        if (actionDetail1 != 0) actionDetails.add(actionDetail1)
        if (actionDetail2 != 0) actionDetails.add(actionDetail2)
        if (actionDetail3 != 0) actionDetails.add(actionDetail3)
        this.actionValue1 = DoubleValue(actionValue1, EActionValue.VALUE1)
        this.actionValue2 = DoubleValue(actionValue2, EActionValue.VALUE2)
        this.actionValue3 = DoubleValue(actionValue3, EActionValue.VALUE3)
        this.actionValue4 = DoubleValue(actionValue4, EActionValue.VALUE4)
        this.actionValue5 = DoubleValue(actionValue5, EActionValue.VALUE5)
        this.actionValue6 = DoubleValue(actionValue6, EActionValue.VALUE6)
        this.actionValue7 = DoubleValue(actionValue7, EActionValue.VALUE7)
        if (actionValue1 != 0.0) rawActionValues.add(actionValue1)
        if (actionValue2 != 0.0) rawActionValues.add(actionValue2)
        if (actionValue3 != 0.0) rawActionValues.add(actionValue3)
        if (actionValue4 != 0.0) rawActionValues.add(actionValue4)
        if (actionValue5 != 0.0) rawActionValues.add(actionValue5)
        if (actionValue6 != 0.0) rawActionValues.add(actionValue6)
        if (actionValue7 != 0.0) rawActionValues.add(actionValue7)
        if (childrenAction != null) {
            this.childrenAction = childrenAction
        }
        targetParameter = TargetParameter(
            targetAssignment,
            targetNumber,
            targetType,
            targetRange,
            targetArea,
            targetCount,
            dependAction
        )
        childInit()
        return this
    }

    protected open fun childInit() {}
    private fun bracesIfNeeded(content: String): String {
        return if (content.contains("+")) String.format("(%s)", content) else content
    }

    open fun localizedDetail(level: Int, property: Property?): String? {
        return if (rawActionType == 0) {
            getString(R.string.no_effect)
        } else getString(
            R.string.Unknown_effect_d1_to_s2_with_details_s3_values_s4,
            rawActionType,
            targetParameter!!.buildTargetClause(),
            actionDetails.toString(),
            rawActionValues.toString()
        )
    }

    fun buildExpression(level: Int, property: Property?): String {
        return buildExpression(level, actionValues, null, property,
            isHealing = false,
            isSelfTPRestoring = false,
            hasBracesIfNeeded = false
        )
    }

    fun buildExpression(level: Int, roundingMode: RoundingMode?, property: Property?): String {
        return buildExpression(level, actionValues, roundingMode, property,
            isHealing = false,
            isSelfTPRestoring = false,
            hasBracesIfNeeded = false
        )
    }

    @JvmOverloads
    fun buildExpression(
        level: Int,
        actionValues: MutableList<ActionValue>?,
        roundingMode: RoundingMode?,
        property: Property?,
        isHealing: Boolean = false,
        isSelfTPRestoring: Boolean = false,
        hasBracesIfNeeded: Boolean = false
    ): String {
        var rActionValues = actionValues
        var rRoundingMode = roundingMode
        var rProperty = property
        if (rActionValues == null) rActionValues = this.actionValues
        if (rRoundingMode == null) rRoundingMode = RoundingMode.DOWN
        if (rProperty == null) rProperty = Property()
        return if (get().getExpression() == UserSettings.EXPRESSION_EXPRESSION) {
            val expression = StringBuilder()
            for (value in rActionValues) {
                val part = StringBuilder()
                if (value.initial != null && value.perLevel != null) {
                    val initialValue = value.initial!!.toDouble()
                    val perLevelValue = value.perLevel!!.toDouble()
                    if (initialValue == 0.0 && perLevelValue == 0.0) {
                        continue
                    } else if (initialValue == 0.0) {
                        part.append(
                            String.format(
                                "%s * %s",
                                perLevelValue,
                                getString(R.string.SLv)
                            )
                        )
                    } else if (perLevelValue == 0.0) {
                        if (value.key == null && rRoundingMode != RoundingMode.UNNECESSARY) {
                            val bigDecimal = BigDecimal(initialValue)
                            part.append(bigDecimal.setScale(0, rRoundingMode).toInt())
                        } else {
                            part.append(initialValue)
                        }
                    } else {
                        part.append(
                            String.format(
                                "%s + %s * %s",
                                initialValue,
                                perLevelValue,
                                getString(R.string.SLv)
                            )
                        )
                    }
                    if (value.key != null) {
                        if (initialValue == 0.0 && perLevelValue == 0.0) {
                            continue
                        } else if (initialValue == 0.0 || perLevelValue == 0.0) {
                            part.append(String.format(" * %s", value.key!!.description()))
                        } else {
                            val c = part.toString()
                            part.setLength(0)
                            part.append(String.format("(%s) * %s", c, value.key!!.description()))
                        }
                    }
                }
                if (part.isNotEmpty()) {
                    expression.append(part).append(" + ")
                }
            }
            if (expression.isEmpty()) {
                "0"
            } else {
                expression.delete(expression.lastIndexOf(" +"), expression.length)
                if (hasBracesIfNeeded) bracesIfNeeded(expression.toString()) else expression.toString()
            }
        } else if (get().getExpression() == UserSettings.EXPRESSION_ORIGINAL) {
            val expression = StringBuilder()
            for (value in rActionValues) {
                val part = StringBuilder()
                if (value.initial != null && value.perLevel != null) {
                    val initialValue = value.initial!!.toDouble()
                    val perLevelValue = value.perLevel!!.toDouble()
                    if (initialValue == 0.0 && perLevelValue == 0.0) {
                        continue
                    } else if (initialValue == 0.0) {
                        part.append(
                            String.format(
                                "##%s##%s * %s",
                                value.perLevelValue.description,
                                perLevelValue,
                                getString(R.string.SLv)
                            )
                        )
                    } else if (perLevelValue == 0.0) {
                        if (value.key == null && rRoundingMode != RoundingMode.UNNECESSARY) {
                            val bigDecimal = BigDecimal(initialValue)
                            part.append(
                                String.format(
                                    "##%s##%s",
                                    value.initialValue.description,
                                    bigDecimal.setScale(0, rRoundingMode).toInt()
                                )
                            )
                        } else {
                            part.append(
                                String.format(
                                    "##%s##%s",
                                    value.initialValue.description,
                                    initialValue
                                )
                            )
                        }
                    } else {
                        part.append(
                            String.format(
                                "##%s##%s + ##%s##%s * %s",
                                value.initialValue.description,
                                initialValue,
                                value.perLevelValue.description,
                                perLevelValue,
                                getString(R.string.SLv)
                            )
                        )
                    }
                    if (value.key != null) {
                        if (initialValue == 0.0 && perLevelValue == 0.0) {
                            continue
                        } else if (initialValue == 0.0 || perLevelValue == 0.0) {
                            part.append(String.format(" * %s", value.key!!.description()))
                        } else {
                            val c = part.toString()
                            part.setLength(0)
                            part.append(String.format("(%s) * %s", c, value.key!!.description()))
                        }
                    }
                }
                if (part.isNotEmpty()) {
                    expression.append(part).append(" + ")
                }
            }
            if (expression.isEmpty()) {
                "0"
            } else {
                expression.delete(expression.lastIndexOf(" +"), expression.length)
                if (hasBracesIfNeeded) bracesIfNeeded(expression.toString()) else expression.toString()
            }
        } else {
            var fixedValue = BigDecimal("0.0")
            for (value in rActionValues) {
                var part = BigDecimal("0.0")
                if (value.initial != null && value.perLevel != null) {
                    val initialValue = BigDecimal(value.initial)
                    val perLevelValue = BigDecimal(value.perLevel)
                    part = initialValue.add(perLevelValue.multiply(BigDecimal(level)))
                }
                if (value.key != null) {
                    part = part.multiply(BigDecimal.valueOf(rProperty.getItem(value.key)))
                }
                //                int num = (int)part;
//                if (UnitUtils.Companion.approximately(part, (double)num)) {
//                    part = num;
//                }
                fixedValue = fixedValue.add(part)
            }
            if (rRoundingMode == RoundingMode.UNNECESSARY) fixedValue.stripTrailingZeros()
                .toPlainString() else fixedValue.setScale(0, rRoundingMode).toPlainString()
        }
    }

    @JvmField
    protected var actionValues: MutableList<ActionValue> = ArrayList()

    inner class ActionValue {
        var initial: String?
        var perLevel: String?
        var key: PropertyKey?
        var initialValue: DoubleValue
        var perLevelValue: DoubleValue

        constructor(initial: DoubleValue, perLevel: DoubleValue, key: PropertyKey?) {
            initialValue = initial
            perLevelValue = perLevel
            this.initial = initial.valueString()
            this.perLevel = perLevel.valueString()
            this.key = key
        }

        constructor(
            initial: Double,
            perLevel: Double,
            vInitial: EActionValue,
            vPerLevel: EActionValue,
            key: PropertyKey?
        ) {
            initialValue = DoubleValue(initial, vInitial)
            perLevelValue = DoubleValue(perLevel, vPerLevel)
            this.initial = initial.toString()
            this.perLevel = perLevel.toString()
            this.key = key
        }
    }

    companion object {
        fun type(rawType: Int): ActionParameter {
            return when (rawType) {
                1 -> DamageAction()
                2 -> MoveAction()
                3 -> KnockAction()
                4 -> HealAction()
                5 -> CureAction()
                6 -> BarrierAction()
                7 -> ReflexiveAction()
                8, 9, 12, 13 -> AilmentAction()
                10 -> AuraAction()
                11 -> CharmAction()
                14 -> ModeChangeAction()
                15 -> SummonAction()
                16 -> ChangeEnergyAction()
                17 -> TriggerAction()
                18 -> DamageChargeAction()
                19 -> ChargeAction()
                20 -> DecoyAction()
                21 -> NoDamageAction()
                22 -> ChangePatternAction()
                23 -> IfForChildrenAction()
                24 -> RevivalAction()
                25 -> ContinuousAttackAction()
                26 -> AdditiveAction()
                27 -> MultipleAction()
                28 -> IfForAllAction()
                29 -> SearchAreaChangeAction()
                30 -> DestroyAction()
                31 -> ContinuousAttackNearbyAction()
                32 -> EnchantLifeStealAction()
                33 -> EnchantStrikeBackAction()
                34 -> AccumulativeDamageAction()
                35 -> SealAction()
                36 -> AttackFieldAction()
                37 -> HealFieldAction()
                38 -> ChangeParameterFieldAction()
                39 -> AbnormalStateFieldAction()
                40 -> ChangeSpeedFieldAction()
                41 -> UBChangeTimeAction()
                42 -> LoopTriggerAction()
                43 -> IfHasTargetAction()
                44 -> WaveStartIdleAction()
                45 -> SkillExecCountAction()
                46 -> RatioDamageAction()
                47 -> UpperLimitAttackAction()
                48 -> RegenerationAction()
                49 -> DispelAction()
                50 -> ChannelAction()
                52 -> ChangeBodyWidthAction()
                53 -> IFExistsFieldForAllAction()
                54 -> StealthAction()
                55 -> MovePartsAction()
                56 -> CountBlindAction()
                57 -> CountDownAction()
                58 -> StopFieldAction()
                59 -> InhibitHealAction()
                60 -> AttackSealAction()
                61 -> FearAction()
                62 -> AweAction()
                63 -> LoopMotionRepeatAction()
                69 -> ToadAction()
                71 -> KnightGuardAction()
                72 -> DamageCutAction()
                73 -> LogBarrierAction()
                74 -> DivideAction()
                75 -> ActionByHitCountAction()
                76 -> HealDownAction()
                77 -> PassiveSealAction()
                78 -> PassiveDamageUpAction()
                79 -> DamageByBehaviourAction()
                83 -> ChangeSpeedOverlapAction()
                90 -> PassiveAction()
                91 -> PassiveIntermittentAction()
                92 -> ChangeEnergyRecoveryRatioByDamageAction()
                93 -> IgnoreDecoyAction()
                94 -> EffectAction()
                95 -> SpyAction()
                96 -> ChangeEnergyFieldAction()
                else -> ActionParameter()
            }
        }
    }
}

enum class PercentModifier {
    Percent, Number;

    fun description(): String {
        return when (this) {
            Percent -> "%"
            else -> ""
        }
    }

    companion object {
        @JvmStatic
        fun parse(value: Int): PercentModifier {
            return when (value) {
                2 -> Percent
                else -> Number
            }
        }
    }
}

enum class ClassModifier(val value: Int) {
    Unknown(0), Physical(1), Magic(2), InevitablePhysical(3);

    fun description(): String {
        return when (this) {
            Magic -> getString(R.string.magic)
            Physical -> getString(R.string.physical)
            InevitablePhysical -> getString(
                R.string.inevitable_physical
            )
            else -> getString(R.string.unknown)
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

enum class CriticalModifier(val value: Int) {
    Normal(0), Critical(1);

    companion object {
        @JvmStatic
        fun parse(value: Int): CriticalModifier {
            return when (value) {
                1 -> Critical
                else -> Normal
            }
        }
    }
}