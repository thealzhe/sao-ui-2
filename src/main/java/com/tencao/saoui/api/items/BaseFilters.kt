/*
 * Copyright (C) 2016-2019 Arnaud 'Bluexin' Solé
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.tencao.saoui.api.items

import com.tencao.saomclib.Client
import com.tencao.saoui.api.screens.IIcon
import com.tencao.saoui.screens.util.toIcon
import com.tencao.saoui.util.IconCore
import net.minecraft.block.Blocks
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.container.Slot
import net.minecraft.item.*
import net.minecraftforge.fml.ModList

/**
 * TODO: use [ItemTool.getToolClasses] for moar modded compat
 */
enum class BaseFilters(val filter: (ItemStack, Boolean) -> Boolean) : IItemFilter { // Todo: support for TConstruct

    EQUIPMENT({ _, _ -> false }) {
        override val icon: IIcon
            get() = IconCore.EQUIPMENT
        override val displayName: String
            get() = I18n.format("sao.element.equipment")
        override val isCategory: Boolean
            get() = true
    },

    ARMOR({ _, _ -> false }) {
        override val icon: IIcon
            // get() = IconCore.ARMOR
            get() = Items.IRON_HORSE_ARMOR.toIcon()
        override val category: IItemFilter
            get() = EQUIPMENT
        override val displayName: String
            get() = I18n.format("sao.element.armor")
        override val isCategory: Boolean
            get() = true
    },

    HELMET({ stack, _ ->
        mc.player?.openContainer?.inventorySlots?.first { it.slotNumber == 5 }?.isItemValid(stack) == true
    }) {
        override val icon: IIcon
            get() = Items.CHAINMAIL_HELMET.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.helmet")

        override val category: IItemFilter
            get() = ARMOR

        override fun getValidSlots(): Set<Slot> {
            return IItemFilter.getPlayerSlots(5)
        }
    },

    CHESTPLATES({ stack, _ ->
        mc.player!!.openContainer!!.inventorySlots!!.first { it.slotNumber == 6 }.isItemValid(stack)
    }) {
        override val icon: IIcon
            get() = Items.CHAINMAIL_CHESTPLATE.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.chestplates")

        override val category: IItemFilter
            get() = ARMOR

        override fun getValidSlots(): Set<Slot> {
            return IItemFilter.getPlayerSlots(6)
        }
    },

    LEGGINS({ stack, _ ->
        mc.player!!.openContainer.inventorySlots.first { it.slotNumber == 7 }.isItemValid(stack)
    }) {
        override val icon: IIcon
            get() = Items.CHAINMAIL_LEGGINGS.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.leggings")

        override val category: IItemFilter
            get() = ARMOR

        override fun getValidSlots(): Set<Slot> {
            return IItemFilter.getPlayerSlots(7)
        }
    },

    BOOTS({ stack, _ ->
        mc.player!!.openContainer.inventorySlots.first { it.slotNumber == 8 }.isItemValid(stack)
    }) {
        override val icon: IIcon
            get() = Items.CHAINMAIL_BOOTS.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.boots")

        override val category: IItemFilter
            get() = ARMOR

        override fun getValidSlots(): Set<Slot> {
            return IItemFilter.getPlayerSlots(8)
        }
    },

    SHIELDS({ stack, _ ->
        stack.item.isShield(stack, null) ||
            stack.item is ShieldItem
    }) {
        override val icon: IIcon
            get() = Items.SHIELD.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.shields")

        override val category: IItemFilter
            get() = ARMOR

        override fun getValidSlots(): Set<Slot> {
            return IItemFilter.getPlayerSlots(45)
        }
    },

    WEAPONS({ _, _ -> false }) {
        override val icon: IIcon
            get() = Items.SPECTRAL_ARROW.toIcon()

        override val category: IItemFilter
            get() = EQUIPMENT

        override val displayName: String
            get() = I18n.format("sao.element.weapons")

        override val isCategory: Boolean
            get() = true
    },

