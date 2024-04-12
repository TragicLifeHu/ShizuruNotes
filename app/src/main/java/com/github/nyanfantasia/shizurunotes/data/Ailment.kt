package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString

class Ailment(type: Int, detail: Int) {
    inner class AilmentDetail {
        @JvmField
        var detail: Any? = null
        fun setDetail(obj: Any?) {
            detail = obj
        }

        fun description(): String {
            return when (detail) {
                is DotDetail -> {
                    (detail as DotDetail).description()
                }
                is ActionDetail -> {
                    (detail as ActionDetail).description()
                }
                is CharmDetail -> {
                    (detail as CharmDetail).description()
                }
                else -> {
                    getString(R.string.Unknown)
                }
            }
        }
    }

    enum class DotDetail(val value: Int) {
        Detain(0),
        Poison(1),
        Burn(2),
        Curse(3),
        ViolentPoison(4),
        Hex(5),
        Compensation(6),
        Unknown(-1);

        fun description(): String {
            return when (this) {
                Detain -> getString(R.string.Detain_Damage)
                Poison -> getString(R.string.Poison)
                Burn -> getString(R.string.Burn)
                Curse -> getString(R.string.Curse)
                ViolentPoison -> getString(R.string.Violent_Poison)
                Hex -> getString(R.string.Hex)
                else -> getString(R.string.Unknown)
            }
        }

        companion object {
            fun parse(value: Int): DotDetail {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    enum class CharmDetail(val value: Int) {
        Charm(0), Confuse(1);

        fun description(): String {
            return when {
                this == Charm -> getString(R.string.Charm)
                this == Confuse -> getString(R.string.Confuse)
                else -> getString(R.string.Unknown)
            }
        }

        companion object {
            fun parse(value: Int): CharmDetail? {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return null
            }
        }
    }

    enum class ActionDetail(val value: Int) {
        Slow(1),
        Haste(2),
        Paralyse(3),
        Freeze(4),
        Bind(5),
        Sleep(6),
        Stun(7),
        Petrify(8),
        Detain(9),
        Faint(10),
        TimeStop(11),
        Unknown(12);

        fun description(): String {
            return when (this) {
                Slow -> getString(R.string.Slow)
                Haste -> getString(R.string.Haste)
                Paralyse -> getString(R.string.Paralyse)
                Freeze -> getString(R.string.Freeze)
                Bind -> getString(R.string.Bind)
                Sleep -> getString(R.string.Sleep)
                Stun -> getString(R.string.Stun)
                Petrify -> getString(R.string.Petrify)
                Detain -> getString(R.string.Detain)
                Faint -> getString(R.string.Faint)
                TimeStop -> getString(R.string.time_stop)
                else -> getString(R.string.Unknown)
            }
        }

        companion object {
            fun parse(value: Int): ActionDetail {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    enum class AilmentType(val value: Int) {
        KnockBack(3),
        Action(8),
        Dot(9),
        Charm(11),
        Darken(12),
        Silence(13),
        Confuse(19),
        InstantDeath(30),
        CountBlind(56),
        InhibitHeal(59),
        AttackSeal(60),
        Fear(61),
        Awe(62),
        Toad(69),
        MaxHp(70),
        HpRegenerationDown(76),
        DamageTakenIncreased(78),
        DamageByBehaviour(79),
        Unknown(80);

        fun description(): String {
            return when (this) {
                KnockBack -> getString(R.string.Knock_Back)
                Action -> getString(R.string.Action)
                Dot -> getString(R.string.Dot)
                Charm -> getString(R.string.Charm)
                Darken -> getString(R.string.Blind)
                Silence -> getString(R.string.Silence)
                InstantDeath -> getString(R.string.Instant_Death)
                Confuse -> getString(R.string.Confuse)
                CountBlind -> getString(R.string.Count_Blind)
                InhibitHeal -> getString(R.string.Inhibit_Heal)
                Fear -> getString(R.string.Fear)
                AttackSeal -> getString(R.string.Seal)
                Awe -> getString(R.string.Awe)
                Toad -> getString(R.string.Polymorph)
                MaxHp -> getString(R.string.Changing_Max_HP)
                HpRegenerationDown -> getString(
                    R.string.HP_Regeneration_Down
                )
                DamageTakenIncreased -> getString(
                    R.string.Damage_Taken_Increased
                )
                DamageByBehaviour -> getString(
                    R.string.Damage_By_Behaviour
                )
                else -> getString(R.string.Unknown_Effect)
            }
        }

        companion object {
            fun parse(value: Int): AilmentType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Unknown
            }
        }
    }

    @JvmField
    var ailmentType: AilmentType = AilmentType.parse(type)

    @JvmField
    var ailmentDetail: AilmentDetail?
    fun description(): String {
        return if (ailmentDetail != null) ailmentDetail!!.description() else ailmentType.description()
    }

    init {
        ailmentDetail = AilmentDetail()
        when (ailmentType) {
            AilmentType.Action -> ailmentDetail!!.setDetail(ActionDetail.parse(detail))
            AilmentType.Dot, AilmentType.DamageByBehaviour -> ailmentDetail!!.setDetail(
                DotDetail.parse(
                    detail
                )
            )
            AilmentType.Charm -> ailmentDetail!!.setDetail(CharmDetail.parse(detail))
            else -> ailmentDetail = null
        }
    }
}