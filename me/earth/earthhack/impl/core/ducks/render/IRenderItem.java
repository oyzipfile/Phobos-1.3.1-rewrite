/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.item.ItemStack
 */
package me.earth.earthhack.impl.core.ducks.render;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;

public interface IRenderItem {
    public void setNotRenderingEffectsInGUI(boolean var1);

    public void invokeRenderModel(IBakedModel var1, ItemStack var2);
}

