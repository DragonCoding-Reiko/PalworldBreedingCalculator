package net.dragoncoding.palworld.breedingcalculator.models

data class PalBreeding(val parentOne: PalModel, val parentTwo: PalModel, val child: PalModel) {
    val parentOneId: Int = parentOne.id
    val parentOneIdAdd: Char? = parentOne.idAdd
    val parentTwoId: Int = parentTwo.id
    val parentTwoIdAdd: Char? = parentTwo.idAdd
    val childId: Int = child.id
    val childIdAdd: Char? = child.idAdd

    fun isBreeding(parentOne: PalModel, parentTwo: PalModel): Boolean =
            (this.parentOne == parentOne && this.parentTwo == parentTwo)
                    || (this.parentOne == parentTwo && this.parentTwo == parentOne)

    fun hasParent(parent: PalModel): Boolean = this.parentOne == parent || this.parentTwo == parent

    override fun toString(): String {
        val sb: StringBuilder = StringBuilder()
        sb.append(javaClass.simpleName)
        sb.append("[")

        val parentOneString = "${parentOne.id}${parentOne.idAdd ?: ""} ${parentOne.name}: ${parentOne.power}p"
        sb.append("parentOne: $parentOneString")
        sb.append(", ")

        val parentTwoString = "${parentTwo.id}${parentTwo.idAdd ?: ""} ${parentTwo.name}: ${parentTwo.power}p"
        sb.append("parentTwo: $parentTwoString")
        sb.append(", ")

        val childString = "${child.id}${child.idAdd ?: ""} ${child.name}: ${child.power}p"
        sb.append("child: $childString")
        sb.append("]")

        return sb.toString()
    }
}