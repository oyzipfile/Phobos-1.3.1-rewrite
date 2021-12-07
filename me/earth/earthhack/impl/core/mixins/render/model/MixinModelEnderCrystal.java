/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelEnderCrystal
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.entity.Entity
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.earth.earthhack.impl.core.mixins.render.model;

import me.earth.earthhack.api.event.bus.instance.Bus;
import me.earth.earthhack.api.event.events.Stage;
import me.earth.earthhack.impl.event.events.render.RenderCrystalCubeEvent;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ModelEnderCrystal.class})
public abstract class MixinModelEnderCrystal {
    @Shadow
    private ModelRenderer base;
    @Shadow
    @Final
    private ModelRenderer glass;
    @Shadow
    @Final
    private ModelRenderer cube;

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderHook(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci) {
    }

    @Redirect(method={"Lnet/minecraft/client/model/ModelEnderCrystal;render(Lnet/minecraft/entity/Entity;FFFFFF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelRenderer;render(F)V", ordinal=0))
    private void renderBaseHook(ModelRenderer model, float scale) {
        RenderCrystalCubeEvent preBaseEvent = new RenderCrystalCubeEvent(scale, model, RenderCrystalCubeEvent.Model.BASE, Stage.PRE);
        Bus.EVENT_BUS.post(preBaseEvent);
        if (!preBaseEvent.isCancelled()) {
            model.render(preBaseEvent.getScale());
        }
        RenderCrystalCubeEvent postBaseEvent = new RenderCrystalCubeEvent(scale, model, RenderCrystalCubeEvent.Model.BASE, Stage.POST);
        Bus.EVENT_BUS.post(postBaseEvent);
    }

    @Redirect(method={"Lnet/minecraft/client/model/ModelEnderCrystal;render(Lnet/minecraft/entity/Entity;FFFFFF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelRenderer;render(F)V", ordinal=1))
    private void renderGlassHook(ModelRenderer model, float scale) {
        RenderCrystalCubeEvent preBaseEvent = new RenderCrystalCubeEvent(scale, model, RenderCrystalCubeEvent.Model.GLASS_1, Stage.PRE);
        Bus.EVENT_BUS.post(preBaseEvent);
        if (!preBaseEvent.isCancelled()) {
            model.render(preBaseEvent.getScale());
        }
        RenderCrystalCubeEvent postBaseEvent = new RenderCrystalCubeEvent(scale, model, RenderCrystalCubeEvent.Model.GLASS_1, Stage.POST);
        Bus.EVENT_BUS.post(postBaseEvent);
    }

    @Redirect(method={"Lnet/minecraft/client/model/ModelEnderCrystal;render(Lnet/minecraft/entity/Entity;FFFFFF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelRenderer;render(F)V", ordinal=2))
    private void renderGlassHook2(ModelRenderer model, float scale) {
        RenderCrystalCubeEvent preBaseEvent = new RenderCrystalCubeEvent(scale, model, RenderCrystalCubeEvent.Model.GLASS_2, Stage.PRE);
        Bus.EVENT_BUS.post(preBaseEvent);
        if (!preBaseEvent.isCancelled()) {
            model.render(preBaseEvent.getScale());
        }
        RenderCrystalCubeEvent postBaseEvent = new RenderCrystalCubeEvent(scale, model, RenderCrystalCubeEvent.Model.GLASS_2, Stage.POST);
        Bus.EVENT_BUS.post(postBaseEvent);
    }

    @Redirect(method={"Lnet/minecraft/client/model/ModelEnderCrystal;render(Lnet/minecraft/entity/Entity;FFFFFF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelRenderer;render(F)V", ordinal=3))
    private void renderCubeHook(ModelRenderer model, float scale) {
        RenderCrystalCubeEvent preBaseEvent = new RenderCrystalCubeEvent(scale, model, RenderCrystalCubeEvent.Model.CUBE, Stage.PRE);
        Bus.EVENT_BUS.post(preBaseEvent);
        if (!preBaseEvent.isCancelled()) {
            model.render(preBaseEvent.getScale());
        }
        RenderCrystalCubeEvent postBaseEvent = new RenderCrystalCubeEvent(scale, model, RenderCrystalCubeEvent.Model.CUBE, Stage.POST);
        Bus.EVENT_BUS.post(postBaseEvent);
    }
}

