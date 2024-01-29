package net.dragoncoding.palworld.breedingcalculator.models

data class PalHierarchyNode(val breeding: PalBreeding, val depth: Int) {
    val nodes: ArrayList<PalHierarchyNode> = arrayListOf()

    override fun toString(): String {
        return "PalHierarchyNode(breeding=$breeding, depth=$depth, nodes=$nodes)"
    }
}