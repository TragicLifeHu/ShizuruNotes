package com.github.nyanfantasia.shizurunotes.data.action

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.data.Property


class CopyAtkParamAction : ActionParameter() {
    enum class AtkType(val value: Int) {
        Atk(1), MagicStr(2);

        fun description(): String {
            return when (this) {
                Atk -> getString(R.string.ATK)
                MagicStr -> getString(R.string.Magic_STR)
            }
        }

        companion object {
            fun parse(value: Int): AtkType {
                for (item in entries) {
                    if (item.value == value) return item
                }
                return Atk
            }
        }
    }

    private var atkType: AtkType? = null
    override fun childInit() {
        super.childInit()
        atkType = AtkType.parse(actionDetail1)
    }

    override fun localizedDetail(level: Int, property: Property?): String {
        return getString(
            R.string.Use_param_s1_of_s2_for_action_d3,
            atkType!!.description(),
            targetParameter!!.buildTargetClause(),
            actionNum(actionDetail2)
        )
    }
}