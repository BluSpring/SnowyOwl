package xyz.bluspring.snowyowl.powers

import io.github.apace100.apoli.power.Power
import io.github.apace100.apoli.power.PowerTypeReference
import net.minecraft.util.Identifier

object PowerTypes {
    val INSULATION = PowerTypeReference<Power>(Identifier("snowy_owl", "insulation"))
    val NOT_SO_PICKY = PowerTypeReference<Power>(Identifier("snowy_owl", "not_so_picky"))
    val SNOW_SHOES = PowerTypeReference<Power>(Identifier("snowy_owl", "snowshoes"))
}