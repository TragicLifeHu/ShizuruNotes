package com.github.nyanfantasia.shizurunotes.data

import kotlin.math.ceil
import kotlin.math.roundToInt

@Suppress("SuspiciousVarProperty")
class Property {
    var hp = 0.0
    var atk = 0.0
    var magicStr = 0.0
    var def = 0.0
    var magicDef = 0.0
    var physicalCritical = 0.0
    var magicCritical = 0.0
    var waveHpRecovery = 0.0
    var waveEnergyRecovery = 0.0
    var dodge = 0.0
    var physicalPenetrate = 0.0
    var magicPenetrate = 0.0
    var lifeSteal = 0.0
    var hpRecoveryRate = 0.0
    var energyRecoveryRate = 0.0
    var energyReduceRate = 0.0
    var accuracy = 0.0
    var effectivePhysicalHp: Int = 0
        get() = (hp * (1 + def / 100)).roundToInt()
    var effectiveMagicalHp: Int = 0
        get() = (hp * (1 + magicDef / 100)).roundToInt()

    fun getEffectiveHp(physical: Int, magical: Int): Int {
        return (hp * (1 + (def * physical + magicDef * magical) / 10000)).roundToInt()
    }

    var physicalDamageCut: Double = 0.0
        get() = def / (100.0 + def)
    var magicalDamageCut: Double = 0.0
        get() = magicDef / (100.0 + magicDef)
    var hpRecovery: Double = 0.0
        get() = 1.0 + hpRecoveryRate / 100
    var tpUpRate: Double = 0.0
        get() = 1.0 + energyRecoveryRate / 100
    var tpRemain: Int = 0
        get() = (energyReduceRate * 10.0).roundToInt()

    var getHp = 0
        get() = hp.roundToInt()
    var getAtk = 0
        get() = atk.roundToInt()
    var getMagicStr = 0
        get() = magicStr.roundToInt()
    var getDef = 0
        get() = def.roundToInt()
    var getMagicDef = 0
        get() = magicDef.roundToInt()
    var getPhysicalCritical = 0
        get() = physicalCritical.roundToInt()
    var getMagicCritical = 0
        get() = magicCritical.roundToInt()
    var getWaveHpRecovery = 0
        get() = waveHpRecovery.roundToInt()
    var getWaveEnergyRecovery = 0
        get() = waveEnergyRecovery.roundToInt()
    var getDodge = 0
        get() = dodge.roundToInt()
    var getPhysicalPenetrate = 0
        get() = physicalPenetrate.roundToInt()
    var getMagicPenetrate = 0
        get() = magicPenetrate.roundToInt()
    var getLifeSteal = 0
        get() = lifeSteal.roundToInt()
    var getHpRecoveryRate = 0
        get() = hpRecoveryRate.roundToInt()
    var getEnergyRecoveryRate = 0
        get() = energyRecoveryRate.roundToInt()
    var getEnergyReduceRate = 0
        get() = energyReduceRate.roundToInt()
    var getAccuracy = 0
        get() = accuracy.roundToInt()

    constructor()
    constructor(
        hp: Double,
        atk: Double,
        magicStr: Double,
        def: Double,
        magicDef: Double,
        physicalCritical: Double,
        magicCritical: Double,
        waveHpRecovery: Double,
        waveEnergyRecovery: Double,
        dodge: Double,
        physicalPenetrate: Double,
        magicPenetrate: Double,
        lifeSteal: Double,
        hpRecoveryRate: Double,
        energyRecoveryRate: Double,
        energyReduceRate: Double,
        accuracy: Double
    ) {
        this.hp = hp
        this.atk = atk
        this.magicStr = magicStr
        this.def = def
        this.magicDef = magicDef
        this.physicalCritical = physicalCritical
        this.magicCritical = magicCritical
        this.waveHpRecovery = waveHpRecovery
        this.waveEnergyRecovery = waveEnergyRecovery
        this.dodge = dodge
        this.physicalPenetrate = physicalPenetrate
        this.magicPenetrate = magicPenetrate
        this.lifeSteal = lifeSteal
        this.hpRecoveryRate = hpRecoveryRate
        this.energyRecoveryRate = energyRecoveryRate
        this.energyReduceRate = energyReduceRate
        this.accuracy = accuracy
    }

    fun reverse(): Property {
        return Property(
            -hp,
            -atk,
            -magicStr,
            -def,
            -magicDef,
            -physicalCritical,
            -magicCritical,
            -waveHpRecovery,
            -waveEnergyRecovery,
            -dodge,
            -physicalPenetrate,
            -magicPenetrate,
            -lifeSteal,
            -hpRecoveryRate,
            -energyRecoveryRate,
            -energyReduceRate,
            -accuracy
        )
    }

