/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.earth.earthhack.impl.core.mixins.render;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.api.event.bus.instance.Bus;
import me.earth.earthhack.impl.core.ducks.render.IRenderGlobal;
import me.earth.earthhack.impl.event.events.render.RenderEntityInWorldEvent;
import me.earth.earthhack.impl.event.events.render.RenderSkyEvent;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.render.ambience.Ambience;
import me.earth.earthhack.impl.modules.render.xray.XRay;
import me.earth.earthhack.impl.modules.render.xray.mode.XrayMode;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderGlobal.class})
public abstract class MixinRenderGlobal
implements IRenderGlobal {
    @Shadow
    private int countEntitiesRendered;
    private static final ModuleCache<XRay> XRAY = Caches.getModule(XRay.class);
    private static final ModuleCache<Ambience> AMBIENCE = Caches.getModule(Ambience.class);

    @ModifyVariable(method={"setupTerrain"}, at=@At(value="HEAD"))
    private boolean setupTerrainHook(boolean playerSpectator) {
        if (XRAY.isEnabled() && ((XRay)XRAY.get()).getMode() == XrayMode.Opacity) {
            return true;
        }
        return playerSpectator;
    }

    @Redirect(method={"renderEntities"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/entity/RenderManager;renderEntityStatic(Lnet/minecraft/entity/Entity;FZ)V", ordinal=1))
    private void renderEntityHook(RenderManager instance, Entity entityIn, float partialTicks, boolean p_188388_3_) {
        RenderEntityInWorldEvent.Pre pre = new RenderEntityInWorldEvent.Pre(entityIn, partialTicks);
        Bus.EVENT_BUS.post(pre);
        if (!pre.isCancelled()) {
            instance.renderEntityStatic(entityIn, partialTicks, p_188388_3_);
        } else {
            --this.countEntitiesRendered;
        }
        RenderEntityInWorldEvent.Post post = new RenderEntityInWorldEvent.Post(entityIn);
    }

    @Inject(method={"renderSky(FI)V"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal=3, shift=At.Shift.AFTER)})
    private void renderSkyHook(float f4, int f5, CallbackInfo ci) {
        Bus.EVENT_BUS.post(new RenderSkyEvent());
    }
}

