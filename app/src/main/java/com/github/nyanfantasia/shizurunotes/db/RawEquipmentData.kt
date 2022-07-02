package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.Property

@Suppress("PropertyName")
class RawEquipmentData {
    var equipment_id = 0
    var equipment_name: String? = null
    var description: String? = null
    var promotion_level = 0
    var craft_flg = 0
    var equipment_enhance_point = 0
    var sale_price = 0
    var require_level = 0
    var max_equipment_enhance_level = 0
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
    var catalog: String? = null
    var rarity = 0
    var condition_equipment_id_1 = 0
    var consume_num_1 = 0
    var condition_equipment_id_2 = 0
    var consume_num_2 = 0
    var condition_equipment_id_3 = 0
    var consume_num_3 = 0
    var condition_equipment_id_4 = 0
    var consume_num_4 = 0
    var condition_equipment_id_5 = 0
    var consume_num_5 = 0
    var condition_equipment_id_6 = 0
    var consume_num_6 = 0
    var condition_equipment_id_7 = 0
    var consume_num_7 = 0
    var condition_equipment_id_8 = 0
    var consume_num_8 = 0
    var condition_equipment_id_9 = 0
    var consume_num_9 = 0
    var condition_equipment_id_10 = 0
    var consume_num_10 = 0
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
}