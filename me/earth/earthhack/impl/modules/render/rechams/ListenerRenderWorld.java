/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.rechams;

import me.earth.earthhack.impl.event.events.render.WorldRenderEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.rechams.Chams;
import me.earth.earthhack.impl.modules.render.rechams.mode.ChamsMode;

public class ListenerRenderWorld
extends ModuleListener<Chams, WorldRenderEvent> {
    public ListenerRenderWorld(Chams module) {
        super(module, WorldRenderEvent.class);
    }

    @Override
    public void invoke(WorldRenderEvent event) {
        for (ChamsMode mode : ((Chams)this.module).getAllCurrentModes()) {
            mode.renderWorld(event, (Chams)this.module);
        }
    }
}

