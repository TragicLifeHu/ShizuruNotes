package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Skill
import com.github.nyanfantasia.shizurunotes.data.action.TargetCount.PluralModifier
import com.github.nyanfantasia.shizurunotes.data.action.TargetType.ExclusiveAllType

class TargetParameter(
    targetAssignment: Int,
    targetNumber: Int,
    targetType: Int,
    targetRange: Int,
    targetArea: Int,
    targetCount: Int,
    dependAction: Skill.Action?
) {
    private var targetAssignment: TargetAssignment
    private var targetNumber: TargetNumber
    private var rawTargetType: Int
    @JvmField
    var targetType: TargetType
    var targetRange: TargetRange
    private var direction: DirectionType
    private var targetCount: TargetCount
    private val dependAction: Skill.Action?
    private var hasRelationPhrase = false
    private var hasCountPhrase = false
    private var hasRangePhrase = false
    private var hasNthModifier = false
    private var hasDirectionPhrase = false
    private var hasTargetType = false
    private fun hasDependAction(): Boolean {
        return dependAction != null && dependAction.actionId != 0 && targetType != TargetType.Absolute && dependAction.parameter.actionType !== ActionType.ChooseArea
    }

    private fun setBooleans() {
        hasRelationPhrase = (targetType != TargetType.Self
                && targetType != TargetType.Absolute)
        hasCountPhrase = (targetType != TargetType.Self
                && !(targetType == TargetType.None && targetCount == TargetCount.Zero))
        hasRangePhrase = targetRange.rangeType == TargetRange.FINITE
        hasNthModifier =
            targetNumber == TargetNumber.Second || targetNumber == TargetNumber.Third || targetNumber == TargetNumber.Fourth || targetNumber == TargetNumber.Fifth
        hasDirectionPhrase = (direction == DirectionType.Front
                && (hasRangePhrase || targetCount == TargetCount.All))
        hasTargetType =
            !(targetType.exclusiveWithAll() == ExclusiveAllType.Exclusive && targetCount == TargetCount.All)
    }

    fun buildTargetClause(anyOfModifier: Boolean): String {
        return if (targetCount.pluralModifier == PluralModifier.Many && anyOfModifier) getString(
            R.string.any_of_s,
            buildTargetClause()
        ) else buildTargetClause()
    }

    fun buildTargetClause(): String {
        if (hasDependAction()) {
            return if (dependAction!!.parameter.actionType === ActionType.Damage) getString(
                R.string.targets_those_damaged_by_effect_d,
                dependAction!!.actionId % 100
            ) else getString(
                R.string.targets_of_effect_d,
                dependAction!!.actionId % 100
            )
        } else if (!hasRelationPhrase) {
            return targetType.description()
        } else if (!hasCountPhrase && !hasNthModifier && !hasRangePhrase && hasRelationPhrase) {
            return getString(R.string.targets_of_last_effect)
        } else if (hasCountPhrase && !hasNthModifier && !hasRangePhrase && hasRelationPhrase && !hasDirectionPhrase) {
            if (targetCount == TargetCount.All) {
                if (targetType.exclusiveWithAll() == ExclusiveAllType.Exclusive) return getString(
                    R.string.all_s_targets,
                    targetAssignment.description()
                ) else if (targetType.exclusiveWithAll() == ExclusiveAllType.Not) return getString(
                    R.string.all_s_s_targets,
                    targetAssignment.description(),
                    targetType.description()
                ) else if (targetType.exclusiveWithAll() == ExclusiveAllType.HalfExclusive) {
                    return getString(
                        R.string.all_s_targets,
                        targetAssignment.description()
                    ) + getString(R.string.except_self)
                }
            } else return if (targetCount == TargetCount.One && targetType.ignoresOne()) {
                getString(
                    R.string.s_s_target,
                    targetType.description(),
                    targetAssignment.description()
                )
            } else {
                getString(
                    R.string.s_s_s,
                    targetType.description(),
                    targetAssignment.description(),
                    targetCount.description()
                )
            }
        } else if (hasCountPhrase && !hasNthModifier && !hasRangePhrase && hasRelationPhrase && hasDirectionPhrase && targetType.exclusiveWithAll() == ExclusiveAllType.Exclusive) {
            return when (targetAssignment) {
                TargetAssignment.Enemy -> getString(R.string.all_front_enemy_targets)
                TargetAssignment.Friendly -> getString(R.string.all_front_including_self_friendly_targets)
                else -> getString(R.string.all_front_targets)
            }
        } else if (hasCountPhrase && !hasNthModifier && !hasRangePhrase && hasRelationPhrase && hasDirectionPhrase && targetType.exclusiveWithAll() == ExclusiveAllType.Not) {
            return when (targetAssignment) {
                TargetAssignment.Enemy -> getString(
                    R.string.all_front_s_enemy_targets,
                    targetType.description()
                )
                TargetAssignment.Friendly -> getString(
                    R.string.all_front_including_self_s_friendly_targets,
                    targetType.description()
                )
                else -> getString(
                    R.string.all_front_s_targets,
                    targetType.description()
                )
            }
        } else if (hasCountPhrase && !hasNthModifier && !hasRangePhrase && hasRelationPhrase && hasDirectionPhrase && targetType.exclusiveWithAll() == ExclusiveAllType.HalfExclusive) {
            return when (targetAssignment) {
                TargetAssignment.Enemy -> getString(R.string.all_front_enemy_targets) + getString(
                    R.string.except_self
                )
                TargetAssignment.Friendly -> getString(R.string.all_front_including_self_friendly_targets)
                else -> getString(R.string.all_front_targets) + getString(
                    R.string.except_self
                )
            }
        } else if (!hasCountPhrase && !hasNthModifier && hasRangePhrase && hasRelationPhrase && !hasDirectionPhrase) {
            return getString(
                R.string.s1_targets_in_range_d2,
                targetAssignment.description(),
                targetRange.rawRange
            )
        } else if (!hasCountPhrase && !hasNthModifier && hasRangePhrase && hasRelationPhrase && hasDirectionPhrase) {
            return getString(
                R.string.front_s1_targets_in_range_d2,
                targetAssignment.description(),
                targetRange.rawRange
            )
        } else if (!hasCountPhrase && hasNthModifier && hasRangePhrase && hasRelationPhrase) {
            return getString(R.string.targets_of_last_effect)
        } else if (hasCountPhrase && !hasNthModifier && hasRangePhrase && hasRelationPhrase && !hasDirectionPhrase) {
            if (targetCount == TargetCount.All) {
                if (targetType.exclusiveWithAll() == ExclusiveAllType.Exclusive) return getString(
                    R.string.s1_targets_in_range_d2,
                    targetAssignment.description(),
                    targetRange.rawRange
                ) else if (targetType.exclusiveWithAll() == ExclusiveAllType.Not) return getString(
                    R.string.s1_s2_target_in_range_d3,
                    targetAssignment.description(),
                    targetType.description(),
                    targetRange.rawRange
                ) else if (targetType.exclusiveWithAll() == ExclusiveAllType.HalfExclusive) return getString(
                    R.string.s1_targets_in_range_d2,
                    targetAssignment.description() + getString(R.string.except_self),
                    targetRange.rawRange
                )
            } else return if (targetCount == TargetCount.One && targetType.ignoresOne()) {
                getString(
                    R.string.s1_s2_target_in_range_d3,
                    targetType.description(),
                    targetAssignment.description(),
                    targetRange.rawRange
                )
            } else {
                getString(
                    R.string.s1_s2_s3_in_range_d4,
                    targetType.description(),
                    targetAssignment.description(),
                    targetCount.description(),
                    targetRange.rawRange
                )
            }
        } else if (hasCountPhrase && !hasNthModifier && hasRangePhrase && hasRelationPhrase && hasDirectionPhrase) {
            if (targetCount == TargetCount.All) {
                if (targetType.exclusiveWithAll() == ExclusiveAllType.Exclusive) return getString(
                    R.string.front_s1_targets_in_range_d2,
                    targetAssignment.description(),
                    targetRange.rawRange
                ) else if (targetType.exclusiveWithAll() == ExclusiveAllType.Not) return getString(
                    R.string.front_s1_s2_targets_in_range_d3,
                    targetAssignment.description(),
                    targetType.description(),
                    targetRange.rawRange
                ) else if (targetType.exclusiveWithAll() == ExclusiveAllType.HalfExclusive) return getString(
                    R.string.front_s1_targets_in_range_d2,
                    targetAssignment.description() + getString(R.string.except_self),
                    targetRange.rawRange
                )
            } else return if (targetCount == TargetCount.One && targetType.ignoresOne()) {
                getString(
                    R.string.s1_front_s2_target_in_range_d3,
                    targetType.description(),
                    targetAssignment.description(),
                    targetRange.rawRange
                )
            } else {
                getString(
                    R.string.s1_front_s2_s3_in_range_d4,
                    targetType.description(),
                    targetAssignment.description(),
                    targetCount.description(),
                    targetRange.rawRange
                )
            }
        } else if (hasCountPhrase && hasNthModifier && !hasRangePhrase && hasRelationPhrase && !hasDirectionPhrase) {
            return if (targetCount == TargetCount.One && targetType.ignoresOne()) {
                getString(
                    R.string.s_s_target,
                    targetType.description(targetNumber, null),
                    targetAssignment.description()
                )
            } else {
                val modifier = getString(
                    R.string.s1_to_s2,
                    targetNumber.description(),
                    untilNumber.description()
                )
                getString(
                    R.string.s_s_s,
                    targetType.description(targetNumber, modifier),
                    targetAssignment.description(),
                    targetCount.pluralModifier!!.description()
                )
            }
        } else if (hasCountPhrase && hasNthModifier && !hasRangePhrase && hasRelationPhrase && hasDirectionPhrase) {
            val modifier =
                getString(R.string.s1_to_s2, targetNumber.description(), untilNumber.description())
            return getString(
                R.string.s1_front_s2_s3,
                targetType.description(targetNumber, modifier),
                targetAssignment.description(),
                targetCount.pluralModifier!!.description()
            )
        } else if (hasCountPhrase && hasNthModifier && hasRangePhrase && hasRelationPhrase && !hasDirectionPhrase) {
            return if (targetCount == TargetCount.One && targetType.ignoresOne()) {
                getString(
                    R.string.s1_s2_target_in_range_d3,
                    targetType.description(targetNumber, null),
                    targetAssignment.description(),
                    targetRange.rawRange
                )
            } else {
                val modifier = getString(
                    R.string.s1_to_s2,
                    targetNumber.description(),
                    untilNumber.description()
                )
                getString(
                    R.string.s1_s2_s3_in_range_d4,
                    targetType.description(targetNumber, modifier),
                    targetAssignment.description(),
                    targetCount.pluralModifier!!.description(),
                    targetRange.rawRange
                )
            }
        } else if (hasCountPhrase && hasNthModifier && hasRangePhrase && hasRelationPhrase && hasDirectionPhrase) {
            return if (targetCount == TargetCount.One && targetType.ignoresOne()) {
                getString(
                    R.string.s1_front_s2_target_in_range_d3,
                    targetType.description(targetNumber, null),
                    targetAssignment.description(),
                    targetRange.rawRange
                )
            } else {
                val modifier = getString(
                    R.string.s1_to_s2,
                    targetNumber.description(),
                    untilNumber.description()
                )
                getString(
                    R.string.s1_front_s2_s3_in_range_d4,
                    targetType.description(targetNumber, modifier),
                    targetAssignment.description(),
                    targetCount.pluralModifier!!.description(),
                    targetRange.rawRange
                )
            }
        }
        return ""
    }

    private val untilNumber: TargetNumber
        get() {
            val tUntil = TargetNumber.parse(targetNumber.value + targetCount.value)
            return if (tUntil == TargetNumber.Other) TargetNumber.Fifth else tUntil
        }

    init {
        this.targetAssignment = TargetAssignment.parse(targetAssignment)
        this.targetNumber = TargetNumber.parse(targetNumber)
        rawTargetType = targetType
        this.targetType = TargetType.parse(targetType)
        this.targetRange = TargetRange.parse(targetRange)
        direction = DirectionType.parse(targetArea)
        this.targetCount = TargetCount.parse(targetCount)
        this.dependAction = dependAction
        setBooleans()
    }
}

