/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.shader.Framebuffer
 *  net.minecraft.client.shader.Shader
 *  net.minecraft.client.shader.ShaderGroup
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package me.earth.earthhack.impl.core.mixins.render;

import java.util.List;
import me.earth.earthhack.impl.core.ducks.render.IShaderGroup;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ShaderGroup.class})
public abstract class MixinShaderGroup
implements IShaderGroup {
    @Override
    @Accessor(value="listFramebuffers")
    public abstract List<Framebuffer> getListFramebuffers();

    @Override
    @Accessor(value="listShaders")
    public abstract List<Shader> getListShaders();
}

