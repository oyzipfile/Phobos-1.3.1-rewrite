/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketMultiBlockChange
 *  net.minecraft.network.play.server.SPacketMultiBlockChange$BlockUpdateData
 */
package me.earth.earthhack.impl.modules.player.automine;

import me.earth.earthhack.impl.event.events.network.PacketEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.player.automine.AutoMine;
import net.minecraft.network.play.server.SPacketMultiBlockChange;

final class ListenerMultiBlockChange
extends ModuleListener<AutoMine, PacketEvent.Receive<SPacketMultiBlockChange>> {
    public ListenerMultiBlockChange(AutoMine module) {
        super(module, PacketEvent.Receive.class, SPacketMultiBlockChange.class);
    }

    @Override
    public void invoke(PacketEvent.Receive<SPacketMultiBlockChange> event) {
        SPacketMultiBlockChange packet = (SPacketMultiBlockChange)event.getPacket();
        mc.addScheduledTask(() -> {
            if (((AutoMine)this.module).constellation == null) {
                return;
            }
            for (SPacketMultiBlockChange.BlockUpdateData data : packet.getChangedBlocks()) {
                if (!((AutoMine)this.module).constellation.isAffected(data.getPos(), data.getBlockState())) continue;
                ((AutoMine)this.module).constellation = null;
                break;
            }
        });
    }
}

