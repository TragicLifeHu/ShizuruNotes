package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.common.Statics

class EquipmentPiece(id: Int, name: String) : Item {
    override val itemId: Int = id
    override val itemName: String = name
    override val itemType: ItemType = ItemType.EQUIPMENT_PIECE
    override val iconUrl: String = Statics.EQUIPMENT_ICON_URL.format(itemId)
}