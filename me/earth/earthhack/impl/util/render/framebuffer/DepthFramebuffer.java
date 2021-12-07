/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.texture.TextureUtil
 *  org.lwjgl.opengl.GL11
 */
package me.earth.earthhack.impl.util.render.framebuffer;

import java.nio.IntBuffer;
import me.earth.earthhack.impl.util.render.framebuffer.Framebuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

public class DepthFramebuffer
extends Framebuffer {
    private int framebufferTexture = -1;
    private int depthTexture = -1;

    public DepthFramebuffer(int width, int height) {
        super(width, height);
    }

    @Override
    protected void setupFramebuffer(int width, int height) {
        this.id = OpenGlHelper.glGenFramebuffers();
        this.framebufferTexture = TextureUtil.glGenTextures();
        this.depthTexture = TextureUtil.glGenTextures();
        GL11.glBindTexture((int)3553, (int)this.framebufferTexture);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10242, (int)10496);
        GL11.glTexParameteri((int)3553, (int)10243, (int)10496);
        GL11.glTexImage2D((int)3553, (int)0, (int)32856, (int)width, (int)height, (int)0, (int)6408, (int)5121, (IntBuffer)null);
        GL11.glBindTexture((int)3553, (int)0);
        GL11.glBindTexture((int)3553, (int)this.depthTexture);
        GL11.glTexImage2D((int)3553, (int)0, (int)6402, (int)width, (int)height, (int)0, (int)6402, (int)5121, (IntBuffer)null);
        GL11.glBindTexture((int)3553, (int)0);
        OpenGlHelper.glBindFramebuffer((int)OpenGlHelper.GL_FRAMEBUFFER, (int)this.id);
        OpenGlHelper.glFramebufferTexture2D((int)OpenGlHelper.GL_FRAMEBUFFER, (int)OpenGlHelper.GL_COLOR_ATTACHMENT0, (int)3553, (int)this.framebufferTexture, (int)0);
        OpenGlHelper.glFramebufferTexture2D((int)OpenGlHelper.GL_FRAMEBUFFER, (int)OpenGlHelper.GL_DEPTH_ATTACHMENT, (int)3553, (int)this.depthTexture, (int)0);
        OpenGlHelper.glBindFramebuffer((int)OpenGlHelper.GL_FRAMEBUFFER, (int)this.id);
        this.checkSetupFramebuffer();
    }

    public int getFramebufferTexture() {
        return this.framebufferTexture;
    }

    public int getDepthTexture() {
        return this.depthTexture;
    }
}