    operator fun plus(rProperty: Property?): Property {
        return if (rProperty == null) this else Property(
            hp + rProperty.hp,
            atk + rProperty.atk,
            magicStr + rProperty.magicStr,
            def + rProperty.def,
            magicDef + rProperty.magicDef,
            physicalCritical + rProperty.physicalCritical,
            magicCritical + rProperty.magicCritical,
            waveHpRecovery + rProperty.waveHpRecovery,
            waveEnergyRecovery + rProperty.waveEnergyRecovery,
            dodge + rProperty.dodge,
            physicalPenetrate + rProperty.physicalPenetrate,
            magicPenetrate + rProperty.magicPenetrate,
            lifeSteal + rProperty.lifeSteal,
            hpRecoveryRate + rProperty.hpRecoveryRate,
            energyRecoveryRate + rProperty.energyRecoveryRate,
            energyReduceRate + rProperty.energyReduceRate,
            accuracy + rProperty.accuracy
        )
    }

    fun plusEqual(rProperty: Property?): Property {
        if (rProperty == null) return this
        hp += rProperty.hp
        atk += rProperty.atk
        magicStr += rProperty.magicStr
        def += rProperty.def
        magicDef += rProperty.magicDef
        physicalCritical += rProperty.physicalCritical
        magicCritical += rProperty.magicCritical
        waveHpRecovery += rProperty.waveHpRecovery
        waveEnergyRecovery += rProperty.waveEnergyRecovery
        dodge += rProperty.dodge
        physicalPenetrate += rProperty.physicalPenetrate
        magicPenetrate += rProperty.magicPenetrate
        lifeSteal += rProperty.lifeSteal
        hpRecoveryRate += rProperty.hpRecoveryRate
        energyRecoveryRate += rProperty.energyRecoveryRate
        energyReduceRate += rProperty.energyReduceRate
        accuracy += rProperty.accuracy
        return this
    }

    fun multiply(multiplier: Double): Property {
        return Property(
            hp * multiplier,
            atk * multiplier,
            magicStr * multiplier,
            def * multiplier,
            magicDef * multiplier,
            physicalCritical * multiplier,
            magicCritical * multiplier,
            waveHpRecovery * multiplier,
            waveEnergyRecovery * multiplier,
            dodge * multiplier,
            physicalPenetrate * multiplier,
            magicPenetrate * multiplier,
            lifeSteal * multiplier,
            hpRecoveryRate * multiplier,
            energyRecoveryRate * multiplier,
            energyReduceRate * multiplier,
            accuracy * multiplier
        )
    }

    fun multiplyEqual(multiplier: Double): Property {
        hp *= multiplier
        atk *= multiplier
        magicStr *= multiplier
        def *= multiplier
        magicDef *= multiplier
        physicalCritical *= multiplier
        magicCritical *= multiplier
        waveHpRecovery *= multiplier
        waveEnergyRecovery *= multiplier
        dodge *= multiplier
        physicalPenetrate *= multiplier
        magicPenetrate *= multiplier
        lifeSteal *= multiplier
        hpRecoveryRate *= multiplier
        energyRecoveryRate *= multiplier
        energyReduceRate *= multiplier
        accuracy *= multiplier
        return this
    }

    val ceil: Property
        get() = Property(
            ceil(hp),
            ceil(atk),
            ceil(magicStr),
            ceil(def),
            ceil(magicDef),
            ceil(physicalCritical),
            ceil(magicCritical),
            ceil(waveHpRecovery),
            ceil(waveEnergyRecovery),
            ceil(dodge),
            ceil(physicalPenetrate),
            ceil(magicPenetrate),
            ceil(lifeSteal),
            ceil(hpRecoveryRate),
            ceil(energyRecoveryRate),
            ceil(energyReduceRate),
            ceil(accuracy)
        )

