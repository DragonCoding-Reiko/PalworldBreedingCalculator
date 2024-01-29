package net.dragoncoding.palworld.breedingcalculator.models

data class PalModel(val name: String, val id: Int, val power: Int, val order: Int, val idAdd: Char? = null) {
    var isUniqueBreeding: Boolean = false
    var isSelfBreedingOnly: Boolean = false

    override fun toString(): String {
        val idAddString = idAdd ?: ""

        val sb: StringBuilder = StringBuilder()
        sb.append(javaClass.simpleName)

        sb.append("[")
        sb.append("id: $id$idAddString")
        sb.append(", ")
        sb.append("name: $name")
        sb.append(", ")
        sb.append("power: $power")
        sb.append("]")

        return sb.toString()
    }
}