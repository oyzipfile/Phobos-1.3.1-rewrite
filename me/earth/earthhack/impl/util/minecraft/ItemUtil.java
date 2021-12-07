/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemExpBottle
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemLingeringPotion
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.item.ItemSplashPotion
 *  net.minecraft.item.ItemStack
 */
package me.earth.earthhack.impl.util.minecraft;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;

public class ItemUtil {
    public static boolean isThrowable(Item item) {
        return item instanceof ItemEnderPearl || item instanceof ItemExpBottle || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemFishingRod;
    }

    public static boolean areSame(Block block1, Block block2) {
        return Block.getIdFromBlock((Block)block1) == Block.getIdFromBlock((Block)block2);
    }

    public static boolean areSame(Item item1, Item item2) {
        return Item.getIdFromItem((Item)item1) == Item.getIdFromItem((Item)item2);
    }

    public static boolean areSame(Block block, Item item) {
        return item instanceof ItemBlock && ItemUtil.areSame(block, ((ItemBlock)item).getBlock());
    }

    public static boolean areSame(ItemStack stack, Block block) {
        if (stack == null) {
            return false;
        }
        if (block == Blocks.AIR && stack.isEmpty()) {
            return true;
        }
        return ItemUtil.areSame(block, stack.getItem());
    }

    public static boolean areSame(ItemStack stack, Item item) {
        return stack != null && ItemUtil.areSame(stack.getItem(), item);
    }
}

