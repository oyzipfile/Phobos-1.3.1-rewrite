/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketClickWindow
 */
package me.earth.earthhack.impl.modules.misc.pingspoof;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.impl.event.events.network.PacketEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.client.pingbypass.PingBypass;
import me.earth.earthhack.impl.modules.misc.pingspoof.PingSpoof;
import net.minecraft.network.play.client.CPacketClickWindow;

final class ListenerClickWindow
extends ModuleListener<PingSpoof, PacketEvent.Post<CPacketClickWindow>> {
    private static final ModuleCache<PingBypass> PINGBYPASS = Caches.getModule(PingBypass.class);

    public ListenerClickWindow(PingSpoof module) {
        super(module, PacketEvent.Post.class, CPacketClickWindow.class);
    }

    @Override
    public void invoke(PacketEvent.Post<CPacketClickWindow> event) {
        if (((PingSpoof)this.module).transactions.getValue().booleanValue() && !PINGBYPASS.isEnabled()) {
            ((PingSpoof)this.module).transactionIDs.add(((CPacketClickWindow)event.getPacket()).getActionNumber());
        }
    }
}

