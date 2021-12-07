/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.itemchams;

import me.earth.earthhack.api.event.events.Stage;
import me.earth.earthhack.impl.event.events.render.RenderItemInFirstPersonEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.itemchams.ItemChams;

public class ListenerRenderItemPre
extends ModuleListener<ItemChams, RenderItemInFirstPersonEvent> {
    public ListenerRenderItemPre(ItemChams module) {
        super(module, RenderItemInFirstPersonEvent.class);
    }

    @Override
    public void invoke(RenderItemInFirstPersonEvent event) {
        if (event.getStage() == Stage.PRE && !((ItemChams)this.module).forceRender && ((ItemChams)this.module).chams.getValue().booleanValue()) {
            event.setCancelled(true);
        }
    }

    private void render(RenderItemInFirstPersonEvent event) {
        mc.getItemRenderer().renderItemSide(event.getEntity(), event.getStack(), event.getTransformType(), event.isLeftHanded());
    }
}

