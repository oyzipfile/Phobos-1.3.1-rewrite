/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.player.speedmine;

import me.earth.earthhack.impl.event.events.misc.ResetBlockEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.player.speedmine.Speedmine;
import me.earth.earthhack.impl.modules.player.speedmine.mode.MineMode;

final class ListenerReset
extends ModuleListener<Speedmine, ResetBlockEvent> {
    public ListenerReset(Speedmine module) {
        super(module, ResetBlockEvent.class);
    }

    @Override
    public void invoke(ResetBlockEvent event) {
        if (((Speedmine)this.module).noReset.getValue().booleanValue() || ((Speedmine)this.module).mode.getValue() == MineMode.Reset) {
            event.setCancelled(true);
        }
    }
}

