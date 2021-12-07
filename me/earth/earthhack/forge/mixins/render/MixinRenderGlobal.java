/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.client.renderer.culling.ICamera
 *  net.minecraft.entity.Entity
 *  net.minecraftforge.client.MinecraftForgeClient
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.earth.earthhack.forge.mixins.render;

import me.earth.earthhack.api.event.bus.instance.Bus;
import me.earth.earthhack.impl.event.events.render.PostRenderEntitiesEvent;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.MinecraftForgeClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderGlobal.class})
public abstract class MixinRenderGlobal {
    @Inject(method={"renderEntities"}, at={@At(value="INVOKE", target="Lnet/minecraft/util/math/BlockPos$PooledMutableBlockPos;release()V", shift=At.Shift.BEFORE)})
    private void renderEntitiesHook(Entity renderViewEntity, ICamera camera, float partialTicks, CallbackInfo ci) {
        int pass = MinecraftForgeClient.getRenderPass();
        Bus.EVENT_BUS.post(new PostRenderEntitiesEvent(partialTicks, pass));
    }
}

