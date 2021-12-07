/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.ITextureObject
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.util.ResourceLocation
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package me.earth.earthhack.impl.core.mixins.render;

import java.util.Map;
import me.earth.earthhack.impl.core.ducks.render.ITextureManager;
import me.earth.earthhack.impl.util.render.image.EfficientTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={TextureManager.class})
public abstract class MixinTextureManager
implements ITextureManager {
    @Shadow
    @Final
    private Map<String, Integer> mapTextureCounters;

    @Shadow
    public abstract boolean loadTexture(ResourceLocation var1, ITextureObject var2);

    @Override
    public ResourceLocation getEfficientTextureResourceLocation(String name, EfficientTexture texture) {
        Integer integer = this.mapTextureCounters.get(name);
        integer = integer == null ? Integer.valueOf(1) : Integer.valueOf(integer + 1);
        this.mapTextureCounters.put(name, integer);
        ResourceLocation resourcelocation = new ResourceLocation(String.format("dynamic/%s_%d", name, integer));
        this.loadTexture(resourcelocation, (ITextureObject)texture);
        return resourcelocation;
    }
}