enum class TargetAssignment(val value: Int) {
    None(0), Enemy(1), Friendly(2), All(3);

    fun description(): String {
        return when (this) {
            Enemy -> getString(R.string.enemy)
            Friendly -> getString(R.string.friendly)
            All -> getString(R.string.both_sides)
            else -> ""
        }
    }

    companion object {
        fun parse(value: Int): TargetAssignment {
            for (item in values()) {
                if (item.value == value) return item
            }
            return None
        }
    }
}

enum class TargetNumber(val value: Int) {
    First(0), Second(1), Third(2), Fourth(3), Fifth(4), Other(5);

    fun description(): String {
        return when (this) {
            First -> getString(R.string.first)
            Second -> getString(R.string.second)
            Third -> getString(R.string.third)
            Fourth -> getString(R.string.fourth)
            Fifth -> getString(R.string.fifth)
            else -> ""
        }
    }

    companion object {
        fun parse(value: Int): TargetNumber {
            for (item in values()) {
                if (item.value == value) return item
            }
            return Other
        }
    }
}

enum class TargetType(val value: Int) {
    Unknown(-1),
    Zero(0),
    None(1),
    Random(2),
    Near(3),
    Far(4),
    HpAscending(5),
    HpDescending(6),
    Self(7),
    RandomOnce(8),
    Forward(9),
    Backward(10),
    Absolute(11),
    TpDescending(12),
    TpAscending(13),
    AtkDescending(14),
    AtkAscending(15),
    MagicStrDescending(16),
    MagicStrAscending(17),
    Summon(18),
    TpReducing(19),
    Physics(20),
    Magic(21),
    AllSummonRandom(22),
    SelfSummonRandom(23),
    Boss(24),
    HpAscendingOrNear(25),
    HpDescendingOrNear(26),
    TpDescendingOrNear(27),
    TpAscendingOrNear(28),
    AtkDescendingOrNear(29),
    AtkAscendingOrNear(30),
    MagicStrDescendingOrNear(31),
    MagicStrAscendingOrNear(32),
    Shadow(33),
    NearWithoutSelf(34),
    HpDescendingOrNearForward(35),
    HpAscendingOrNearForward(36),
    TpDescendingOrMaxForward(37),
    BothAtkDescending(38),
    BothAtkAscending(39),
    EnergyAscBackWithoutOwner(41),
    ParentTargetParts(42),
    AtkDecForwardWithoutOwner(43);

