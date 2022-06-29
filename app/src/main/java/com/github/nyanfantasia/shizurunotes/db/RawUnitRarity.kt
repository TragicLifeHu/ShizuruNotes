package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.Property

@Suppress("PropertyName", "PrivatePropertyName")
class RawUnitRarity {
    var unit_id = 0
    var rarity = 0
    var hp = 0.0
    var atk = 0.0
    var magic_str = 0.0
    var def = 0.0
    var magic_def = 0.0
    var physical_critical = 0.0
    var magic_critical = 0.0
    var wave_hp_recovery = 0.0
    var wave_energy_recovery = 0.0
    var dodge = 0.0
    var physical_penetrate = 0.0
    var magic_penetrate = 0.0
    var life_steal = 0.0
    var hp_recovery_rate = 0.0
    var energy_recovery_rate = 0.0
    var energy_reduce_rate = 0.0
    var accuracy = 0.0
    private var hp_growth = 0.0
    private var atk_growth = 0.0
    private var magic_str_growth = 0.0
    private var def_growth = 0.0
    private var magic_def_growth = 0.0
    private var physical_critical_growth = 0.0
    private var magic_critical_growth = 0.0
    private var wave_hp_recovery_growth = 0.0
    private var wave_energy_recovery_growth = 0.0
    private var dodge_growth = 0.0
    private var physical_penetrate_growth = 0.0
    private var magic_penetrate_growth = 0.0
    private var life_steal_growth = 0.0
    private var hp_recovery_rate_growth = 0.0
    private var energy_recovery_rate_growth = 0.0
    private var energy_reduce_rate_growth = 0.0
    private var accuracy_growth = 0.0
    val property: Property
        get() = Property(
            hp,
            atk,
            magic_str,
            def,
            magic_def,
            physical_critical,
            magic_critical,
            wave_hp_recovery,
            wave_energy_recovery,
            dodge,
            physical_penetrate,
            magic_penetrate,
            life_steal,
            hp_recovery_rate,
            energy_recovery_rate,
            energy_reduce_rate,
            accuracy
        )
    val propertyGrowth: Property
        get() = Property(
            hp_growth,
            atk_growth,
            magic_str_growth,
            def_growth,
            magic_def_growth,
            physical_critical_growth,
            magic_critical_growth,
            wave_hp_recovery_growth,
            wave_energy_recovery_growth,
            dodge_growth,
            physical_penetrate_growth,
            magic_penetrate_growth,
            life_steal_growth,
            hp_recovery_rate_growth,
            energy_recovery_rate_growth,
            energy_reduce_rate_growth,
            accuracy_growth
        )
}