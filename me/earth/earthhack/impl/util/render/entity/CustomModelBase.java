/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 */
package me.earth.earthhack.impl.util.render.entity;

import java.util.ArrayList;
import java.util.List;
import me.earth.earthhack.impl.util.render.entity.CustomModelRenderer;
import net.minecraft.client.model.ModelBase;

public abstract class CustomModelBase
extends ModelBase {
    private List<CustomModelRenderer> customRenderers = new ArrayList<CustomModelRenderer>();

    public CustomModelBase(List<CustomModelRenderer> customRenderers) {
        this.customRenderers = customRenderers;
    }
}