    fun roundThenSubtract(rProperty: Property): Property {
        return Property(
            this.getHp.toDouble() - rProperty.getHp,
            this.getAtk.toDouble() - rProperty.getAtk,
            this.getMagicStr.toDouble() - rProperty.getMagicStr,
            this.getDef.toDouble() - rProperty.getDef,
            this.getMagicDef.toDouble() - rProperty.getMagicDef,
            this.getPhysicalCritical.toDouble() - rProperty.getPhysicalCritical,
            this.getMagicCritical.toDouble() - rProperty.getMagicCritical,
            this.getWaveHpRecovery.toDouble() - rProperty.getWaveHpRecovery,
            this.getWaveEnergyRecovery.toDouble() - rProperty.getWaveEnergyRecovery,
            this.getDodge.toDouble() - rProperty.getDodge,
            this.getPhysicalPenetrate.toDouble() - rProperty.getPhysicalPenetrate,
            this.getMagicPenetrate.toDouble() - rProperty.getMagicPenetrate,
            this.getLifeSteal.toDouble() - rProperty.getLifeSteal,
            this.getHpRecoveryRate.toDouble() - rProperty.getHpRecoveryRate,
            this.getEnergyRecoveryRate.toDouble() - rProperty.getEnergyRecoveryRate,
            this.getEnergyReduceRate.toDouble() - rProperty.getEnergyReduceRate,
            this.getAccuracy.toDouble() - rProperty.getAccuracy
        )
    }

    fun getItem(key: PropertyKey?): Double {
        return when (key) {
            PropertyKey.Atk -> atk
            PropertyKey.Def -> def
            PropertyKey.Dodge -> dodge
            PropertyKey.EnergyRecoveryRate -> energyRecoveryRate
            PropertyKey.EnergyReduceRate -> energyReduceRate
            PropertyKey.Hp -> hp
            PropertyKey.HpRecoveryRate -> hpRecoveryRate
            PropertyKey.LifeSteal -> lifeSteal
            PropertyKey.MagicCritical -> magicCritical
            PropertyKey.MagicDef -> magicDef
            PropertyKey.MagicPenetrate -> magicPenetrate
            PropertyKey.MagicStr -> magicStr
            PropertyKey.PhysicalCritical -> physicalCritical
            PropertyKey.PhysicalPenetrate -> physicalPenetrate
            PropertyKey.WaveEnergyRecovery -> waveEnergyRecovery
            PropertyKey.WaveHpRecovery -> waveHpRecovery
            PropertyKey.Accuracy -> accuracy
            else -> 0.0
        }
    }

    val nonZeroPropertiesMap: Map<PropertyKey, Int>
        get() {
            val map = HashMap<PropertyKey, Int>()
            for (key in PropertyKey.entries) {
                val value = ceil(getItem(key)).toInt()
                if (value.toDouble() != 0.0) {
                    map[key] = value
                }
            }
            return map
        }

    companion object {
        fun getPropertyWithKeyAndValue(
            property: Property?,
            key: PropertyKey?,
            value: Double
        ): Property {
            var rProperty = property
            if (rProperty == null) rProperty = Property()
            return when (key) {
                PropertyKey.Atk -> {
                    rProperty.atk += value
                    rProperty
                }
                PropertyKey.Def -> {
                    rProperty.def += value
                    rProperty
                }
                PropertyKey.Dodge -> {
                    rProperty.dodge += value
                    rProperty
                }
                PropertyKey.EnergyRecoveryRate -> {
                    rProperty.energyRecoveryRate += value
                    rProperty
                }
                PropertyKey.EnergyReduceRate -> {
                    rProperty.energyReduceRate += value
                    rProperty
                }
                PropertyKey.Hp -> {
                    rProperty.hp += value
                    rProperty
                }
                PropertyKey.HpRecoveryRate -> {
                    rProperty.hpRecoveryRate += value
                    rProperty
                }
                PropertyKey.LifeSteal -> {
                    rProperty.lifeSteal += value
                    rProperty
                }
                PropertyKey.MagicCritical -> {
                    rProperty.magicCritical += value
                    rProperty
                }
                PropertyKey.MagicDef -> {
                    rProperty.magicDef += value
                    rProperty
                }
                PropertyKey.MagicPenetrate -> {
                    rProperty.magicPenetrate += value
                    rProperty
                }
                PropertyKey.MagicStr -> {
                    rProperty.magicStr += value
                    rProperty
                }
                PropertyKey.PhysicalCritical -> {
                    rProperty.physicalCritical += value
                    rProperty
                }
                PropertyKey.PhysicalPenetrate -> {
                    rProperty.physicalPenetrate += value
                    rProperty
                }
                PropertyKey.WaveEnergyRecovery -> {
                    rProperty.waveEnergyRecovery += value
                    rProperty
                }
                PropertyKey.WaveHpRecovery -> {
                    rProperty.waveHpRecovery += value
                    rProperty
                }
                PropertyKey.Accuracy -> {
                    rProperty.accuracy += value
                    rProperty
                }
                else -> rProperty
            }
        }
    }
}