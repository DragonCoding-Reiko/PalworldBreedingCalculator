package net.dragoncoding.palworld.breedingcalculator

import net.dragoncoding.palworld.breedingcalculator.models.BreedingOverride
import net.dragoncoding.palworld.breedingcalculator.models.PalBreeding
import net.dragoncoding.palworld.breedingcalculator.models.PalModel

object DataCache {
    var palList: ArrayList<PalModel> = arrayListOf()
    val breedingOverrides: ArrayList<BreedingOverride> = arrayListOf()
    val breedingResults: ArrayList<PalBreeding> = arrayListOf()
}