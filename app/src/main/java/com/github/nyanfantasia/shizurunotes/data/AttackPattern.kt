package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.common.Statics

class AttackPattern(
    var patternId: Int,
    var unitId: Int,
    private var loopStart: Int,
    private var loopEnd: Int,
    private var rawAttackPatterns: List<Int>
) {
    var items: MutableList<AttackPatternItem> = ArrayList()

    fun setItems(skills: List<Skill>, atkType: Int): AttackPattern {
        items.clear()
        for (i in rawAttackPatterns.indices) {
            if (i >= loopEnd) break
            val raw = rawAttackPatterns[i]
            var iconUrl: String
            if (raw == 1) {
                iconUrl = if (atkType == 2) {
                    MAG_ICON
                } else {
                    PHY_ICON
                }
            } else {
                var skill: Skill? = null
                for (innerSkill in skills) {
                    if (innerSkill.skillClass === PatternType.parse(raw).skillClass()) {
                        skill = innerSkill
                        break
                    }
                }
                iconUrl = skill?.iconUrl ?: Statics.UNKNOWN_ICON
            }
            items.add(AttackPatternItem(raw, getLoopText(i), iconUrl))
        }
        return this
    }

    private fun getLoopText(index: Int): String {
        if (index + 1 == loopStart) return getString(R.string.loop_start)
        return if (index + 1 == loopEnd) getString(R.string.loop_end) else ""
    }

    inner class AttackPatternItem(rawAttackPatterns: Int, var loopText: String, var iconUrl: String?) {
        var skillText: String = PatternType.parse(rawAttackPatterns).description()

    }

    companion object {
        private const val PHY_ICON = Statics.API_URL + "/icon/equipment/101011.webp"
        private const val MAG_ICON = Statics.API_URL + "/icon/equipment/101251.webp"
    }
}