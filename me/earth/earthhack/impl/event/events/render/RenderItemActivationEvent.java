/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType
 *  net.minecraft.item.ItemStack
 */
package me.earth.earthhack.impl.event.events.render;

import me.earth.earthhack.api.event.events.Event;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;

public class RenderItemActivationEvent
extends Event {
    private RenderItem renderItem;
    private final ItemStack stack;
    private final ItemCameraTransforms.TransformType type;

    public RenderItemActivationEvent(RenderItem renderItem, ItemStack stack, ItemCameraTransforms.TransformType type) {
        this.renderItem = renderItem;
        this.stack = stack;
        this.type = type;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public ItemCameraTransforms.TransformType getType() {
        return this.type;
    }

    public RenderItem getRenderItem() {
        return this.renderItem;
    }
}

