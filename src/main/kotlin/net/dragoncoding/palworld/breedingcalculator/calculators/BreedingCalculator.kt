package net.dragoncoding.palworld.breedingcalculator.calculators

import net.dragoncoding.palworld.breedingcalculator.DataCache
import net.dragoncoding.palworld.breedingcalculator.models.PalBreeding
import net.dragoncoding.palworld.breedingcalculator.models.PalModel
import kotlin.math.floor

object BreedingCalculator {
    private var palsByPower: List<PalModel>? = null

    fun calculateBreedings() {
        if (palsByPower == null) {
            palsByPower = DataCache.palList.stream()
                .filter { !it.isUniqueBreeding }
                .filter { !it.isSelfBreedingOnly }
                .sorted(compareBy(PalModel::power))
                .toList()
        }

        for (parentOne in DataCache.palList) {
            for (parentTwo in DataCache.palList) {
                if (DataCache.breedingResults.any { it.isBreeding(parentOne, parentTwo) }) {
                    continue
                }

                if (DataCache.breedingOverrides.any { it.isOverride(parentOne, parentTwo) }) {
                    calculateOverrideBreeding(parentOne, parentTwo)?.let {
                        DataCache.breedingResults.add(it)
                    }
                } else if (parentOne == parentTwo) {
                    DataCache.breedingResults.add(calculateSamePalsBreeding(parentOne, parentTwo))
                } else {
                    calculatePalBreeding(parentOne, parentTwo)?.let {
                        DataCache.breedingResults.add(it)
                    }
                }
            }
        }
    }

    private fun calculateOverrideBreeding(parentOne: PalModel, parentTwo: PalModel): PalBreeding? {
        val override = DataCache.breedingOverrides.firstOrNull { it.isOverride(parentOne, parentTwo) }

        if (override == null) {
            return null;
        }

        val parent1 =
            DataCache.palList.firstOrNull { it.id == override.parentOneId && it.idAdd == override.parentOneIdAdd }
        val parent2 =
            DataCache.palList.firstOrNull { it.id == override.parentTwoId && it.idAdd == override.parentTwoIdAdd }
        val child = DataCache.palList.firstOrNull { it.id == override.childId && it.idAdd == override.childIdAdd }

        if (parent1 == null || parent2 == null || child == null) {
            return null;
        }

        return PalBreeding(parent1, parent2, child)
    }

    private fun calculateSamePalsBreeding(parentOne: PalModel, parentTwo: PalModel): PalBreeding {
        return PalBreeding(parentOne, parentOne, parentOne)
    }

    private fun calculatePalBreeding(parentOne: PalModel, parentTwo: PalModel): PalBreeding? {
        val childPower = floor(((parentOne.power + parentTwo.power + 1) / 2.0)).toInt()

        palsByPower!!.firstOrNull { it.power == childPower }?.let {
            return PalBreeding(parentOne, parentTwo, it)
        }

        val lowerPowerPal = palsByPower!!.lastOrNull { it.power < childPower && !it.isUniqueBreeding }
        val greaterPowerPal = palsByPower!!.firstOrNull { it.power > childPower && !it.isUniqueBreeding }

        if (lowerPowerPal == null && greaterPowerPal == null) {
            return null
        }

        if (lowerPowerPal == null) {
            return PalBreeding(parentOne, parentTwo, greaterPowerPal!!)
        }

        if (greaterPowerPal == null) {
            return PalBreeding(parentOne, parentTwo, lowerPowerPal)
        }

        val lowerPowerDiff = childPower - lowerPowerPal.power
        val greaterPowerDiff = greaterPowerPal.power - childPower

        if (lowerPowerDiff != greaterPowerDiff) {
            val bestMatch = if (lowerPowerDiff < greaterPowerDiff) lowerPowerPal else greaterPowerPal
            return PalBreeding(parentOne, parentTwo, bestMatch)
        } else {
            val bestMatch = if (lowerPowerPal.id < greaterPowerPal.id) lowerPowerPal else greaterPowerPal
            return PalBreeding(parentOne, parentTwo, bestMatch)
        }
    }
}