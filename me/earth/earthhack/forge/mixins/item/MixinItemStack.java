/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  org.spongepowered.asm.mixin.Dynamic
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.earth.earthhack.forge.mixins.item;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.misc.truedurability.TrueDurability;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ItemStack.class})
public abstract class MixinItemStack {
    private static ModuleCache<TrueDurability> trueDurability;
    @Shadow
    int itemDamage;

    @Inject(method={"<init>(Lnet/minecraft/item/Item;IILnet/minecraft/nbt/NBTTagCompound;)V"}, at={@At(value="RETURN")})
    @Dynamic
    private void initHook_Item(Item item, int amount, int meta, NBTTagCompound compound, CallbackInfo info) {
        if (trueDurability == null) {
            trueDurability = Caches.getModule(TrueDurability.class);
        }
        this.itemDamage = this.checkDurability(this.itemDamage, meta);
    }

    @Inject(method={"<init>(Lnet/minecraft/nbt/NBTTagCompound;)V"}, at={@At(value="RETURN")})
    private void initHook(NBTTagCompound compound, CallbackInfo info) {
        if (trueDurability == null) {
            trueDurability = Caches.getModule(TrueDurability.class);
        }
        this.itemDamage = this.checkDurability(this.itemDamage, compound.getShort("Damage"));
    }

    private int checkDurability(int damage, int meta) {
        int durability = damage;
        if (trueDurability != null && trueDurability.isEnabled() && meta < 0) {
            durability = meta;
        }
        return durability;
    }
}

