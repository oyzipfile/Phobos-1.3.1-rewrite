/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.texture.TextureUtil
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL30
 */
package me.earth.earthhack.impl.util.render.framebuffer;

import java.nio.IntBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class TextureFramebuffer {
    private int framebuffer = -1;
    private int framebufferTexture = -1;
    private int depthStencilTexture = -1;
    private int depthTexture = -1;
    private int stencilTexture = -1;
    private int stencilView = -1;
    private float[] framebufferColor = new float[4];
    private int width;
    private int height;

    public TextureFramebuffer(int width, int height) {
        this.createFrameBuffer(width, height);
        this.clear();
    }

    public void createFrameBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.framebufferColor[0] = 0.0f;
        this.framebufferColor[1] = 0.0f;
        this.framebufferColor[2] = 0.0f;
        this.framebufferColor[3] = 0.0f;
        this.framebuffer = OpenGlHelper.glGenFramebuffers();
        this.framebufferTexture = TextureUtil.glGenTextures();
        this.depthStencilTexture = TextureUtil.glGenTextures();
        this.depthTexture = TextureUtil.glGenTextures();
        this.stencilTexture = TextureUtil.glGenTextures();
        GL11.glBindTexture((int)3553, (int)this.depthStencilTexture);
        GL11.glTexImage2D((int)3553, (int)0, (int)35056, (int)width, (int)height, (int)0, (int)34041, (int)34042, (IntBuffer)null);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glBindTexture((int)3553, (int)0);
        GL11.glBindTexture((int)3553, (int)this.depthTexture);
        GL11.glTexImage2D((int)3553, (int)0, (int)6402, (int)width, (int)height, (int)0, (int)6402, (int)5121, (IntBuffer)null);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glBindTexture((int)3553, (int)0);
        GL11.glBindTexture((int)3553, (int)this.stencilTexture);
        GL11.glTexImage2D((int)3553, (int)0, (int)36168, (int)width, (int)height, (int)0, (int)6401, (int)5121, (IntBuffer)null);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glBindTexture((int)3553, (int)0);
        GlStateManager.bindTexture((int)this.framebufferTexture);
        GlStateManager.glTexParameteri((int)3553, (int)10241, (int)9728);
        GlStateManager.glTexParameteri((int)3553, (int)10240, (int)9728);
        GlStateManager.glTexParameteri((int)3553, (int)10242, (int)10496);
        GlStateManager.glTexParameteri((int)3553, (int)10243, (int)10496);
        GL11.glTexImage2D((int)3553, (int)0, (int)32856, (int)width, (int)height, (int)0, (int)6408, (int)5121, (IntBuffer)null);
        OpenGlHelper.glBindFramebuffer((int)OpenGlHelper.GL_FRAMEBUFFER, (int)this.framebuffer);
        OpenGlHelper.glFramebufferTexture2D((int)OpenGlHelper.GL_FRAMEBUFFER, (int)OpenGlHelper.GL_COLOR_ATTACHMENT0, (int)3553, (int)this.framebufferTexture, (int)0);
        OpenGlHelper.glFramebufferTexture2D((int)OpenGlHelper.GL_FRAMEBUFFER, (int)OpenGlHelper.GL_DEPTH_ATTACHMENT, (int)3553, (int)this.depthTexture, (int)0);
        OpenGlHelper.glFramebufferTexture2D((int)OpenGlHelper.GL_FRAMEBUFFER, (int)36128, (int)3553, (int)this.stencilTexture, (int)0);
        if (GL30.glCheckFramebufferStatus((int)36160) != 36053) {
            System.out.println("Error creating framebuffer: " + GL30.glCheckFramebufferStatus((int)36160));
        }
        OpenGlHelper.glBindFramebuffer((int)OpenGlHelper.GL_FRAMEBUFFER, (int)0);
        GlStateManager.bindTexture((int)0);
    }

    public void clear() {
        this.bind();
        GlStateManager.clearColor((float)this.framebufferColor[0], (float)this.framebufferColor[1], (float)this.framebufferColor[2], (float)this.framebufferColor[3]);
        this.clearDepth(1.0);
        this.clearStencil(0);
        OpenGlHelper.glBindFramebuffer((int)OpenGlHelper.GL_FRAMEBUFFER, (int)0);
    }

    public void clearDepth(double depth) {
        this.bind();
        GlStateManager.clearDepth((double)depth);
        OpenGlHelper.glBindFramebuffer((int)OpenGlHelper.GL_FRAMEBUFFER, (int)0);
    }

    public void clearStencil(int value) {
        this.bind();
        GL11.glClearStencil((int)value);
        OpenGlHelper.glBindFramebuffer((int)OpenGlHelper.GL_FRAMEBUFFER, (int)0);
    }

    public void bind() {
        OpenGlHelper.glBindFramebuffer((int)OpenGlHelper.GL_FRAMEBUFFER, (int)this.framebuffer);
    }

    public void deleteFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture((int)0);
            OpenGlHelper.glBindFramebuffer((int)OpenGlHelper.GL_FRAMEBUFFER, (int)0);
            if (this.depthStencilTexture > -1) {
                TextureUtil.deleteTexture((int)this.framebufferTexture);
                this.framebufferTexture = -1;
            }
            if (this.depthTexture > -1) {
                TextureUtil.deleteTexture((int)this.depthTexture);
                this.depthTexture = -1;
            }
            if (this.stencilTexture > -1) {
                TextureUtil.deleteTexture((int)this.stencilTexture);
                this.stencilTexture = -1;
            }
            if (this.framebufferTexture > -1) {
                TextureUtil.deleteTexture((int)this.framebufferTexture);
                this.framebufferTexture = -1;
            }
            if (this.framebuffer > -1) {
                OpenGlHelper.glBindFramebuffer((int)OpenGlHelper.GL_FRAMEBUFFER, (int)0);
                OpenGlHelper.glDeleteFramebuffers((int)this.framebuffer);
                this.framebuffer = -1;
            }
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getTexture() {
        return this.framebufferTexture;
    }

    public int getFBO() {
        return this.framebuffer;
    }
}

