/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.InventoryPlayer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.earth.earthhack.impl.core.mixins.entity.living.player;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.player.sorter.Sorter;
import me.earth.earthhack.impl.util.thread.Locks;
import net.minecraft.entity.player.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={InventoryPlayer.class})
public abstract class MixinInventoryPlayer {
    private static final ModuleCache<Sorter> SORTER = Caches.getModule(Sorter.class);

    @Redirect(method={"setPickedItemStack"}, at=@At(value="FIELD", target="Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I", opcode=181))
    private void setPickedItemStackHook(InventoryPlayer inventoryPlayer, int value) {
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
            inventoryPlayer.currentItem = value;
        });
    }

    @Redirect(method={"pickItem"}, at=@At(value="FIELD", target="Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I", opcode=181))
    private void pickItemHook(InventoryPlayer inventoryPlayer, int value) {
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
            inventoryPlayer.currentItem = value;
        });
    }

    @Inject(method={"changeCurrentItem"}, at={@At(value="HEAD")}, cancellable=true)
    private void changeCurrentItemHook(int direction, CallbackInfo ci) {
        if (SORTER.returnIfPresent(s -> s.scroll(direction), false).booleanValue()) {
            ci.cancel();
        }
    }
}

