package net.dragoncoding.palworld.breedingcalculator.models

data class BreedingOverride(
    val parentOneId: Int,
    val parentOneIdAdd: Char?,
    val parentTwoId: Int,
    val parentTwoIdAdd: Char?,
    val childId: Int,
    val childIdAdd: Char?
) {
    fun isOverride(parentOne: PalModel, parentTwo: PalModel): Boolean {
        val sameOrder = parentOneId == parentOne.id
                && parentOneIdAdd == parentOne.idAdd
                && parentTwoId == parentTwo.id
                && parentTwoIdAdd == parentTwo.idAdd

        val switchedOrder = parentOneId == parentTwo.id
                && parentOneIdAdd == parentTwo.idAdd
                && parentTwoId == parentOne.id
                && parentTwoIdAdd == parentOne.idAdd

        return sameOrder || switchedOrder
    }

    fun hasChild(child: PalModel): Boolean {
        return childId == child.id && childIdAdd == child.idAdd
    }

    override fun toString(): String {
        val sb: StringBuilder = StringBuilder()
        sb.append(javaClass.simpleName)
        sb.append("[")

        sb.append("parentOne: $parentOneId${parentOneIdAdd ?: ""}")
        sb.append(", ")

        sb.append("parentTwo: $parentTwoId${parentTwoIdAdd ?: ""}")
        sb.append(", ")

        sb.append("child: $childId${childIdAdd ?: ""}")
        sb.append("]")

        return sb.toString()
    }
}
