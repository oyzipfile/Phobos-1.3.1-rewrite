/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.rechams;

import me.earth.earthhack.impl.event.events.render.BeginRenderEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.rechams.Chams;
import me.earth.earthhack.impl.modules.render.rechams.mode.ChamsMode;

public class ListenerBeginRender
extends ModuleListener<Chams, BeginRenderEvent> {
    public ListenerBeginRender(Chams module) {
        super(module, BeginRenderEvent.class);
    }

    @Override
    public void invoke(BeginRenderEvent event) {
        for (ChamsMode mode : ((Chams)this.module).getAllCurrentModes()) {
            mode.beginRender(event, (Chams)this.module);
        }
    }
}

