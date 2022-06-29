package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.utils.Utils
import java.util.ArrayList

@Suppress("PropertyName")
class RawUnitPromotion {
    var unit_id = 0
    var promotion_level = 0
    var equip_slot_1 = 0
    var equip_slot_2 = 0
    var equip_slot_3 = 0
    var equip_slot_4 = 0
    var equip_slot_5 = 0
    var equip_slot_6 = 0

    //            if(equip_id != 999999)
    val charaSlots: List<Int>
        get() {
            val slotList = ArrayList<Int>()
            for (i in 1..6) {
                val equipId = Utils.getValueFromObject(this, "equip_slot_$i") as Int
                //            if(equip_id != 999999)
                slotList.add(equipId)
            }
            return slotList
        }
}