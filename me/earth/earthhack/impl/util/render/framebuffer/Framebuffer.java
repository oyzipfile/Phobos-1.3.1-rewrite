/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.OpenGlHelper
 *  org.lwjgl.opengl.GL30
 */
package me.earth.earthhack.impl.util.render.framebuffer;

import me.earth.earthhack.impl.util.render.GlObject;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL30;

public abstract class Framebuffer
extends GlObject {
    protected int width;
    protected int height;

    public Framebuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.setupFramebuffer(width, height);
    }

    protected abstract void setupFramebuffer(int var1, int var2);

    public boolean checkSetupFramebuffer() {
        OpenGlHelper.glBindFramebuffer((int)OpenGlHelper.GL_FRAMEBUFFER, (int)this.id);
        if (GL30.glCheckFramebufferStatus((int)36160) != 36053) {
            System.out.println("Error creating framebuffer: " + GL30.glCheckFramebufferStatus((int)36160));
            return false;
        }
        OpenGlHelper.glBindFramebuffer((int)OpenGlHelper.GL_FRAMEBUFFER, (int)0);
        return true;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}

