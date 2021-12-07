/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.rechams;

import me.earth.earthhack.impl.event.events.render.CrystalRenderEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.rechams.Chams;

public class ListenerRenderCrystalPre
extends ModuleListener<Chams, CrystalRenderEvent.Pre> {
    public ListenerRenderCrystalPre(Chams module) {
        super(module, CrystalRenderEvent.Pre.class);
    }

    @Override
    public void invoke(CrystalRenderEvent.Pre event) {
        ((Chams)this.module).getModeFromEntity(event.getEntity()).renderCrystalPre(event, (Chams)this.module);
    }
}

