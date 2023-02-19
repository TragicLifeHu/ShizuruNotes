package com.github.nyanfantasia.shizurunotes.data.action

enum class CriticalModifier(val value: Int) {
    Normal(0), Critical(1);

    companion object {
        @JvmStatic
        fun parse(value: Int): CriticalModifier {
            return when (value) {
                1 -> Critical
                else -> Normal
            }
        }
    }
}