/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.RenderGuardian
 *  net.minecraft.entity.monster.EntityGuardian
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.earth.earthhack.impl.core.mixins.render.entity;

import me.earth.earthhack.impl.modules.render.esp.ESP;
import net.minecraft.client.renderer.entity.RenderGuardian;
import net.minecraft.entity.monster.EntityGuardian;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderGuardian.class})
public abstract class MixinRenderGuardian {
    @Inject(method={"doRender"}, at={@At(value="INVOKE", target="net/minecraft/entity/monster/EntityGuardian.getAttackAnimationScale(F)F")}, cancellable=true)
    private void doRenderHook(EntityGuardian entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if (ESP.isRendering) {
            info.cancel();
        }
    }
}

