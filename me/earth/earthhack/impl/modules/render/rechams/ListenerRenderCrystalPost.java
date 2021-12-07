/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.rechams;

import me.earth.earthhack.impl.event.events.render.CrystalRenderEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.rechams.Chams;

public class ListenerRenderCrystalPost
extends ModuleListener<Chams, CrystalRenderEvent.Post> {
    public ListenerRenderCrystalPost(Chams module) {
        super(module, CrystalRenderEvent.Post.class);
    }

    @Override
    public void invoke(CrystalRenderEvent.Post event) {
        ((Chams)this.module).getModeFromEntity(event.getEntity()).renderCrystalPost(event, (Chams)this.module);
    }
}

