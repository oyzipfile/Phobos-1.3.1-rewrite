/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemStack
 */
package me.earth.earthhack.impl.event.events.render;

import me.earth.earthhack.api.event.events.Stage;
import me.earth.earthhack.api.event.events.StageEvent;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class RenderItemInFirstPersonEvent
extends StageEvent {
    private final EntityLivingBase entity;
    private ItemStack stack;
    private ItemCameraTransforms.TransformType transformType;
    private final boolean leftHanded;

    public RenderItemInFirstPersonEvent(EntityLivingBase entitylivingbaseIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform, boolean leftHanded, Stage stage) {
        super(stage);
        this.entity = entitylivingbaseIn;
        this.stack = heldStack;
        this.transformType = transform;
        this.leftHanded = leftHanded;
    }

    public ItemCameraTransforms.TransformType getTransformType() {
        return this.transformType;
    }

    public void setTransformType(ItemCameraTransforms.TransformType transformType) {
        this.transformType = transformType;
    }

    public boolean isLeftHanded() {
        return this.leftHanded;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public EntityLivingBase getEntity() {
        return this.entity;
    }
}

