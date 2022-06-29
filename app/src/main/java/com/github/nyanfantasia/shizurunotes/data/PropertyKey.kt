package com.github.nyanfantasia.shizurunotes.data

import com.github.nyanfantasia.shizurunotes.common.I18N.Companion.getString
import com.github.nyanfantasia.shizurunotes.R
import java.util.ArrayList

enum class PropertyKey {
    Atk,
    Def,
    Dodge,
    EnergyRecoveryRate,
    EnergyReduceRate,
    Hp,
    HpRecoveryRate,
    LifeSteal,
    MagicCritical,
    MagicDef,
    MagicPenetrate,
    MagicStr,
    PhysicalCritical,
    PhysicalPenetrate,
    WaveEnergyRecovery,
    WaveHpRecovery,
    Accuracy,
    Unknown;

    val keys: List<PropertyKey>
        get() {
            val all: MutableList<PropertyKey> = ArrayList()
            all.add(Atk)
            all.add(Def)
            all.add(Dodge)
            all.add(EnergyRecoveryRate)
            all.add(EnergyReduceRate)
            all.add(Hp)
            all.add(HpRecoveryRate)
            all.add(LifeSteal)
            all.add(MagicCritical)
            all.add(MagicDef)
            all.add(MagicPenetrate)
            all.add(MagicStr)
            all.add(PhysicalCritical)
            all.add(PhysicalPenetrate)
            all.add(WaveEnergyRecovery)
            all.add(WaveHpRecovery)
            all.add(Accuracy)
            return all
        }

    fun description(): String {
        return when (this) {
            Atk -> getString(
                R.string.ATK
            )
            Def -> getString(
                R.string.DEF
            )
            Dodge -> getString(
                R.string.Dodge
            )
            EnergyRecoveryRate -> getString(
                R.string.Energy_Recovery_Rate
            )
            EnergyReduceRate -> getString(
                R.string.Energy_Reduce_Rate
            )
            Hp -> getString(
                R.string.HP
            )
            HpRecoveryRate -> getString(
                R.string.HP_Recovery_Rate
            )
            LifeSteal -> getString(
                R.string.Life_Steal
            )
            MagicCritical -> getString(
                R.string.Magic_Critical
            )
            MagicDef -> getString(
                R.string.Magic_DEF
            )
            MagicPenetrate -> getString(
                R.string.Magic_Penetrate
            )
            MagicStr -> getString(
                R.string.Magic_STR
            )
            PhysicalCritical -> getString(
                R.string.Physical_Critical
            )
            PhysicalPenetrate -> getString(
                R.string.Physical_Penetrate
            )
            WaveEnergyRecovery -> getString(
                R.string.Wave_Energy_Recovery
            )
            WaveHpRecovery -> getString(
                R.string.Wave_HP_Recovery
            )
            Accuracy -> getString(
                R.string.Accuracy
            )
            else -> getString(R.string.Unknown)
        }
    }
}