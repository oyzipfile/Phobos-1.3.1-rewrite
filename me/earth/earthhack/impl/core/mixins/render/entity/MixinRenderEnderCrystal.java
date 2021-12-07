/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.entity.RenderEnderCrystal
 *  net.minecraft.entity.Entity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package me.earth.earthhack.impl.core.mixins.render.entity;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.api.event.bus.instance.Bus;
import me.earth.earthhack.impl.event.events.render.CrystalRenderEvent;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.render.crystalchams.CrystalChams;
import me.earth.earthhack.impl.modules.render.crystalscale.CrystalScale;
import me.earth.earthhack.impl.util.animation.TimeAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={RenderEnderCrystal.class})
public abstract class MixinRenderEnderCrystal {
    private static final ModuleCache<CrystalScale> SCALE = Caches.getModule(CrystalScale.class);
    private static final ModuleCache<CrystalChams> CHAMS = Caches.getModule(CrystalChams.class);

    @Redirect(method={"doRender"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void renderHook(ModelBase modelBase, Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!SCALE.isPresent()) {
            return;
        }
        float crystalScale = ((CrystalScale)MixinRenderEnderCrystal.SCALE.get()).animate.getValue() != false ? (float)(((CrystalScale)MixinRenderEnderCrystal.SCALE.get()).scaleMap.containsKey(entityIn.getEntityId()) ? ((CrystalScale)MixinRenderEnderCrystal.SCALE.get()).scaleMap.get(entityIn.getEntityId()).getCurrent() : (double)0.1f) : ((CrystalScale)MixinRenderEnderCrystal.SCALE.get()).scale.getValue().floatValue();
        TimeAnimation animation = ((CrystalScale)MixinRenderEnderCrystal.SCALE.get()).scaleMap.get(entityIn.getEntityId());
        if (animation != null) {
            animation.add(Minecraft.getMinecraft().getRenderPartialTicks());
        }
        if (SCALE.isEnabled()) {
            GlStateManager.scale((float)crystalScale, (float)crystalScale, (float)crystalScale);
        }
        RenderEnderCrystal renderLiving = (RenderEnderCrystal)RenderEnderCrystal.class.cast(this);
        CrystalRenderEvent.Pre pre = new CrystalRenderEvent.Pre(renderLiving, entityIn, modelBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Bus.EVENT_BUS.post(pre);
        if (!pre.isCancelled()) {
            modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
        CrystalRenderEvent.Post post = new CrystalRenderEvent.Post(renderLiving, entityIn, modelBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Bus.EVENT_BUS.post(post);
        if (SCALE.isEnabled()) {
            GlStateManager.scale((float)(1.0f / crystalScale), (float)(1.0f / crystalScale), (float)(1.0f / crystalScale));
        }
    }
}

