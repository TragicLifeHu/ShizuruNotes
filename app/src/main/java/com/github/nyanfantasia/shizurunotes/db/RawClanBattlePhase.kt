package com.github.nyanfantasia.shizurunotes.db

import com.github.nyanfantasia.shizurunotes.data.ClanBattlePhase

@Suppress("PrivatePropertyName")
class RawClanBattlePhase {
    private var phase = 0
    private var wave_group_id_1 = 0
    private var wave_group_id_2 = 0
    private var wave_group_id_3 = 0
    private var wave_group_id_4 = 0
    private var wave_group_id_5 = 0
    val clanBattlePhase: ClanBattlePhase
        get() = ClanBattlePhase(
            phase,
            if (wave_group_id_1 == 0) null else wave_group_id_1,
            if (wave_group_id_2 == 0) null else wave_group_id_2,
            if (wave_group_id_3 == 0) null else wave_group_id_3,
            if (wave_group_id_4 == 0) null else wave_group_id_4,
            if (wave_group_id_5 == 0) null else wave_group_id_5
        )
}