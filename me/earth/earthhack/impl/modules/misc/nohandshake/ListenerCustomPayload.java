/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  io.netty.util.ReferenceCounted
 *  net.minecraft.network.PacketBuffer
 *  net.minecraft.network.play.client.CPacketCustomPayload
 */
package me.earth.earthhack.impl.modules.misc.nohandshake;

import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCounted;
import me.earth.earthhack.impl.commands.packet.util.BufferUtil;
import me.earth.earthhack.impl.core.mixins.network.client.ICPacketCustomPayload;
import me.earth.earthhack.impl.event.events.network.PacketEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.misc.nohandshake.NoHandShake;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;

final class ListenerCustomPayload
extends ModuleListener<NoHandShake, PacketEvent.Send<CPacketCustomPayload>> {
    public ListenerCustomPayload(NoHandShake module) {
        super(module, PacketEvent.Send.class, CPacketCustomPayload.class);
    }

    @Override
    public void invoke(PacketEvent.Send<CPacketCustomPayload> event) {
        CPacketCustomPayload packet = (CPacketCustomPayload)event.getPacket();
        if (packet.getChannelName().equals("MC|Brand")) {
            PacketBuffer buffer = packet.getBufferData();
            BufferUtil.releaseBuffer((ReferenceCounted)buffer);
            ((ICPacketCustomPayload)packet).setData(new PacketBuffer(Unpooled.buffer()).writeString("vanilla"));
        }
    }
}