    SWORDS({ stack, _ -> stack.item is SwordItem || stack.toolTypes.any { it.name.equals("sword", true) } }) {
        override val icon: IIcon
            get() = Items.IRON_SWORD.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.swords")

        override val category: IItemFilter
            get() = WEAPONS
    },

    BOWS({ stack, _ -> stack.item is BowItem || stack.item.getUseAction(stack) == UseAction.BOW || stack.toolTypes.any { it.name.equals("bow", true) } }) {
        override val icon: IIcon
            get() = Items.BOW.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.bows")

        override val category: IItemFilter
            get() = WEAPONS
    },

    TOOLS({ _, _ -> false }) {
        override val icon: IIcon
            get() = Items.IRON_HOE.toIcon()

        override val category: IItemFilter
            get() = EQUIPMENT

        override val displayName: String
            get() = I18n.format("sao.element.tools")

        override val isCategory: Boolean
            get() = true
    },

    PICKAXES({ stack, _ -> stack.item is PickaxeItem || stack.toolTypes.any { it.name.equals("pickaxe", true) } }) {
        override val icon: IIcon
            get() = Items.IRON_PICKAXE.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.pickaxe")

        override val category: IItemFilter
            get() = TOOLS
    },

    AXES({ stack, _ -> stack.item is AxeItem || stack.toolTypes.any { it.name.equals("axe", true) } }) {
        override val icon: IIcon
            get() = Items.IRON_AXE.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.axe")

        override val category: IItemFilter
            get() = TOOLS
    },

    SHOVELS({ stack, _ -> stack.item is ShovelItem }) {
        override val icon: IIcon
            get() = Items.IRON_SHOVEL.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.shovel")

        override val category: IItemFilter
            get() = TOOLS
    },

    COMPATTOOLS({ stack, _ ->
        val item = stack.item
        values().filter { it.category == TOOLS && it != COMPATTOOLS }.none { it.invoke(stack) } &&
            (item is ToolItem || item is HoeItem || item is ShearsItem)
    }) {
        override val icon: IIcon
            get() = Items.SHEARS.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.compattools")

        override val category: IItemFilter
            get() = TOOLS
    },

    /*
    ACCESSORY({ stack, _ -> baublesLoaded && stack.item is IBauble }) {
        override val icon: IIcon
            get() = Items.GHAST_TEAR.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.accessory")

        override val category: IItemFilter
            get() = EQUIPMENT
    },*/

    ITEMS({ _, _ -> false }) {
        override val displayName: String
            get() = I18n.format("sao.element.items")
        override val isCategory: Boolean
            get() = true
    },

    CONSUMABLES({ stack, _ ->
        val action = stack.useAction
        action == UseAction.DRINK || action == UseAction.EAT
    }) {
        override val icon: IIcon
            get() = Items.APPLE.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.consumables")

        override val category: IItemFilter
            get() = ITEMS
    },

    BLOCKS({ stack, _ -> stack.item is BlockItem }) {
        override val icon: IIcon
            get() = Blocks.COBBLESTONE.toIcon()
        override val displayName: String
            get() = I18n.format("sao.element.blocks")

        override val category: IItemFilter
            get() = ITEMS
    },

    /**
     * Default fallback filter
     */
    MATERIALS({ stack, _ -> ItemFilterRegister.findFilter(stack) == MATERIALS }) {
        override val icon: IIcon
            get() = Items.IRON_INGOT.toIcon()

        override val displayName: String
            get() = I18n.format("sao.element.materials")

        override val category: IItemFilter
            get() = ITEMS
    };

    override fun invoke(stack: ItemStack, equipped: Boolean) = filter(stack, equipped)

    companion object {
        val mc = Client.minecraft

        val baublesLoaded by lazy { ModList.get().isLoaded("baubles") }

        fun getBaubles(player: PlayerEntity): IInventory? {
            return if (!baublesLoaded) {
                null
            } else {
                null
                // TODO Fix me
                // BaublesApi.getBaubles(player)
            }
        }
    }
}
