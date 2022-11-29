package com.github.nyanfantasia.shizurunotes.data.action

@Suppress("unused")
enum class ActionType(val value: Int) {
    Unknown(0),
    Damage(1),
    Move(2),
    Knock(3),
    Heal(4),
    Cure(5),
    Guard(6),
    ChooseArea(7),
    Ailment(8),
    Dot(9),
    Aura(10),
    Charm(11),
    Blind(12),
    Silence(13),
    ChangeMode(14),
    Summon(15),
    ChangeEnergy(16),
    Trigger(17),
    Charge(18),
    DamageCharge(19),
    Taunt(20),
    Invulnerable(21),
    ChangePattern(22),
    IfForChildren(23),
    Revival(24),
    ContinuousAttack(25),
    Additive(26),
    Multiple(27),
    IfForAll(28),
    ChangeSearchArea(29),
    InstantDeath(30),
    ContinuousAttackNearby(31),
    EnhanceLifeSteal(32),
    EnhanceStrikeBack(33),
    AccumulativeDamage(34),
    Seal(35),
    AttackField(36),
    HealField(37),
    ChangeParameterField(38),
    DotField(39),
    AilmentField(40),
    ChangeUBTime(41),
    LoopTrigger(42),
    IfHasTarget(43),
    WaveStartIdle(44),
    SkillCount(45),
    Gravity(46),
    UpperLimitAttack(47),
    Hot(48),
    Dispel(49),
    Channel(50),
    Division(51),
    ChangeWidth(52),
    IfExistsFieldForAll(53),
    Stealth(54),
    MoveParts(55),
    CountBlind(56),
    CountDown(57),
    StopFieldAction(58),
    InhibitHealAction(59),
    AttackSeal(60),
    Fear(61),
    Awe(62),
    Loop(63),
    Toad(69),
    KnightGuard(71),
    DamageCut(72),
    LogBarrier(73),
    Divide(74),
    ActionByHitCount(75),
    HealDown(76),
    PassiveSeal(77),
    PassiveDamageUp(78),
    DamageByBehaviourAction(79),
    ChangeSpeedOverlapAction(83),
    Ex(90),
    ExPlus(91),
    ChangeEnergyRecoveryRatioByDamage(92),
    IgnoreDecoyAction(93),
    EffectAction(94),
    SpyAction(95),
    ChangeEnergyFieldAction(96),
    ChangeEnergyByDamageAction(97),
    EnergyDamageReduceAction(98);

    companion object {
        @JvmStatic
        fun parse(value: Int): ActionType {
            for (item in values()) {
                if (item.value == value) return item
            }
            return Unknown
        }
    }
}