    enum class ExclusiveAllType {
        Not, Exclusive, HalfExclusive
    }

    fun exclusiveWithAll(): ExclusiveAllType {
        return when (this) {
            Unknown, Magic, Physics, Summon, Boss -> ExclusiveAllType.Not
            NearWithoutSelf -> ExclusiveAllType.HalfExclusive
            else -> ExclusiveAllType.Exclusive
        }
    }

    fun ignoresOne(): Boolean {
        return when (this) {
            Unknown, Random, RandomOnce, Absolute, Summon, SelfSummonRandom, AllSummonRandom, Magic, Physics -> false
            else -> true
        }
    }

    fun description(): String {
        return when (this) {
            Unknown -> getString(
                R.string.unknown
            )
            Random, RandomOnce -> getString(
                R.string.random
            )
            Zero, Near, None -> getString(
                R.string.the_nearest
            )
            Far -> getString(
                R.string.the_farthest
            )
            HpAscending, HpAscendingOrNear -> getString(
                R.string.the_lowest_HP_ratio
            )
            HpAscendingOrNearForward -> getString(
                R.string.the_lowest_HP
            )
            HpDescendingOrNearForward -> getString(
                R.string.the_highest_HP
            )
            HpDescending, HpDescendingOrNear -> getString(
                R.string.the_highest_HP_ratio
            )
            Self -> getString(
                R.string.self
            )
            Forward -> getString(
                R.string.the_most_backward
            )
            Backward -> getString(
                R.string.the_most_forward
            )
            Absolute -> getString(
                R.string.targets_within_the_scope
            )
            TpDescending, TpDescendingOrNear, TpDescendingOrMaxForward -> getString(
                R.string.the_highest_TP
            )
            TpAscending, TpReducing, TpAscendingOrNear -> getString(
                R.string.the_lowest_TP
            )
            AtkDescending, AtkDescendingOrNear -> getString(
                R.string.the_highest_ATK
            )
            AtkAscending, AtkAscendingOrNear -> getString(
                R.string.the_lowest_ATK
            )
            MagicStrDescending, MagicStrDescendingOrNear -> getString(
                R.string.the_highest_Magic_STR
            )
            MagicStrAscending, MagicStrAscendingOrNear -> getString(
                R.string.the_lowest_Magic_STR
            )
            Summon -> getString(
                R.string.minion
            )
            Physics -> getString(
                R.string.physics
            )
            Magic -> getString(
                R.string.magics
            )
            AllSummonRandom -> getString(
                R.string.random_minion
            )
            SelfSummonRandom -> getString(
                R.string.random_self_minion
            )
            Boss -> getString(
                R.string.boss
            )
            Shadow -> getString(
                R.string.shadow
            )
            NearWithoutSelf -> getString(
                R.string.nearest_without_self
            )
            BothAtkDescending -> getString(
                R.string.the_highest_ATK_or_Magic_STR
            )
            BothAtkAscending -> getString(
                R.string.the_lowest_ATK_or_Magic_STR
            )
            EnergyAscBackWithoutOwner -> getString(
                R.string.the_lowest_TP_except_self
            )
            ParentTargetParts -> getString(
                R.string.parts
            )
            AtkDecForwardWithoutOwner -> getString(
                R.string.the_highest_ATK_except_caster
            )
        }
    }

