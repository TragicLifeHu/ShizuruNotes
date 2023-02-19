package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N

enum class EventType {
    Campaign,
    Hatsune,
    ClanBattle,
    Tower,
    Gacha;

    private val tangerine = 0xFFFFB878
    private val sage = 0xFF7AE7BF
    private val peacock = 0xFF46D6DB
    private val grape = 0xFFDBADFF
    private val flamingo = 0xFFFBD75B
    private val graphite = 0xFFE1E1E1

    val description: String
        get() = when (this) {
            Campaign -> I18N.getString(R.string.campaign)
            Hatsune -> I18N.getString(R.string.hatsune)
            ClanBattle -> I18N.getString(R.string.clanBattle)
            Tower -> I18N.getString(R.string.tower)
            Gacha -> I18N.getString(R.string.gacha)
//            else -> I18N.getString(R.string.unknown)
        }

    val color: Int
        get() = when (this) {
            Campaign -> sage.toInt()
            Hatsune -> tangerine.toInt()
            ClanBattle -> peacock.toInt()
            Tower -> grape.toInt()
            Gacha -> flamingo.toInt()
//            else -> Graphite.toInt()
        }
}