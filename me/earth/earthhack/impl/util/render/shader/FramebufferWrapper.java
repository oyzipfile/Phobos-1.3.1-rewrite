/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.shader.Framebuffer
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 */
package me.earth.earthhack.impl.util.render.shader;

import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.impl.util.thread.SafeRunnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class FramebufferWrapper
implements Globals {
    private Framebuffer framebuffer;
    protected static int lastScale;
    protected static int lastScaleWidth;
    protected static int lastScaleHeight;
    private boolean hasUpdated;

    public FramebufferWrapper() {
        this.updateFramebuffer();
    }

    public void updateFramebuffer() {
        this.hasUpdated = false;
        if (Display.isActive() || Display.isVisible()) {
            if (this.framebuffer != null) {
                this.framebuffer.framebufferClear();
                ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
                int factor = scale.getScaleFactor();
                int factor2 = scale.getScaledWidth();
                int factor3 = scale.getScaledHeight();
                if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3) {
                    this.framebuffer.deleteFramebuffer();
                    this.framebuffer = new Framebuffer(FramebufferWrapper.mc.displayWidth, FramebufferWrapper.mc.displayHeight, true);
                    this.hasUpdated = true;
                }
                lastScale = factor;
                lastScaleWidth = factor2;
                lastScaleHeight = factor3;
            } else {
                this.framebuffer = new Framebuffer(FramebufferWrapper.mc.displayWidth, FramebufferWrapper.mc.displayHeight, true);
                this.hasUpdated = true;
            }
        } else if (this.framebuffer == null) {
            this.framebuffer = new Framebuffer(FramebufferWrapper.mc.displayWidth, FramebufferWrapper.mc.displayHeight, true);
            this.hasUpdated = true;
        }
    }

    public void renderToFramebuffer(SafeRunnable renderOp) {
        GlStateManager.pushAttrib();
        this.framebuffer.bindFramebuffer(true);
        renderOp.run();
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.popAttrib();
    }

    public void renderFramebuffer(SafeRunnable ... renderOp) {
        GlStateManager.pushAttrib();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GL11.glBlendFunc((int)770, (int)771);
        mc.getFramebuffer().bindFramebuffer(true);
        for (SafeRunnable runnable : renderOp) {
            runnable.run();
        }
        this.drawFramebuffer(this.framebuffer);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.popAttrib();
    }

    public void drawFramebuffer(Framebuffer framebuffer) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        GL11.glBindTexture((int)3553, (int)framebuffer.framebufferTexture);
        GL11.glBegin((int)7);
        GL11.glTexCoord2d((double)0.0, (double)1.0);
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glTexCoord2d((double)0.0, (double)0.0);
        GL11.glVertex2d((double)0.0, (double)scaledResolution.getScaledHeight());
        GL11.glTexCoord2d((double)1.0, (double)0.0);
        GL11.glVertex2d((double)scaledResolution.getScaledWidth(), (double)scaledResolution.getScaledHeight());
        GL11.glTexCoord2d((double)1.0, (double)1.0);
        GL11.glVertex2d((double)scaledResolution.getScaledWidth(), (double)0.0);
        GL11.glEnd();
    }

    public Framebuffer getFramebuffer() {
        return this.framebuffer;
    }

    public boolean hasUpdated() {
        return this.hasUpdated;
    }
}

