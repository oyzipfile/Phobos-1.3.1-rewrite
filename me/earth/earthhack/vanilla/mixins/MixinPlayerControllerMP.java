/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package me.earth.earthhack.vanilla.mixins;

import me.earth.earthhack.api.event.bus.instance.Bus;
import me.earth.earthhack.api.event.events.Stage;
import me.earth.earthhack.impl.event.events.misc.BlockDestroyEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={PlayerControllerMP.class})
public abstract class MixinPlayerControllerMP {
    @Redirect(method={"onPlayerDestroyBlock"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z"))
    private boolean setBlockStateHook(World world, BlockPos pos, IBlockState newState, int flags) {
        BlockDestroyEvent event = new BlockDestroyEvent(Stage.PRE, pos);
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            return false;
        }
        boolean result = world.setBlockState(pos, newState, flags);
        if (result) {
            Bus.EVENT_BUS.post(new BlockDestroyEvent(Stage.POST, pos));
        }
        return result;
    }
}

