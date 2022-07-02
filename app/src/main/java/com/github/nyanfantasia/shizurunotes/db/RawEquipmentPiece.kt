package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.EquipmentPiece

@Suppress("PropertyName")
class RawEquipmentPiece {
    var equipment_id = 0
    var equipment_name: String? = null
    val equipmentPiece: EquipmentPiece
        get() = EquipmentPiece(equipment_id, equipment_name!!)
}