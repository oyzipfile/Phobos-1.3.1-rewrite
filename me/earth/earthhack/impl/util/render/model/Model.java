/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  jassimp.AiMesh
 *  jassimp.AiScene
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.Tessellator
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL20
 *  org.lwjgl.opengl.GL30
 */
package me.earth.earthhack.impl.util.render.model;

import jassimp.AiMesh;
import jassimp.AiScene;
import java.nio.ByteBuffer;
import me.earth.earthhack.impl.util.render.model.IModel;
import me.earth.earthhack.impl.util.render.model.Mesh;
import me.earth.earthhack.impl.util.render.model.ModelUtil;
import me.earth.earthhack.impl.util.render.model.Texture;
import me.earth.earthhack.impl.util.render.model.Vertex;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Model
implements IModel {
    private String name;
    protected final Mesh[] meshes;
    protected final AiMesh[] aiMeshes;

    public Model(AiMesh[] meshes, Mesh[] meshes1, String name) {
        this.aiMeshes = meshes;
        this.meshes = meshes1;
        this.name = name;
        this.setupMeshes();
    }

    @Override
    public Mesh[] getMeshes() {
        return this.meshes;
    }

    @Override
    public void setupMesh(Mesh mesh) {
        BufferBuilder builder = Tessellator.getInstance().getBuffer();
        builder.begin(4, ModelUtil.POS_NORMAL_TEX);
        for (Vertex vertex : mesh.getVertices()) {
            builder.pos(vertex.getPosition().x, vertex.getPosition().y, vertex.getPosition().z).normal((float)vertex.getNormal().x, (float)vertex.getNormal().y, (float)vertex.getNormal().z).tex(vertex.getTex().getX(), vertex.getTex().getY()).endVertex();
        }
        builder.finishDrawing();
        builder.reset();
        ByteBuffer modelBuffer = builder.getByteBuffer();
        mesh.getBuffer().bufferData(modelBuffer);
        mesh.setVAO(GL30.glGenVertexArrays());
        mesh.setEBO(OpenGlHelper.glGenBuffers());
        mesh.getBuffer().bindBuffer();
        GL30.glBindVertexArray((int)mesh.getVAO());
        GL20.glEnableVertexAttribArray((int)0);
        GL20.glVertexAttribPointer((int)0, (int)3, (int)5126, (boolean)false, (int)32, (long)0L);
        GL20.glEnableVertexAttribArray((int)1);
        GL20.glVertexAttribPointer((int)1, (int)3, (int)5126, (boolean)false, (int)32, (long)12L);
        GL20.glEnableVertexAttribArray((int)2);
        GL20.glVertexAttribPointer((int)2, (int)2, (int)5126, (boolean)false, (int)32, (long)24L);
        GL30.glBindVertexArray((int)0);
        mesh.getBuffer().unbindBuffer();
    }

    @Override
    public Mesh[] genMeshes(AiScene scene) {
        return new Mesh[0];
    }

    @Override
    public void render(double x, double y, double z, double partialTicks) {
        GL11.glPushAttrib((int)1048575);
        GL11.glPushMatrix();
        GL11.glPushClientAttrib((int)-1);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glHint((int)3155, (int)4354);
        GL11.glDisable((int)2896);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2884);
        GL11.glEnable((int)2929);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glTranslated((double)x, (double)y, (double)z);
        ModelUtil.SHADER_BONELESS.bind();
        Mesh[] arrmesh = this.meshes;
        int n = arrmesh.length;
        for (int i = 0; i < n; ++i) {
            int i2 = 0;
            Mesh mesh = arrmesh[i];
            if (i2 < mesh.getTextures().size()) {
                Texture texture = mesh.getTextures().get(i2);
                GL11.glBindTexture((int)3553, (int)texture.getId());
            }
            ModelUtil.SHADER_BONELESS.set("sampler", 0);
            GL30.glBindVertexArray((int)mesh.getVAO());
            mesh.getBuffer().bindBuffer();
            mesh.getBuffer().drawArrays(4);
            mesh.getBuffer().unbindBuffer();
            GL11.glBindTexture((int)3553, (int)0);
            GL30.glBindVertexArray((int)0);
        }
        ModelUtil.SHADER_BONELESS.unbind();
        GL11.glTranslated((double)(-x), (double)(-y), (double)(-z));
        GL11.glDisable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glPopClientAttrib();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}

