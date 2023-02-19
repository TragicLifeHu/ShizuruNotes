package com.github.nyanfantasia.shizurunotes.data.action

enum class PercentModifier {
    Percent, Number;

    fun description(): String {
        return when (this) {
            Percent -> "%"
            else -> ""
        }
    }

    companion object {
        @JvmStatic
        fun parse(value: Int): PercentModifier {
            return when (value) {
                2 -> Percent
                else -> Number
            }
        }
    }
}