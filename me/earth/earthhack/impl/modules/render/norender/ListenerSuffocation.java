/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.norender;

import me.earth.earthhack.impl.event.events.render.SuffocationEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.norender.NoRender;

final class ListenerSuffocation
extends ModuleListener<NoRender, SuffocationEvent> {
    public ListenerSuffocation(NoRender module) {
        super(module, SuffocationEvent.class);
    }

    @Override
    public void invoke(SuffocationEvent event) {
        if (((NoRender)this.module).blocks.getValue().booleanValue()) {
            event.setCancelled(true);
        }
    }
}

