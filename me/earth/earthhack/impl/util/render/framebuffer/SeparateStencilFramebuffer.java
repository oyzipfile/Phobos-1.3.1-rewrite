/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.util.render.framebuffer;

import me.earth.earthhack.impl.util.render.framebuffer.Framebuffer;

public class SeparateStencilFramebuffer
extends Framebuffer {
    private int framebufferTexture = -1;
    private int depthTexture = -1;
    private int stencilTexture = -1;

    public SeparateStencilFramebuffer(int width, int height) {
        super(width, height);
    }

    @Override
    protected void setupFramebuffer(int width, int height) {
    }
}

