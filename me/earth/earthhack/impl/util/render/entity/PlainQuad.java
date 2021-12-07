/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.VertexFormat
 *  net.minecraft.client.renderer.vertex.VertexFormatElement
 *  org.joml.Vector3f
 */
package me.earth.earthhack.impl.util.render.entity;

import java.nio.FloatBuffer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import org.joml.Vector3f;

public class PlainQuad {
    private final Vector3f[] vertices = new Vector3f[4];
    private final VertexFormat format;
    private FloatBuffer buffer;

    public PlainQuad(Vector3f f, Vector3f f1, Vector3f f2, Vector3f f3, VertexFormat format, FloatBuffer buffer) {
        this.vertices[0] = f;
        this.vertices[1] = f1;
        this.vertices[2] = f2;
        this.vertices[3] = f3;
        this.format = format;
        this.buffer = buffer;
    }

    public void render(BufferBuilder renderer, float scale) {
        renderer.begin(7, this.format);
        int bufferIndex = 0;
        for (int i = 0; i < 4; ++i) {
            for (VertexFormatElement element : this.format.getElements()) {
                int size = element.getSize();
                switch (element.getUsage()) {
                    case POSITION: {
                        float x = size >= 1 ? this.buffer.get(bufferIndex) : 0.0f;
                        float y = size >= 2 ? this.buffer.get(bufferIndex + 1) : 0.0f;
                        float z = size >= 3 ? this.buffer.get(bufferIndex + 2) : 0.0f;
                        renderer.pos((double)(x * scale), (double)(y * scale), (double)(z * scale));
                        break;
                    }
                    case UV: {
                        float u = size >= 1 ? this.buffer.get(bufferIndex) : 0.0f;
                        float v = size >= 2 ? this.buffer.get(bufferIndex + 1) : 0.0f;
                        renderer.tex((double)(u * scale), (double)(v * scale));
                        break;
                    }
                    case NORMAL: {
                        float f = size >= 1 ? this.buffer.get(bufferIndex) : 0.0f;
                        float f1 = size >= 2 ? this.buffer.get(bufferIndex + 1) : 0.0f;
                        float f2 = size >= 3 ? this.buffer.get(bufferIndex + 2) : 0.0f;
                        renderer.normal(f * scale, f1 * scale, f2 * scale);
                        break;
                    }
                    case COLOR: {
                        float r = size >= 1 ? this.buffer.get(bufferIndex) : 0.0f;
                        float g = size >= 2 ? this.buffer.get(bufferIndex + 1) : 0.0f;
                        float b = size >= 3 ? this.buffer.get(bufferIndex + 2) : 0.0f;
                        float a = size >= 4 ? this.buffer.get(bufferIndex + 3) : 1.0f;
                        renderer.color(r * scale, g * scale, b * scale, a * scale);
                    }
                }
                bufferIndex += size;
            }
            renderer.endVertex();
        }
        Tessellator.getInstance().draw();
    }

    public FloatBuffer getBuffer() {
        return this.buffer;
    }

    public void setBuffer(FloatBuffer buffer) {
        this.buffer = buffer;
    }
}

