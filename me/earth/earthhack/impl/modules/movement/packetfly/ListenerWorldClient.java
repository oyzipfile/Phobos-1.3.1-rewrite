/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.movement.packetfly;

import me.earth.earthhack.impl.event.events.network.WorldClientEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.movement.packetfly.PacketFly;

final class ListenerWorldClient
extends ModuleListener<PacketFly, WorldClientEvent.Load> {
    public ListenerWorldClient(PacketFly module) {
        super(module, WorldClientEvent.Load.class);
    }

    @Override
    public void invoke(WorldClientEvent.Load event) {
        ((PacketFly)this.module).clearValues();
    }
}

