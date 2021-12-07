/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.EnumConnectionState
 *  net.minecraft.network.EnumPacketDirection
 *  net.minecraft.network.Packet
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package me.earth.earthhack.impl.core.mixins.network;

import me.earth.earthhack.impl.util.network.CustomPacket;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EnumConnectionState.class})
public abstract class MixinEnumConnectionState {
    @Inject(method={"getFromPacket"}, at={@At(value="HEAD")}, cancellable=true)
    private static void getFromPacketHook(Packet<?> packet, CallbackInfoReturnable<EnumConnectionState> cir) {
        if (packet instanceof CustomPacket) {
            cir.setReturnValue((Object)((CustomPacket)packet).getState());
        }
    }

    @Inject(method={"getPacketId"}, at={@At(value="HEAD")}, cancellable=true)
    private void getPacketIdHook(EnumPacketDirection dir, Packet<?> packet, CallbackInfoReturnable<Integer> cir) throws Exception {
        if (dir == EnumPacketDirection.SERVERBOUND && packet instanceof CustomPacket) {
            cir.setReturnValue((Object)((CustomPacket)packet).getId());
        }
    }
}

