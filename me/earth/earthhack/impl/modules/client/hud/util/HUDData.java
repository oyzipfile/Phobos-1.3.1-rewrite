/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.client.hud.util;

import me.earth.earthhack.api.module.data.DefaultData;
import me.earth.earthhack.impl.modules.client.hud.HUD;

public class HUDData
extends DefaultData<HUD> {
    public HUDData(HUD hud) {
        super(hud);
    }

    @Override
    public int getColor() {
        return -13327873;
    }

    @Override
    public String getDescription() {
        return "Displays info like ping, tps or toggled modules on your screen.";
    }
}

