package xyz.bluspring.snowyowl.duck

import net.minecraft.item.Item
import net.minecraft.item.ItemStack

interface ExtendedHungerManager {
    fun modifiedEat(item: Item, itemStack: ItemStack)
}