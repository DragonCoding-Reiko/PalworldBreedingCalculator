package net.dragoncoding.palworld.breedingcalculator

import net.dragoncoding.palworld.breedingcalculator.calculators.BreedingCalculator
import net.dragoncoding.palworld.breedingcalculator.parsing.PalParser
import net.dragoncoding.palworld.breedingcalculator.parsing.UniqueBreedingParser
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory


@SpringBootApplication
class BreedingCalculatorApplication

fun main(args: Array<String>) {
    loadData()

    var tester = DataCache.palList.firstOrNull { it.id == 100 && it.idAdd == null }
    var test = DataCache.breedingResults.filter { it.child == tester }.stream()
        .filter { it.parentOne != tester && it.parentTwo != tester }
        .filter { it.parentOne != it.parentTwo }.toList()
    test.forEach { println(it) }

    //val parent = DataCache.palList.firstOrNull { it.id == 8 }
    //val child = DataCache.palList.firstOrNull { it.id == 100 }

    //val test2 = ParentToChildCalculator().getPossibleBreedingRoutes(parent!!, child!!)
    runApplication<BreedingCalculatorApplication>(*args)
}

fun loadData() {
    loadPals()
    loadBreedingOverrides()
    updatePalsWithOverrideFlag()
    loadBreedings()
}

fun updatePalsWithOverrideFlag() {
    DataCache.breedingOverrides.forEach {
        DataCache.palList.firstOrNull { pal -> pal.id == it.childId && pal.idAdd == it.childIdAdd }?.let {
            it.isUniqueBreeding = true
        }
    }
}

fun loadPals() {
    val factory = SAXParserFactory.newInstance()
    val saxParser: SAXParser = factory.newSAXParser()
    val palParser = PalParser()

    saxParser.parse("src/main/resources/pals.xml", palParser);

    DataCache.palList.addAll(palParser.palList)
    //DataCache.palList.forEach { println(it.toString()) }
}

fun loadBreedings() {
    BreedingCalculator.calculateBreedings()
    //DataCache.breedingResults.forEach { println(it.toString()) }
}

fun loadBreedingOverrides() {
    val factory = SAXParserFactory.newInstance()
    val saxParser: SAXParser = factory.newSAXParser()
    val overridesParser = UniqueBreedingParser()

    saxParser.parse("src/main/resources/unique_breedings.xml", overridesParser);

    DataCache.breedingOverrides.addAll(overridesParser.overrides)
    //DataCache.breedingOverrides.forEach { println(it.toString()) }
}