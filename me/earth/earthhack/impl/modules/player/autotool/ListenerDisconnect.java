/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.player.autotool;

import me.earth.earthhack.impl.event.events.network.DisconnectEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.player.autotool.AutoTool;

final class ListenerDisconnect
extends ModuleListener<AutoTool, DisconnectEvent> {
    public ListenerDisconnect(AutoTool module) {
        super(module, DisconnectEvent.class);
    }

    @Override
    public void invoke(DisconnectEvent event) {
        mc.addScheduledTask(((AutoTool)this.module)::reset);
    }
}

