package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.R
import com.github.nyanfantasia.shizurunotes.common.I18N

enum class CampaignType {
    None(0),
    HalfStaminaNormal(11),
    HalfStaminaHard,
    HalfStaminaBoth,
    HalfStaminaShrine,
    HalfStaminaTemple,
    HalfStaminaVeryHard,
    DropRareNormal(21),
    DropRareHard,
    DropRareBoth,
    DropRareVeryHard,
    DropAmountNormal(31),
    DropAmountHard,
    DropAmountBoth,
    DropAmountExploration,
    DropAmountDungeon,
    DropAmountCoop,
    DropAmountShrine,
    DropAmountTemple,
    DropAmountVeryHard,
    ManaNormal(41),
    ManaHard,
    ManaBoth,
    ManaExploration,
    ManaDungeon,
    ManaCoop,
    ManaTemple(48),
    ManaVeryHard,
    CoinDungeon(51),
    CoolTimeArena(61),
    CoolTimeGrandArena,
    PlayerExpAmountNormal(81),
    PlayerExpAmountHard,
    PlayerExpAmountVeryHard,
    PlayerExpAmountUniqueEquip,
    PlayerExpAmountHighRarityEquip,
    MasterCoin(90),
    MasterCoinNormal,
    MasterCoinHard,
    MasterCoinVeryHard,
    MasterCoinShrine,
    MasterCoinTemple,
    MasterCoinEventNormal,
    MasterCoinEventHard,
    MasterCoinRevivalEventNormal,
    MasterCoinRevivalEventHard,
    MasterCoinDropShioriNormal(100),
    MasterCoinDropShioriHard,
    HalfStaminaEventNormal(111),
    HalfStaminaEventHard,
    HalfStaminaEventBoth,
    DropRareEventNormal(121),
    DropRareEventHard,
    DropRareEventBoth,
    DropAmountEventNormal(131),
    DropAmountEventHard,
    DropAmountEventBoth,
    ManaEventNormal(141),
    ManaEventHard,
    ManaEventBoth,
    ExpEventNormal(151),
    ExpEventHard,
    ExpEventBoth,
    HalfStaminaRevivalEventNormal(211),
    HalfStaminaRevivalEventHard,
    DropRareRevivalEventNormal(221),
    DropRareRevivalEventHard,
    DropAmountRevivalEventNormal(231),
    DropAmountRevivalEventHard,
    ManaRevivalEventNormal(241),
    ManaRevivalEventHard,
    ExpRevivalEventNormal(251),
    ExpRevivalEventHard,
    PlayerExpAmountShioriNormal(351),
    PlayerExpAmountShioriHard;

    var value: Int = 0
    companion object {
        var nextValue: Int = 0
        fun parse(value: Int): CampaignType {
            entries.forEach {
                if (it.value == value) {
                    return it
                }
            }
            return None
        }
    }

    //缩写版
    //隐藏掉一些无关紧要的活动，以免显示的活动日程过多
    fun shortDescription(): String = when (this) {
        ManaDungeon -> I18N.getString(R.string.short_dungeon_s)
        MasterCoinNormal -> I18N.getString(R.string.short_master_coin_s)
        DropAmountNormal -> I18N.getString(R.string.short_normal_drop_s)
        DropAmountHard -> I18N.getString(R.string.short_hard_s)
        DropAmountShrine -> I18N.getString(R.string.short_shrine_s)
        DropAmountTemple -> I18N.getString(R.string.short_temple_s)
        ManaExploration -> I18N.getString(R.string.short_exploration_s)
        DropAmountVeryHard -> I18N.getString(R.string.short_very_hard_s)
        else -> ""
    }

    fun shortColor(): Int = when (this) {
        ManaDungeon -> (0xFF81C784).toInt()
        MasterCoinNormal -> (0xFFEF9A9A).toInt()
        DropAmountNormal -> (0xFF81D4FA).toInt()
        DropAmountHard -> (0xFF4FC3F7).toInt()
        DropAmountShrine -> (0xFFF8BBD0).toInt()
        DropAmountTemple -> (0xFFF8BBD0).toInt()
        ManaExploration -> (0xFFC5E1A5).toInt()
        DropAmountVeryHard -> (0xFF29B6F6).toInt()
        else -> 0
    }

    fun isVisible(): Boolean = shortDescription().isNotEmpty()

    private fun category(): String = when (this) {
        CoinDungeon,
        ManaDungeon,
        DropAmountDungeon -> I18N.getString(R.string.dungeon)
        ManaNormal,
        DropRareNormal,
        MasterCoinNormal,
        HalfStaminaNormal,
        PlayerExpAmountNormal,
        DropAmountNormal -> I18N.getString(R.string.normal)
        ExpEventNormal,
        ManaEventNormal,
        DropRareEventNormal,
        DropAmountEventNormal,
        MasterCoinDropShioriNormal,
        PlayerExpAmountShioriNormal,
        MasterCoinEventNormal -> I18N.getString(R.string.hatsune_normal)
        ExpRevivalEventNormal,
        ManaRevivalEventNormal,
        DropRareRevivalEventNormal,
        DropAmountRevivalEventNormal,
        MasterCoinRevivalEventNormal -> I18N.getString(R.string.revival_event_normal)
        ManaHard,
        DropRareHard,
        MasterCoinHard,
        HalfStaminaHard,
        PlayerExpAmountHard,
        DropAmountHard -> I18N.getString(R.string.hard)
        ExpEventHard,
        ManaEventHard,
        DropRareEventHard,
        DropAmountEventHard,
        MasterCoinDropShioriHard,
        PlayerExpAmountShioriHard,
        MasterCoinEventHard -> I18N.getString(R.string.hatsune_hard)
        ExpRevivalEventHard,
        ManaRevivalEventHard,
        DropRareRevivalEventHard,
        DropAmountRevivalEventHard,
        MasterCoinRevivalEventHard -> I18N.getString(R.string.revival_event_hard)
        DropAmountShrine,
        MasterCoinShrine,
        PlayerExpAmountHighRarityEquip,
        HalfStaminaShrine -> I18N.getString(R.string.shrine)
        ManaTemple,
        DropAmountTemple,
        MasterCoinTemple,
        PlayerExpAmountUniqueEquip,
        HalfStaminaTemple -> I18N.getString(R.string.temple)
        ManaExploration,
        DropAmountExploration -> I18N.getString(R.string.exploration)
        ManaVeryHard,
        DropRareVeryHard,
        DropAmountVeryHard,
        MasterCoinVeryHard,
        PlayerExpAmountVeryHard,
        HalfStaminaVeryHard -> I18N.getString(R.string.very_hard)
        else -> I18N.getString(R.string.others)
    }

    private fun bonus(): String = when (this) {
        PlayerExpAmountNormal,
        PlayerExpAmountHard,
        PlayerExpAmountVeryHard,
        PlayerExpAmountUniqueEquip,
        PlayerExpAmountHighRarityEquip -> I18N.getString(R.string.exp_s)

        else -> when (value / 10 % 10) {
            3 -> I18N.getString(R.string.drop_s)
            4 -> I18N.getString(R.string.mana_s)
            5 -> I18N.getString(R.string.exp_s)
            9 -> I18N.getString(R.string.master_coin_s)
            else -> I18N.getString(R.string.others)
        }
    }

    //完整的活动日程字符串
    fun description(): String = category() + bonus()

    constructor() {
        this.value = nextValue
        nextValue++
    }

    constructor(value: Int) {
        this.value = value
        nextValue = value + 1
    }
}