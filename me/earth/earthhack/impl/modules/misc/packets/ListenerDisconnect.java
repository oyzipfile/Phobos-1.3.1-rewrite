/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.events.network.DisconnectEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.misc.packets.Packets;
import me.earth.earthhack.impl.modules.misc.packets.util.BookCrashMode;

final class ListenerDisconnect
extends ModuleListener<Packets, DisconnectEvent> {
    public ListenerDisconnect(Packets module) {
        super(module, DisconnectEvent.class);
    }

    @Override
    public void invoke(DisconnectEvent event) {
        ((Packets)this.module).bookCrash.setValue(BookCrashMode.None);
        ((Packets)this.module).offhandCrashes.setValue(0);
    }
}

