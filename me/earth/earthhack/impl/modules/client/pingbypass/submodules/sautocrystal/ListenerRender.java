/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 */
package me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautocrystal;

import java.awt.Color;
import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.impl.event.events.render.Render3DEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.client.pingbypass.PingBypass;
import me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautocrystal.ServerAutoCrystal;
import me.earth.earthhack.impl.util.render.Interpolation;
import me.earth.earthhack.impl.util.render.RenderUtil;
import net.minecraft.util.math.BlockPos;

final class ListenerRender
extends ModuleListener<ServerAutoCrystal, Render3DEvent> {
    private static final ModuleCache<PingBypass> PINGBYPASS = Caches.getModule(PingBypass.class);

    public ListenerRender(ServerAutoCrystal module) {
        super(module, Render3DEvent.class);
    }

    @Override
    public void invoke(Render3DEvent event) {
        BlockPos pos = ((ServerAutoCrystal)this.module).renderPos;
        if (pos != null && PINGBYPASS.isEnabled()) {
            RenderUtil.renderBox(Interpolation.interpolatePos(pos, 1.0f), new Color(255, 255, 255, 120), Color.WHITE, 1.5f);
        }
    }
}

