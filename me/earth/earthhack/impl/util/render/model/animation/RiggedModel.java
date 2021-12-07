/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  jassimp.AiBone
 *  jassimp.AiBoneWeight
 *  jassimp.AiMesh
 *  jassimp.AiScene
 *  jassimp.AiWrapperProvider
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.Tessellator
 *  org.joml.Matrix4f
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL20
 *  org.lwjgl.opengl.GL30
 */
package me.earth.earthhack.impl.util.render.model.animation;

import jassimp.AiBone;
import jassimp.AiBoneWeight;
import jassimp.AiMesh;
import jassimp.AiScene;
import jassimp.AiWrapperProvider;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import me.earth.earthhack.impl.util.render.model.AssimpJomlProvider;
import me.earth.earthhack.impl.util.render.model.IModel;
import me.earth.earthhack.impl.util.render.model.Mesh;
import me.earth.earthhack.impl.util.render.model.ModelUtil;
import me.earth.earthhack.impl.util.render.model.Texture;
import me.earth.earthhack.impl.util.render.model.Vertex;
import me.earth.earthhack.impl.util.render.model.animation.Animation;
import me.earth.earthhack.impl.util.render.model.animation.AnimationWrapper;
import me.earth.earthhack.impl.util.render.model.animation.BoneInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class RiggedModel
implements IModel {
    private String name;
    private final Map<String, BoneInfo> bones = new HashMap<String, BoneInfo>();
    private final AiMesh[] aiMeshes;
    private final Mesh[] meshes;
    private final Animation[] animations;
    private final AnimationWrapper[] animationWrappers;
    private final int currentAnimation;
    private int boneCounter = 0;
    private long lastFrame = -1L;

    public RiggedModel(AiScene scene, AiMesh[] aiMeshes, Mesh[] meshes, String name) {
        int i;
        this.aiMeshes = aiMeshes;
        this.meshes = meshes;
        this.name = name;
        this.setupMeshes();
        this.setMeshBones();
        this.animations = new Animation[scene.getNumAnimations()];
        for (i = 0; i < this.animations.length; ++i) {
            this.animations[i] = new Animation(scene, this, i);
        }
        this.animationWrappers = new AnimationWrapper[this.animations.length];
        for (i = 0; i < this.animationWrappers.length; ++i) {
            this.animationWrappers[i] = new AnimationWrapper(this.animations[i]);
        }
        this.currentAnimation = 0;
    }

    private void setMeshBones() {
        block0: for (int k = 0; k < this.meshes.length; ++k) {
            AiMesh mesh = this.aiMeshes[k];
            Mesh customMesh = this.meshes[k];
            for (int i = 0; i < mesh.getBones().size(); ++i) {
                int boneID;
                AiBone bone = (AiBone)mesh.getBones().get(i);
                String name = bone.getName();
                System.out.println("Mesh name: " + mesh.getName() + " Bone: " + name);
                if (!this.bones.containsKey(name)) {
                    BoneInfo info = new BoneInfo(this.boneCounter, (Matrix4f)bone.getOffsetMatrix((AiWrapperProvider)new AssimpJomlProvider()));
                    boneID = this.boneCounter++;
                    this.bones.put(name, info);
                    if (this.boneCounter >= 100) {
                        continue block0;
                    }
                } else {
                    boneID = this.bones.get(name).getId();
                }
                int numWeights = bone.getNumWeights();
                for (int j = 0; j < numWeights; ++j) {
                    AiBoneWeight weight = (AiBoneWeight)bone.getBoneWeights().get(j);
                    int id = weight.getVertexId();
                    float fWeight = weight.getWeight();
                    Vertex vertex = customMesh.getVertices().get(id);
                    for (int l = 0; l < 4; ++l) {
                        if (vertex.getBones()[l] >= 0) continue;
                        vertex.getWeights()[l] = fWeight;
                        vertex.getBones()[l] = boneID;
                    }
                }
            }
        }
    }

    @Override
    public void setupMesh(Mesh mesh) {
        BufferBuilder builder = Tessellator.getInstance().getBuffer();
        builder.begin(4, ModelUtil.POS_NORMAL_TEX);
        for (Vertex vertex : mesh.getVertices()) {
            builder.pos(vertex.getPosition().x, vertex.getPosition().y, vertex.getPosition().z).normal((float)vertex.getNormal().x, (float)vertex.getNormal().y, (float)vertex.getNormal().z).tex(vertex.getTex().getX(), vertex.getTex().getY()).color(vertex.getBones()[0], vertex.getBones()[1], vertex.getBones()[2], vertex.getBones()[3]).color(vertex.getWeights()[0] / 255.0f, vertex.getWeights()[1] / 255.0f, vertex.getWeights()[2] / 255.0f, vertex.getWeights()[3] / 255.0f).endVertex();
        }
        builder.finishDrawing();
        builder.reset();
        ByteBuffer modelBuffer = builder.getByteBuffer();
        mesh.getBuffer().bufferData(modelBuffer);
        mesh.setVAO(GL30.glGenVertexArrays());
        mesh.getBuffer().bindBuffer();
        GL30.glBindVertexArray((int)mesh.getVAO());
        GL20.glEnableVertexAttribArray((int)0);
        GL20.glVertexAttribPointer((int)0, (int)3, (int)5126, (boolean)false, (int)64, (long)0L);
        GL20.glEnableVertexAttribArray((int)1);
        GL20.glVertexAttribPointer((int)1, (int)3, (int)5126, (boolean)false, (int)64, (long)12L);
        GL20.glEnableVertexAttribArray((int)2);
        GL20.glVertexAttribPointer((int)2, (int)2, (int)5126, (boolean)false, (int)64, (long)24L);
        GL20.glEnableVertexAttribArray((int)3);
        GL20.glVertexAttribPointer((int)3, (int)4, (int)5124, (boolean)false, (int)64, (long)32L);
        GL20.glEnableVertexAttribArray((int)4);
        GL20.glVertexAttribPointer((int)4, (int)4, (int)5126, (boolean)false, (int)64, (long)48L);
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
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (this.lastFrame == -1L) {
            this.lastFrame = System.currentTimeMillis();
        }
        long current = System.currentTimeMillis();
        long delta = current - this.lastFrame;
        this.lastFrame = current;
        ModelUtil.SHADER.bind();
        AnimationWrapper wrapper = this.animationWrappers[this.currentAnimation];
        wrapper.update(delta);
        Matrix4f[] matrices = wrapper.getMatrices();
        for (int i = 0; i < matrices.length; ++i) {
            ModelUtil.SHADER.set("finalBonesMatrices[" + i + "]", matrices[i]);
        }
        GL11.glTranslated((double)x, (double)y, (double)z);
        Mesh[] arrmesh = this.meshes;
        int n = arrmesh.length;
        for (int i = 0; i < n; ++i) {
            int i2 = 0;
            Mesh mesh = arrmesh[i];
            if (i2 < mesh.getTextures().size()) {
                Texture texture = mesh.getTextures().get(i2);
                GL11.glBindTexture((int)3553, (int)texture.getId());
            }
            ModelUtil.SHADER.set("sampler", 0);
            mesh.getBuffer().bindBuffer();
            GL30.glBindVertexArray((int)mesh.getVAO());
            mesh.getBuffer().drawArrays(4);
            mesh.getBuffer().unbindBuffer();
            GL11.glBindTexture((int)3553, (int)0);
            GL30.glBindVertexArray((int)0);
        }
        ModelUtil.SHADER.unbind();
        GL11.glTranslated((double)(-x), (double)(-y), (double)(-z));
        GL11.glDisable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glPopClientAttrib();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    @Override
    public Mesh[] getMeshes() {
        return this.meshes;
    }

    public Map<String, BoneInfo> getBones() {
        return this.bones;
    }

    public int getBoneCount() {
        return this.boneCounter;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Animation[] getAnimations() {
        return this.animations;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}

