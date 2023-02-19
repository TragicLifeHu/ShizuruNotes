package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.ClanBattlePeriod
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("PropertyName")
class RawClanBattlePeriod {
    var clan_battle_id = 0
    var release_month = 0
    var start_time: String? = null
    var end_time: String? = null
    fun transToClanBattlePeriod(): ClanBattlePeriod {
        val formatter = DateTimeFormatter.ofPattern("yyyy/M/dd H:mm:ss")
        return ClanBattlePeriod(
            clan_battle_id,
            LocalDateTime.parse(start_time, formatter),
            LocalDateTime.parse(end_time, formatter)
        )
    }
}