package net.dragoncoding.palworld.breedingcalculator.parsing

import net.dragoncoding.palworld.breedingcalculator.DataCache
import net.dragoncoding.palworld.breedingcalculator.models.BreedingOverride
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class UniqueBreedingParser : DefaultHandler() {

    lateinit var overrides: ArrayList<BreedingOverride>

    private var parentId1: Int? = null
    private var parentId1Add: Char? = null
    private var parentId2: Int? = null
    private var parentId2Add: Char? = null
    private var childId: Int? = null
    private var childIdAdd: Char? = null

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        when (qName) {
            ELEMENT_BREEDING_OVERRIDES -> overrides = arrayListOf()
            ELEMENT_BREEDING_OVERRIDE -> {
                parentId1 = attributes?.getValue(ATTR_PARENT_1_ID)?.toInt()
                parentId1Add = attributes?.getValue(ATTR_PARENT_1_ID_ADD)?.first()

                parentId2 = attributes?.getValue(ATTR_PARENT_2_ID)?.toInt()
                parentId2Add = attributes?.getValue(ATTR_PARENT_2_ID_ADD)?.first()

                childId = attributes?.getValue(ATTR_CHILD_ID)?.toInt()
                childIdAdd = attributes?.getValue(ATTR_CHILD_ID_ADD)?.first()
            }

            ELEMENT_SELF_BREEDING_ONLY -> {
                val id: Int? = attributes?.getValue(ATTR_PAL_ID)?.toInt()
                val idAdd: Char? = attributes?.getValue(ATTR_PAL_ID_ADD)?.first()
                DataCache.palList.firstOrNull { it.id == id && it.idAdd == idAdd }?.let {
                    it.isSelfBreedingOnly = true
                }
            }
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        when (qName) {
            ELEMENT_BREEDING_OVERRIDE -> {
                if (parentId1 == null || parentId2 == null || childId == null) {
                    return;
                }

                val override =
                    BreedingOverride(parentId1!!, parentId1Add, parentId2!!, parentId2Add, childId!!, childIdAdd)
                overrides.add(override)
            }
        }
    }
}