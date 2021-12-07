/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.rechams;

import me.earth.earthhack.impl.event.events.render.RenderCrystalCubeEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.rechams.Chams;

public class ListenerRenderCrystalCube
extends ModuleListener<Chams, RenderCrystalCubeEvent> {
    public ListenerRenderCrystalCube(Chams module) {
        super(module, RenderCrystalCubeEvent.class);
    }

    @Override
    public void invoke(RenderCrystalCubeEvent event) {
        ((Chams)this.module).crystalMode.getValue().renderCrystalCube(event, (Chams)this.module);
    }
}

