/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.misc.mcf;

import me.earth.earthhack.api.module.data.DefaultData;
import me.earth.earthhack.impl.modules.misc.mcf.MCF;

final class MCFData
extends DefaultData<MCF> {
    public MCFData(MCF mcf) {
        super(mcf);
    }

    @Override
    public int getColor() {
        return -7077976;
    }

    @Override
    public String getDescription() {
        return "Middleclick on players to friend/unfriend them.";
    }
}

