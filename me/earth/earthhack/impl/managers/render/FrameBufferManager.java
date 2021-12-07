/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.shader.Framebuffer
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL20
 */
package me.earth.earthhack.impl.managers.render;

import me.earth.earthhack.api.event.bus.EventListener;
import me.earth.earthhack.api.event.bus.SubscriberImpl;
import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.impl.core.ducks.entity.IEntityRenderer;
import me.earth.earthhack.impl.event.events.render.Render3DEvent;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class FrameBufferManager
extends SubscriberImpl
implements Globals {
    private Framebuffer blockBuffer;
    protected int lastScale;
    protected int lastScaleWidth;
    protected int lastScaleHeight;

    public FrameBufferManager() {
        this.listeners.add(new EventListener<Render3DEvent>(Render3DEvent.class, Integer.MIN_VALUE){

            @Override
            public void invoke(Render3DEvent event) {
                FrameBufferManager.this.checkSetupFBO();
            }
        });
        this.listeners.add(new EventListener<Render3DEvent>(Render3DEvent.class, Integer.MAX_VALUE){

            @Override
            public void invoke(Render3DEvent event) {
            }
        });
    }

    private void checkSetupFBO() {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (this.lastScale != factor || this.lastScaleWidth != factor2 || this.lastScaleHeight != factor3 || this.blockBuffer == null) {
            this.blockBuffer = new Framebuffer(FrameBufferManager.mc.displayWidth, FrameBufferManager.mc.displayHeight, true);
            this.blockBuffer.framebufferClear();
        } else {
            this.blockBuffer.framebufferClear();
        }
        this.lastScale = factor;
        this.lastScaleWidth = factor2;
        this.lastScaleHeight = factor3;
    }

    public void drawFramebuffer(Framebuffer framebuffer) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int currentProgram = GL11.glGetInteger((int)35725);
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
        GL20.glUseProgram((int)currentProgram);
    }

    public Framebuffer getBlockBuffer() {
        return this.blockBuffer;
    }

    public void renderToBlockBuffer(float partialTicks, Runnable runnable) {
        this.blockBuffer.bindFramebuffer(true);
        ((IEntityRenderer)FrameBufferManager.mc.entityRenderer).invokeSetupCameraTransform(partialTicks, 0);
        runnable.run();
        mc.getFramebuffer().bindFramebuffer(true);
    }
}

