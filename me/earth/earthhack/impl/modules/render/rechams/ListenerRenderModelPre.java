/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package me.earth.earthhack.impl.modules.render.rechams;

import me.earth.earthhack.impl.event.events.render.ModelRenderEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.rechams.Chams;
import net.minecraft.entity.Entity;

public class ListenerRenderModelPre
extends ModuleListener<Chams, ModelRenderEvent.Pre> {
    public ListenerRenderModelPre(Chams module) {
        super(module, ModelRenderEvent.Pre.class);
    }

    @Override
    public void invoke(ModelRenderEvent.Pre event) {
        ((Chams)this.module).getModeFromEntity((Entity)event.getEntity()).renderPre(event, (Chams)this.module);
    }
}

