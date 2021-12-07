/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.Render
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.earth.earthhack.impl.core.mixins.render;

import me.earth.earthhack.impl.modules.render.esp.ESP;
import net.minecraft.client.renderer.entity.Render;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Render.class})
public abstract class MixinRender {
    @Inject(method={"doRenderShadowAndFire"}, at={@At(value="HEAD")}, cancellable=true)
    private void doRenderShadowAndFireHook(CallbackInfo info) {
        if (ESP.isRendering) {
            info.cancel();
        }
    }

    @Inject(method={"renderLivingLabel"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderLivingLabelHook(CallbackInfo info) {
        if (ESP.isRendering) {
            info.cancel();
        }
    }
}