    fun description(targetCount: TargetCount, localizedCount: String?): String {
        val localizedModifier = localizedCount ?: targetCount.description()
        return when (this) {
            Unknown -> getString(
                R.string.s_unknown_type,
                localizedModifier
            )
            Zero, Near, None -> getString(
                R.string.s_nearest,
                localizedModifier
            )
            Far -> getString(
                R.string.s_farthest,
                localizedModifier
            )
            HpAscending -> getString(
                R.string.s_lowest_HP_ratio,
                localizedModifier
            )
            HpDescending -> getString(
                R.string.s_highest_HP_ratio,
                localizedModifier
            )
            HpAscendingOrNearForward -> getString(
                R.string.s_lowest_HP,
                localizedModifier
            )
            HpDescendingOrNearForward -> getString(
                R.string.s_highest_HP,
                localizedModifier
            )
            Forward -> getString(
                R.string.s_most_backward,
                localizedModifier
            )
            Backward -> getString(
                R.string.s_most_forward,
                localizedModifier
            )
            TpDescending, TpDescendingOrNear, TpDescendingOrMaxForward -> getString(
                R.string.s_highest_TP,
                localizedModifier
            )
            TpAscending, TpReducing, TpAscendingOrNear -> getString(
                R.string.s_lowest_TP,
                localizedModifier
            )
            AtkDescending, AtkDescendingOrNear -> getString(
                R.string.s_highest_ATK,
                localizedModifier
            )
            AtkAscending, AtkAscendingOrNear -> getString(
                R.string.s_lowest_ATK,
                localizedModifier
            )
            MagicStrDescending, MagicStrDescendingOrNear -> getString(
                R.string.s_highest_Magic_STR,
                localizedModifier
            )
            MagicStrAscending, MagicStrAscendingOrNear -> getString(
                R.string.s_lowest_Magic_STR,
                localizedModifier
            )
            Random, RandomOnce -> getString(
                R.string.s_random,
                localizedModifier
            )
            Summon -> getString(
                R.string.s_minion,
                localizedModifier
            )
            Physics -> getString(
                R.string.s_physics,
                localizedModifier
            )
            Magic -> getString(
                R.string.s_magic,
                localizedModifier
            )
            Boss -> getString(
                R.string.s_boss,
                localizedModifier
            )
            Shadow -> getString(
                R.string.s_shadow,
                localizedModifier
            )
            NearWithoutSelf -> getString(
                R.string.s_nearest_without_self,
                localizedModifier
            )
            BothAtkDescending -> getString(
                R.string.s_the_highest_ATK_or_Magic_STR,
                localizedModifier
            )
            BothAtkAscending -> getString(
                R.string.s_the_lowest_ATK_or_Magic_STR,
                localizedModifier
            )
            EnergyAscBackWithoutOwner -> getString(
                R.string.s_the_lowest_TP_except_self,
                localizedModifier
            )
            AtkDecForwardWithoutOwner -> getString(
                R.string.s1_the_highest_ATK_except_caster
            )
            else -> description()
        }
    }

