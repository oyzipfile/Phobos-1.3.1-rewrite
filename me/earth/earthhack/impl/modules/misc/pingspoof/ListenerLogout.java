/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.misc.pingspoof;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.impl.event.events.network.DisconnectEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.client.pingbypass.PingBypass;
import me.earth.earthhack.impl.modules.misc.pingspoof.PingSpoof;

final class ListenerLogout
extends ModuleListener<PingSpoof, DisconnectEvent> {
    private static final ModuleCache<PingBypass> PINGBYPASS = Caches.getModule(PingBypass.class);

    public ListenerLogout(PingSpoof module) {
        super(module, DisconnectEvent.class);
    }

    @Override
    public void invoke(DisconnectEvent event) {
        if (!PINGBYPASS.isEnabled()) {
            ((PingSpoof)this.module).clearPackets(false);
        }
    }
}

