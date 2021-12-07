/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.managers.render;

import me.earth.earthhack.api.event.bus.EventListener;
import me.earth.earthhack.api.event.bus.SubscriberImpl;
import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.impl.core.ducks.entity.IEntityRenderer;
import me.earth.earthhack.impl.event.events.render.RenderItemInFirstPersonEvent;
import me.earth.earthhack.impl.event.events.render.WorldRenderEvent;

public class HandRenderManager
extends SubscriberImpl
implements Globals {
    public boolean renderHandsLate = false;
    public boolean forceRender = false;

    public HandRenderManager() {
        this.listeners.add(new EventListener<WorldRenderEvent>(WorldRenderEvent.class, Integer.MAX_VALUE){

            @Override
            public void invoke(WorldRenderEvent event) {
                if (HandRenderManager.this.renderHandsLate) {
                    HandRenderManager.this.forceRender = true;
                    ((IEntityRenderer)Globals.mc.entityRenderer).invokeRenderHand(Globals.mc.getRenderPartialTicks(), 2);
                    HandRenderManager.this.forceRender = false;
                }
            }
        });
        this.listeners.add(new EventListener<RenderItemInFirstPersonEvent>(RenderItemInFirstPersonEvent.class, Integer.MIN_VALUE){

            @Override
            public void invoke(RenderItemInFirstPersonEvent event) {
                if (HandRenderManager.this.renderHandsLate && !HandRenderManager.this.forceRender) {
                    event.setCancelled(true);
                }
            }
        });
    }
}

