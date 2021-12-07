/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiBossOverlay
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.earth.earthhack.impl.core.mixins.gui;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.render.norender.NoRender;
import net.minecraft.client.gui.GuiBossOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiBossOverlay.class})
public abstract class MixinGuiBossOverlay {
    private static final ModuleCache<NoRender> NO_RENDER = Caches.getModule(NoRender.class);

    @Inject(method={"renderBossHealth"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderHook(CallbackInfo ci) {
        if (((NoRender)MixinGuiBossOverlay.NO_RENDER.get()).boss.getValue().booleanValue()) {
            ci.cancel();
        }
    }
}

