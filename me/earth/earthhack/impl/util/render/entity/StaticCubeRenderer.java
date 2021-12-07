/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.vertex.VertexBuffer
 *  net.minecraft.client.renderer.vertex.VertexFormat
 *  org.lwjgl.opengl.GL11
 */
package me.earth.earthhack.impl.util.render.entity;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;

public class StaticCubeRenderer {
    private VertexBuffer buffer;
    private int mode;
    private float x1;
    private float y1;
    private float z1;
    private float x2;
    private float y2;
    private float z2;

    public StaticCubeRenderer(ByteBuffer vertices, VertexFormat format, int mode) {
        this.x1 = this.x1;
        this.y1 = this.y1;
        this.z1 = this.z1;
        this.x2 = this.x2;
        this.y2 = this.y2;
        this.z2 = this.z2;
        if (OpenGlHelper.useVbo()) {
            this.buffer = new VertexBuffer(format);
            this.buffer.bindBuffer();
            this.buffer.bufferData(vertices);
            this.buffer.unbindBuffer();
        }
        this.mode = mode;
    }

    public void render(double partialTicks) {
        GL11.glPushAttrib((int)1048575);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        this.buffer.drawArrays(this.mode);
        GL11.glPopAttrib();
    }
}

