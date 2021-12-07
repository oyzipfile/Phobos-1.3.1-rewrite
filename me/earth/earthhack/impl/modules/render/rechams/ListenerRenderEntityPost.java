/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.rechams;

import me.earth.earthhack.impl.event.events.render.RenderEntityEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.rechams.Chams;

public class ListenerRenderEntityPost
extends ModuleListener<Chams, RenderEntityEvent.Post> {
    public ListenerRenderEntityPost(Chams module) {
        super(module, RenderEntityEvent.Post.class);
    }

    @Override
    public void invoke(RenderEntityEvent.Post event) {
        ((Chams)this.module).getModeFromEntity(event.getEntity()).renderEntityPost(event, (Chams)this.module);
    }
}

