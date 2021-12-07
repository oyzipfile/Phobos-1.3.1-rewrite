/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.rechams;

import me.earth.earthhack.impl.event.events.render.PreRenderHandEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.rechams.Chams;
import me.earth.earthhack.impl.modules.render.rechams.mode.ChamsMode;

public class ListenerPreRenderHud
extends ModuleListener<Chams, PreRenderHandEvent> {
    public ListenerPreRenderHud(Chams module) {
        super(module, PreRenderHandEvent.class);
    }

    @Override
    public void invoke(PreRenderHandEvent event) {
        for (ChamsMode mode : ((Chams)this.module).getAllCurrentModes()) {
            mode.renderHud(event, (Chams)this.module);
        }
    }
}

