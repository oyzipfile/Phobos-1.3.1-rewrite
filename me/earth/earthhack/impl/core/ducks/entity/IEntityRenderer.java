/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.core.ducks.entity;

public interface IEntityRenderer {
    public void invokeSetupCameraTransform(float var1, int var2);

    public void invokeOrientCamera(float var1);

    public void invokeRenderHand(float var1, int var2);

    public void setLightmapUpdateNeeded(boolean var1);
}

