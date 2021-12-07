/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.vertex.VertexBuffer
 *  net.minecraft.client.renderer.vertex.VertexFormat
 *  org.joml.Vector3f
 */
package me.earth.earthhack.impl.util.render.model;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import me.earth.earthhack.impl.util.render.model.ModelUtil;
import me.earth.earthhack.impl.util.render.model.Texture;
import me.earth.earthhack.impl.util.render.model.Vertex;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.joml.Vector3f;

public class Mesh {
    private final VertexBuffer meshBuffer;
    private FloatBuffer vertexBuffer;
    private ByteBuffer indexBuffer;
    private List<Vertex> vertices;
    private IntBuffer indices;
    private List<Texture> textures;
    private boolean rigged;
    private int vbo;
    private int vao;
    private int ebo;

    public Mesh(List<Vertex> vertices, IntBuffer indices, List<Texture> textures) {
        this.vertices = vertices;
        this.indices = indices;
        this.textures = textures;
        this.meshBuffer = new VertexBuffer(ModelUtil.POS_NORMAL_TEX);
    }

    public Mesh(List<Vertex> vertices, IntBuffer indices, List<Texture> textures, VertexFormat format, boolean rigged) {
        this.vertices = vertices;
        this.indices = indices;
        this.textures = textures;
        this.meshBuffer = new VertexBuffer(format);
    }

    public VertexBuffer getBuffer() {
        return this.meshBuffer;
    }

    public int getVAO() {
        return this.vao;
    }

    public void setVAO(int vao) {
        this.vao = vao;
    }

    public int getEBO() {
        return this.ebo;
    }

    public void setEBO(int ebo) {
        this.ebo = ebo;
    }

    public List<Texture> getTextures() {
        return this.textures;
    }

    public List<Vertex> getVertices() {
        return this.vertices;
    }

    public IntBuffer getIndices() {
        return this.indices;
    }

    public List<Vector3f> getVerticesAsVector() {
        ArrayList<Vector3f> toReturn = new ArrayList<Vector3f>();
        for (Vertex vertex : this.getVertices()) {
            toReturn.add(new Vector3f((float)vertex.getPosition().x, (float)vertex.getPosition().y, (float)vertex.getPosition().z));
        }
        return toReturn;
    }
}

