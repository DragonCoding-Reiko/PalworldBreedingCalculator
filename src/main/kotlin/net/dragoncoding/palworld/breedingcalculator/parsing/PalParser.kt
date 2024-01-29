package net.dragoncoding.palworld.breedingcalculator.parsing

import net.dragoncoding.palworld.breedingcalculator.models.PalModel
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class PalParser : DefaultHandler() {

    lateinit var palList: ArrayList<PalModel>


    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        when (qName) {
            ELEMENT_PALS -> palList = arrayListOf()
            ELEMENT_PAL -> {
                val currentName = attributes?.getValue(ATTR_PAL_NAME)
                val currentId = attributes?.getValue(ATTR_PAL_ID)?.toInt()
                val currentIdAdd = attributes?.getValue(ATTR_PAL_ID_ADD)?.first()
                val currentPower = attributes?.getValue(ATTR_PAL_POWER)?.toInt()
                val currentOrder = attributes?.getValue(ATTR_PAL_ORDER)?.toInt()

                if (currentName != null && currentId != null && currentPower != null && currentOrder != null) {
                    PalModel(currentName, currentId, currentPower, currentOrder, currentIdAdd)
                }
            }
        }
    }
}