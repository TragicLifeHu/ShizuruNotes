package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property

class IfForChildrenAction : ActionParameter() {
    private var trueClause: String? = null
    private var falseClause: String? = null
    private var ifType: IfType? = null
    override fun childInit() {
        if (actionDetail2 != 0) {
            ifType = IfType.parse(actionDetail1)
            if (ifType != IfType.Unknown) {
                trueClause = getString(
                    R.string.use_d1_to_s2_if_s3,
                    actionDetail2 % 100,
                    targetParameter!!.buildTargetClause(true),
                    ifType!!.description()
                )
            } else {
                when (actionDetail1) {
                    in 600..699, 710 -> {
                        trueClause = getString(
                            R.string.use_d1_to_s2_in_state_of_ID_d3,
                            actionDetail2 % 10,
                            targetParameter!!.buildTargetClause(true),
                            actionDetail1 - 600
                        )
                    }
                    700 -> {
                        trueClause = getString(
                            R.string.use_d1_to_s2_if_it_is_alone,
                            actionDetail2 % 10, targetParameter!!.buildTargetClause(true)
                        )
                    }
                    in 901..999 -> {
                        trueClause = getString(
                            R.string.use_d1_if_s2_HP_is_below_d3,
                            actionDetail2 % 10,
                            targetParameter!!.buildTargetClause(true),
                            actionDetail1 - 900
                        )
                    }
                    1300 -> {
                        trueClause = getString(
                            R.string.use_d1_to_s2_if_target_is_magic_type,
                            actionDetail2 % 10, targetParameter!!.buildTargetClause(true)
                        )
                    }
                    1800 -> {
                        trueClause = getString(
                            R.string.Performs_d1_to_s2_if_it_is_a_multi_target_unit,
                            actionDetail2 % 10, targetParameter!!.buildTargetClause()
                        )
                    }
                    in 6000..6999 -> {
                        trueClause = getString(
                            R.string.use_d1_to_s2_in_state_of_ID_d3,
                            actionDetail2 % 10,
                            targetParameter!!.buildTargetClause(true),
                            actionDetail1 - 6000
                        )
                    }
                }
            }
        }
        if (actionDetail3 != 0) {
            ifType = IfType.parse(actionDetail1)
            if (ifType != IfType.Unknown) {
                falseClause = getString(
                    R.string.use_d1_to_s2_if_not_s3,
                    actionDetail3 % 100,
                    targetParameter!!.buildTargetClause(true),
                    ifType!!.description()
                )
            } else {
                when (actionDetail1) {
                    in 600..699, 710 -> {
                        falseClause = getString(
                            R.string.use_d1_to_s2_if_not_in_state_of_ID_d3,
                            actionDetail3 % 10,
                            targetParameter!!.buildTargetClause(true),
                            actionDetail1 - 600
                        )
                    }
                    700 -> {
                        falseClause = getString(
                            R.string.use_d1_to_s2_if_it_is_not_alone,
                            actionDetail3 % 10, targetParameter!!.buildTargetClause(true)
                        )
                    }
                    in 901..999 -> {
                        falseClause = getString(
                            R.string.use_d1_if_s2_HP_is_not_below_d3,
                            actionDetail3 % 10,
                            targetParameter!!.buildTargetClause(true),
                            actionDetail1 - 900
                        )
                    }
                    1300 -> {
                        falseClause = getString(
                            R.string.use_d1_to_s2_if_target_is_not_magic_type,
                            actionDetail3 % 10, targetParameter!!.buildTargetClause(true)
                        )
                    }
                    1800 -> {
                        trueClause = getString(
                            R.string.Performs_d1_to_s2_if_it_is_not_a_multi_target_unit,
                            actionDetail3 % 10, targetParameter!!.buildTargetClause()
                        )
                    }
                    in 6000..6999 -> {
                        falseClause = getString(
                            R.string.use_d1_to_s2_if_not_in_state_of_ID_d3,
                            actionDetail3 % 10,
                            targetParameter!!.buildTargetClause(true),
                            actionDetail1 - 6000
                        )
                    }
                }
            }
        }
    }

    override fun localizedDetail(level: Int, property: Property?): String? {
        if (actionDetail1 == 100 ||
            actionDetail1 == 101 ||
            actionDetail1 == 200 ||
            actionDetail1 == 300 ||
            actionDetail1 == 500 ||
            actionDetail1 == 501 ||
            actionDetail1 == 502 ||
            actionDetail1 == 503 ||
            actionDetail1 == 504 ||
            actionDetail1 == 511 ||
            actionDetail1 == 512 ||
            actionDetail1 in 600..899 ||
            actionDetail1 in 901..999 ||
            actionDetail1 == 1300 ||
            actionDetail1 == 1400 ||
            actionDetail1 == 1600 ||
            actionDetail1 == 1800 ||
            actionDetail1 in 6000..6999
        ) {
            if (trueClause != null && falseClause != null) return getString(
                R.string.Condition_s,
                trueClause + falseClause
            ) else if (trueClause != null) return getString(
                R.string.Condition_s,
                trueClause
            ) else if (falseClause != null) return getString(R.string.Condition_s, falseClause)
        } else if (actionDetail1 in 0..99) {
            if (actionDetail2 != 0 && actionDetail3 != 0) {
                return getString(
                    R.string.Random_event_d1_chance_use_d2_otherwise_d3,
                    actionDetail1,
                    actionDetail2 % 10,
                    actionDetail3 % 10
                )
            } else if (actionDetail2 != 0) {
                return getString(
                    R.string.Random_event_d1_chance_use_d2,
                    actionDetail1,
                    actionDetail2 % 10
                )
            } else if (actionDetail3 != 0) {
                return getString(
                    R.string.Random_event_d1_chance_use_d2,
                    100 - actionDetail1,
                    actionDetail3 % 10
                )
            }
        }
        return super.localizedDetail(level, property)
    }
}

internal enum class IfType(val value: Int) {
    Unknown(-1),
    Controlled(100),
    Hastened(101),
    Blind(200),
    Convert(300),
    Decoy(400),
    Burn(500),
    Curse(501),
    Poison(502),
    Venom(503),
    Hex(504),
    CurseOrHex(511),
    PoisonOrVenom(512),
    Break(710),
    Polymorph(1400),
    Fear(1600),
    Spy(1601),
    MagicDefDecreased(1700);

    fun description(): String {
        return when (this) {
            Controlled -> getString(R.string.controlled)
            Hastened -> getString(R.string.hastened)
            Blind -> getString(R.string.blinded)
            Convert -> getString(R.string.charmed_or_confused)
            Decoy -> getString(R.string.decoying)
            Burn -> getString(R.string.burned)
            Curse -> getString(R.string.cursed)
            Poison -> getString(R.string.poisoned)
            Venom -> getString(R.string.venom)
            PoisonOrVenom -> getString(R.string.poisoned_or_venom)
            Break -> getString(R.string.breaking)
            Polymorph -> getString(R.string.polymorph)
            Hex -> getString(R.string.hexed)
            CurseOrHex -> getString(R.string.cursed_or_hexed)
            Fear -> getString(R.string.feared)
            Spy -> getString(R.string.is_invisible)
            MagicDefDecreased -> getString(R.string.magic_defence_decreased)
            else -> getString(R.string.unknown)
        }
    }

    companion object {
        @JvmStatic
        fun parse(value: Int): IfType {
            for (item in values()) {
                if (item.value == value) return item
            }
            return Unknown
        }
    }
}