    fun description(targetNumber: TargetNumber, localizedNumber: String?): String {
        return if (targetNumber == TargetNumber.Second || targetNumber == TargetNumber.Third || targetNumber == TargetNumber.Fourth || targetNumber == TargetNumber.Fifth) {
            val localizedModifier = localizedNumber ?: targetNumber.description()
            when (this) {
                Unknown -> getString(
                    R.string.the_s_unknown_type,
                    localizedModifier
                )
                Zero, Near, None -> getString(
                    R.string.the_s_nearest,
                    localizedModifier
                )
                Far -> getString(
                    R.string.the_s_farthest,
                    localizedModifier
                )
                HpAscending, HpAscendingOrNear -> getString(
                    R.string.the_s_lowest_HP_ratio,
                    localizedModifier
                )
                HpDescending, HpDescendingOrNear -> getString(
                    R.string.the_s_highest_HP_ratio,
                    localizedModifier
                )
                HpAscendingOrNearForward -> getString(
                    R.string.the_s_lowest_HP,
                    localizedModifier
                )
                HpDescendingOrNearForward -> getString(
                    R.string.the_s_highest_HP,
                    localizedModifier
                )
                Forward -> getString(
                    R.string.the_s_most_backward,
                    localizedModifier
                )
                Backward -> getString(
                    R.string.the_s_most_forward,
                    localizedModifier
                )
                TpDescending, TpDescendingOrNear -> getString(
                    R.string.the_s_highest_TP,
                    localizedModifier
                )
                TpAscending, TpReducing, TpAscendingOrNear -> getString(
                    R.string.the_s_lowest_TP,
                    localizedModifier
                )
                AtkDescending, AtkDescendingOrNear -> getString(
                    R.string.the_s_highest_ATK,
                    localizedModifier
                )
                AtkAscending, AtkAscendingOrNear -> getString(
                    R.string.the_s_lowest_ATK,
                    localizedModifier
                )
                MagicStrDescending, MagicStrDescendingOrNear -> getString(
                    R.string.the_s_highest_Magic_STR,
                    localizedModifier
                )
                MagicStrAscending, MagicStrAscendingOrNear -> getString(
                    R.string.the_s_lowest_Magic_STR,
                    localizedModifier
                )
                NearWithoutSelf -> getString(
                    R.string.the_s_nearest_without_self
                )
                BothAtkDescending -> getString(
                    R.string.the_s_highest_ATK_or_Magic_STR,
                    localizedModifier
                )
                BothAtkAscending -> getString(
                    R.string.the_s_lowest_ATK_or_Magic_STR,
                    localizedModifier
                )
                EnergyAscBackWithoutOwner -> getString(
                    R.string.the_s_th_lowest_TP_except_self,
                    localizedModifier
                )
                AtkDecForwardWithoutOwner -> getString(
                    R.string.the_s1_highest_ATK_except_caster
                )
                else -> description()
            }
        } else {
            description()
        }
    }

