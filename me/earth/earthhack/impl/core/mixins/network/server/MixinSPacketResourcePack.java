/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.PacketBuffer
 *  net.minecraft.network.play.server.SPacketResourcePackSend
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.earth.earthhack.impl.core.mixins.network.server;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.misc.packets.Packets;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={SPacketResourcePackSend.class})
public abstract class MixinSPacketResourcePack {
    private static final ModuleCache<Packets> PACKETS = Caches.getModule(Packets.class);

    @Inject(method={"readPacketData"}, at={@At(value="HEAD")}, cancellable=true)
    private void readPacketDataHook(PacketBuffer buf, CallbackInfo ci) {
        if (PACKETS.returnIfPresent(Packets::areCCResourcesActive, false).booleanValue()) {
            buf.readerIndex(buf.writerIndex());
            ci.cancel();
        }
    }
}

