package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N

internal enum class PatternType(val value: Int) {
    None(0),
    Hit(1),
    Main1(1001),
    Main2(1002),
    Main3(1003),
    Main4(1004),
    Main5(1005),
    Main6(1006),
    Main7(1007),
    Main8(1008),
    Main9(1009),
    Main10(1010),
    Sp1(2001),
    Sp2(2002),
    Sp3(2003),
    Sp4(2004),
    Sp5(2005);

    fun skillClass(): Skill.SkillClass {
        return when (this) {
            Main1 -> Skill.SkillClass.MAIN1
            Main2 -> Skill.SkillClass.MAIN2
            Main3 -> Skill.SkillClass.MAIN3
            Main4 -> Skill.SkillClass.MAIN4
            Main5 -> Skill.SkillClass.MAIN5
            Main6 -> Skill.SkillClass.MAIN6
            Main7 -> Skill.SkillClass.MAIN7
            Main8 -> Skill.SkillClass.MAIN8
            Main9 -> Skill.SkillClass.MAIN9
            Main10 -> Skill.SkillClass.MAIN10
            Sp1 -> Skill.SkillClass.SP1
            Sp2 -> Skill.SkillClass.SP2
            Sp3 -> Skill.SkillClass.SP3
            Sp4 -> Skill.SkillClass.SP4
            Sp5 -> Skill.SkillClass.SP5
            else -> Skill.SkillClass.UNKNOWN
        }
    }

    fun description(): String {
        return when (this) {
            Hit -> I18N.getStringWithSpace(R.string.hit)
            Main1 -> I18N.getStringWithSpace(R.string.main_skill_1)
            Main2 -> I18N.getStringWithSpace(R.string.main_skill_2)
            Main3 -> I18N.getStringWithSpace(R.string.main_skill_3)
            Main4 -> I18N.getStringWithSpace(R.string.main_skill_4)
            Main5 -> I18N.getStringWithSpace(R.string.main_skill_5)
            Main6 -> I18N.getStringWithSpace(R.string.main_skill_6)
            Main7 -> I18N.getStringWithSpace(R.string.main_skill_7)
            Main8 -> I18N.getStringWithSpace(R.string.main_skill_8)
            Main9 -> I18N.getStringWithSpace(R.string.main_skill_9)
            Main10 -> I18N.getStringWithSpace(R.string.main_skill_10)
            Sp1 -> I18N.getStringWithSpace(R.string.sp_skill_1)
            Sp2 -> I18N.getStringWithSpace(R.string.sp_skill_2)
            Sp3 -> I18N.getStringWithSpace(R.string.sp_skill_3)
            Sp4 -> I18N.getStringWithSpace(R.string.sp_skill_4)
            Sp5 -> I18N.getStringWithSpace(R.string.sp_skill_5)
            else -> ""
        }
    }

    companion object {
        fun parse(value: Int): PatternType {
            for (item in values()) {
                if (item.value == value) return item
            }
            return None
        }
    }
}