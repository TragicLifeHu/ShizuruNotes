package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.Property

@Suppress("PropertyName")
class RawPromotionBonus {
    var unit_id = 0
    var promotion_level = 0
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
    val promotionBonus: Property
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
}