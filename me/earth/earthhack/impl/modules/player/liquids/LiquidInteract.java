/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.player.liquids;

import me.earth.earthhack.api.module.Module;
import me.earth.earthhack.api.module.util.Category;
import me.earth.earthhack.impl.util.client.SimpleData;

public class LiquidInteract
extends Module {
    public LiquidInteract() {
        super("LiquidInteract", Category.Player);
        this.setData(new SimpleData(this, "Allows you to place on liquids"));
    }
}

