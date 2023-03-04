package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N
import com.github.nyanfantasia.shizurunotes.common.Statics

class Equipment(
    val equipmentId: Int,
    val equipmentName: String,
    val description: String,
    val promotionLevel: Int,
    val craftFlg: Int,
    val equipmentEnhancePoint: Int,
    val salePrice: Int,
    val requireLevel: Int,
    val maxEnhanceLevel: Int,
    val equipmentProperty: Property,
    var equipmentEnhanceRates: List<Property>,
    val catalog: String,
    val rarity: Int
) : Item {
    override val itemId: Int = equipmentId
    override val iconUrl = Statics.EQUIPMENT_ICON_URL.format(equipmentId)
    override val itemName: String = equipmentName
    override val itemType = ItemType.EQUIPMENT
    var craftMap: Map<Item, Int>? = null

    fun getCeilProperty(): Property {
        return getEnhancedProperty(maxEnhanceLevel)
    }

    fun getEnhancedProperty(level: Int): Property {
        if (equipmentEnhanceRates.isEmpty()) {
            return Property()
        }
        return if (equipmentId in uniqueEquipmentIdRange) {
            if (level <= 260 || equipmentEnhanceRates.size == 1) {
                equipmentProperty.plus(equipmentEnhanceRates[0].multiply((level - 1).toDouble())).ceil
            } else {
                equipmentProperty
                    .plus(equipmentEnhanceRates[0].multiply(259.toDouble())).ceil
                    .plus(equipmentEnhanceRates[1].multiply((level - 260).toDouble())).ceil
            }
        } else {
            equipmentProperty.plus(equipmentEnhanceRates[0].multiply(level.toDouble())).ceil
        }
    }

    fun getLeafCraftMap(): Map<Item, Int> {
        val leafMap = mutableMapOf<Item, Int>()
        craftMap?.forEach {
            addOrCreateLeaf(leafMap, it.key, it.value)
        }
        return leafMap
    }

    private fun addOrCreateLeaf(map: MutableMap<Item, Int>, item: Item, value: Int) {
        if (item is EquipmentPiece || item is GeneralItem || (item is Equipment && item.craftFlg == 0)) {
            if (map.containsKey(item)) {
                map[item]?.plus(value)
            } else {
                map[item] = value
            }
        } else if (item is Equipment && item.craftFlg == 1) {
            item.craftMap?.forEach {
                addOrCreateLeaf(map, it.key, it.value)
            }
        }
    }

    companion object {
        val uniqueEquipmentIdRange = 130000..139999
        val getNull = Equipment(999999,
            I18N.getString(R.string.unimplemented),
            "",
            0,
            0,
            0,
            0,
            0,
            0,
            Property(),
            listOf(Property(), Property()),
            "",
            0
        )
    }
}