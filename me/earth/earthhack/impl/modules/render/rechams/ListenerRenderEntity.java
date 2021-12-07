/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.rechams;

import me.earth.earthhack.impl.event.events.render.RenderEntityEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.rechams.Chams;

public class ListenerRenderEntity
extends ModuleListener<Chams, RenderEntityEvent.Pre> {
    public ListenerRenderEntity(Chams module) {
        super(module, RenderEntityEvent.Pre.class);
    }

    @Override
    public void invoke(RenderEntityEvent.Pre event) {
        ((Chams)this.module).getModeFromEntity(event.getEntity()).renderEntity(event, (Chams)this.module);
    }
}

