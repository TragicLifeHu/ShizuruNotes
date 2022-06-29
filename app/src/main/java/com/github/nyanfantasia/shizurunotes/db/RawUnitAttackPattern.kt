package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.AttackPattern
import com.github.nyanfantasia.shizurunotes.utils.Utils
import java.util.ArrayList

@Suppress("PropertyName", "PrivatePropertyName")
class RawUnitAttackPattern {
    private var pattern_id = 0
    var unit_id = 0
    private var loop_start = 0
    private var loop_end = 0
    var atk_pattern_1 = 0
    var atk_pattern_2 = 0
    var atk_pattern_3 = 0
    var atk_pattern_4 = 0
    var atk_pattern_5 = 0
    var atk_pattern_6 = 0
    var atk_pattern_7 = 0
    var atk_pattern_8 = 0
    var atk_pattern_9 = 0
    var atk_pattern_10 = 0
    var atk_pattern_11 = 0
    var atk_pattern_12 = 0
    var atk_pattern_13 = 0
    var atk_pattern_14 = 0
    var atk_pattern_15 = 0
    var atk_pattern_16 = 0
    var atk_pattern_17 = 0
    var atk_pattern_18 = 0
    var atk_pattern_19 = 0
    var atk_pattern_20 = 0

    // mistake? deliberately? only cy knows
    val attackPattern: AttackPattern
        get() {
            val attackPatternList: MutableList<Int> = ArrayList()
            for (i in 1..20) {
                // mistake? deliberately? only cy knows
                if (i == 14) continue
                val atkPattern = Utils.getValueFromObject(this, "atk_pattern_$i") as Int
                if (atkPattern != 0) attackPatternList.add(atkPattern) else break
            }
            val realEnd: Int = if (loop_end >= 14) {
                loop_end - 1
            } else {
                loop_end
            }
            return AttackPattern(
                pattern_id,
                unit_id,
                loop_start,
                realEnd,
                attackPatternList
            )
        }
}