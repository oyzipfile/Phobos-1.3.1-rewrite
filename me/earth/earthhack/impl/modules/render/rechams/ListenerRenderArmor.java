/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.rechams;

import me.earth.earthhack.impl.event.events.render.RenderArmorEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.rechams.Chams;

public class ListenerRenderArmor
extends ModuleListener<Chams, RenderArmorEvent> {
    public ListenerRenderArmor(Chams module) {
        super(module, RenderArmorEvent.class);
    }

    @Override
    public void invoke(RenderArmorEvent event) {
        ((Chams)this.module).getModeFromEntity(event.getEntity()).renderArmor(event, (Chams)this.module);
    }
}

