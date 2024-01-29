package net.dragoncoding.palworld.breedingcalculator.calculators

import net.dragoncoding.palworld.breedingcalculator.DataCache
import net.dragoncoding.palworld.breedingcalculator.models.PalBreeding
import net.dragoncoding.palworld.breedingcalculator.models.PalHierarchyNode
import net.dragoncoding.palworld.breedingcalculator.models.PalModel

class ParentToChildCalculator {
    private val MAX_ITERATIONS: Int = 3;

    val workingBreedings: ArrayList<PalHierarchyNode> = arrayListOf()
    val alreadyCheckedBreedings: ArrayList<PalBreeding> = arrayListOf()

    fun getPossibleBreedingRoutes(desiredParent: PalModel, desiredChild: PalModel): ArrayList<PalHierarchyNode> {
        val possibleBreedings = getPossibleBreedings(desiredChild)

        println("${possibleBreedings.size} possible breedings for ${desiredChild.toString()}")
        for (breeding in possibleBreedings) {
            if (breeding.hasParent(desiredParent)) {
                workingBreedings.add(PalHierarchyNode(breeding, 0))
                continue;
            }

            val foundBreedingsParent1 = calculateBreeding(breeding.parentOne, desiredParent, 1)
            //workingBreedings.addAll(foundBreedingsParent1)

            val foundBreedingsParent2 = calculateBreeding(breeding.parentTwo, desiredParent, 1)
            //workingBreedings.addAll(foundBreedingsParent2)
        }

        return workingBreedings
    }

    private fun calculateBreeding(desiredChild: PalModel, desiredParent: PalModel, iteration: Int): List<PalHierarchyNode> {
        val possibleBreedings = getPossibleBreedings(desiredChild)

        println("${possibleBreedings.size} possible breedings for ${desiredChild.toString()}")
        for (breeding in possibleBreedings) {
            if (breeding.hasParent(desiredParent)) {
                //workingBreedings.add(PalHierarchyNode(breeding, 0))
                continue;
            }
        }

        return workingBreedings
    }

    private fun getPossibleBreedings(target: PalModel): List<PalBreeding> {
        return DataCache.breedingResults.filter { it.child == target }.stream()
                .filter { it.parentOne != target && it.parentTwo != target }
                .filter { it.parentOne != it.parentTwo }.toList()
    }
}