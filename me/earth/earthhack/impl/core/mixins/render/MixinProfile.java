/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager$Profile
 *  org.spongepowered.asm.mixin.Mixin
 */
package me.earth.earthhack.impl.core.mixins.render;

import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={GlStateManager.Profile.class})
public abstract class MixinProfile {
}

