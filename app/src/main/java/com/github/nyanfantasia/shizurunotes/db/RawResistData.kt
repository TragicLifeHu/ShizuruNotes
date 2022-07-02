package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.db.DBHelper.Companion.get
import com.github.nyanfantasia.shizurunotes.utils.Utils.getValueFromObject

@Suppress("PropertyName")
class RawResistData {
    var resist_status_id = 0
    var ailment_1 = 0
    var ailment_2 = 0
    var ailment_3 = 0
    var ailment_4 = 0
    var ailment_5 = 0
    var ailment_6 = 0
    var ailment_7 = 0
    var ailment_8 = 0
    var ailment_9 = 0
    var ailment_10 = 0
    var ailment_11 = 0
    var ailment_12 = 0
    var ailment_13 = 0
    var ailment_14 = 0
    var ailment_15 = 0
    var ailment_16 = 0
    var ailment_17 = 0
    var ailment_18 = 0
    var ailment_19 = 0
    var ailment_20 = 0
    var ailment_21 = 0
    var ailment_22 = 0
    var ailment_23 = 0
    var ailment_24 = 0
    var ailment_25 = 0
    var ailment_26 = 0
    var ailment_27 = 0
    var ailment_28 = 0
    var ailment_29 = 0
    var ailment_30 = 0
    var ailment_31 = 0
    var ailment_32 = 0
    var ailment_33 = 0
    var ailment_34 = 0
    var ailment_35 = 0
    var ailment_36 = 0
    var ailment_37 = 0
    var ailment_38 = 0
    var ailment_39 = 0
    var ailment_40 = 0
    var ailment_41 = 0
    var ailment_42 = 0
    var ailment_43 = 0
    var ailment_44 = 0
    var ailment_45 = 0
    var ailment_46 = 0
    var ailment_47 = 0
    var ailment_48 = 0
    var ailment_49 = 0
    var ailment_50 = 0
    val resistData: Map<String, Int>
        get() {
            if (ailmentMap == null) {
                ailmentMap = get().ailmentMap
            }
            val resultMap: MutableMap<String, Int> = HashMap()
            for ((key, value) in ailmentMap!!) {
                resultMap[value] = getValueFromObject(this, "ailment_$key") as Int
            }
            return resultMap
        }

    companion object {
        var ailmentMap: Map<Int, String>? = null
    }
}