    companion object {
        fun parse(value: Int): TargetType {
            for (item in values()) {
                if (item.value == value) return item
            }
            return Unknown
        }
    }
}

enum class TargetCount(val value: Int) {
    Zero(0), One(1), Two(2), Three(3), Four(4), All(99);

    fun description(): String {
        return when (this) {
            Zero -> ""
            One -> getString(R.string.one)
            Two -> getString(R.string.two)
            Three -> getString(R.string.three)
            Four -> getString(R.string.four)
            else -> getString(R.string.all)
        }
    }

    enum class PluralModifier {
        One, Many;

        fun description(): String {
            return when (this) {
                One -> getString(R.string.target)
                else -> getString(R.string.targets)
            }
        }
    }

    var pluralModifier: PluralModifier? = null

    companion object {
        fun parse(value: Int): TargetCount {
            for (item in values()) {
                if (item.value == value) return item
            }
            return All
        }
    }

    init {
        pluralModifier = if (value == 1) PluralModifier.One else PluralModifier.Many
    }
}

class TargetRange private constructor(range: Int) {
    var rawRange: Int
    var rangeType = 0

    companion object {
        const val ZERO = 0
        const val ALL = 1
        const val FINITE = 2
        const val INFINITE = 3
        const val UNKNOWN = 4
        fun parse(range: Int): TargetRange {
            return TargetRange(range)
        }
    }

    init {
        if (range == -1) {
            rangeType = INFINITE
        } else if (range == 0) {
            rangeType = ZERO
        } else if (range in 1..2159) {
            rangeType = FINITE
        } else if (range >= 2160) {
            rangeType = ALL
            rawRange = 2160
        } else {
            rangeType = UNKNOWN
        }
        rawRange = range
    }
}

enum class DirectionType(val value: Int) {
    Front(1), FrontAndBack(2), All(3);

    fun description(): String {
        return when (this) {
            Front -> getString(R.string.front_including_self)
            FrontAndBack -> getString(R.string.front_and_back)
            else -> ""
        }
    }

    fun rawDescription(): String {
        return when (this) {
            Front -> getString(R.string.front)
            FrontAndBack -> getString(R.string.front_and_back)
            else -> ""
        }
    }

    companion object {
        fun parse(value: Int): DirectionType {
            for (item in values()) {
                if (item.value == value) return item
            }
            return All
        }
    }
}