/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  jassimp.AiAnimation
 *  jassimp.AiMaterial
 *  jassimp.AiMaterial$PropertyKey
 *  jassimp.AiMesh
 *  jassimp.AiNodeAnim
 *  jassimp.AiPostProcessSteps
 *  jassimp.AiScene
 *  jassimp.AiTextureType
 *  jassimp.AiWrapperProvider
 *  jassimp.Jassimp
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.client.renderer.vertex.VertexFormat
 *  net.minecraft.client.renderer.vertex.VertexFormatElement
 *  net.minecraft.client.renderer.vertex.VertexFormatElement$EnumType
 *  net.minecraft.client.renderer.vertex.VertexFormatElement$EnumUsage
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL30
 */
package me.earth.earthhack.impl.util.render.model;

import jassimp.AiAnimation;
import jassimp.AiMaterial;
import jassimp.AiMesh;
import jassimp.AiNodeAnim;
import jassimp.AiPostProcessSteps;
import jassimp.AiScene;
import jassimp.AiTextureType;
import jassimp.AiWrapperProvider;
import jassimp.Jassimp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.impl.util.math.Vec2d;
import me.earth.earthhack.impl.util.render.GlShader;
import me.earth.earthhack.impl.util.render.image.EfficientTexture;
import me.earth.earthhack.impl.util.render.image.ImageUtil;
import me.earth.earthhack.impl.util.render.model.AssimpJomlProvider;
import me.earth.earthhack.impl.util.render.model.IModel;
import me.earth.earthhack.impl.util.render.model.Mesh;
import me.earth.earthhack.impl.util.render.model.Model;
import me.earth.earthhack.impl.util.render.model.Texture;
import me.earth.earthhack.impl.util.render.model.Vertex;
import me.earth.earthhack.impl.util.render.model.animation.Animation;
import me.earth.earthhack.impl.util.render.model.animation.RiggedModel;
import me.earth.earthhack.impl.util.render.model.animation.SourceNode;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class ModelUtil
implements Globals {
    private static final String NAME = "D:\\DownloadsHDD\\astolfo-obj\\astolfo.obj";
    private static final String NAME1 = "D:\\DownloadsHDD\\astolfo-in-a-hoodie\\source\\Astolfo\\Astolfo.fbx";
    private static final String NAME2 = "D:\\DownloadsHDD\\benette\\source\\Benette.glb";
    private static final String NAME3 = "D:\\DownloadsHDD\\benette(1)\\scene.gltf";
    private static final String NAME4 = "D:\\DownloadsHDD\\kizuna-ai\\source\\Kizunaai.fbx";
    private static final String ROOT = "D:\\DownloadsHDD\\kizuna-ai\\textures";
    public static final String ROOT1 = "D:\\DownloadsHDD\\genshin-impact-amber\\source\\Amber\\Amber";
    public static final String ROOT2 = "D:\\DownloadsHDD\\astolfo-in-a-hoodie(1)\\source\\Astolfo";
    public static final String NAME6 = "D:\\DownloadsHDD\\astolfo-in-a-hoodie(1)\\source\\Astolfo\\Astolfo.fbx";
    public static final String NAME5 = "D:\\DownloadsHDD\\genshin-impact-amber\\source\\Amber\\Amber\\Amber.fbx";
    public static final String NAME7 = "D:\\DownloadsHDD\\kawaiope\\source\\cali_+keyed2_8+1.blend";
    public static final String NAME10 = "D:\\DownloadsHDD\\kawaiope(1)\\scene.gltf";
    public static final String ROOT3 = "D:\\DownloadsHDD\\kawaiope(1)";
    public static final String ROOT6 = "D:\\DownloadsHDD\\kawaiope\\source";
    public static final String NAME11 = "D:\\DownloadsHDD\\kawaiope\\source\\calli.fbx";
    public static final String NAME8 = "D:\\DownloadsHDD\\51dc47334dee42b9bb8e53ee07aa8006\\source\\sexygirl.blend";
    public static final String ROOT4 = "D:\\DownloadsHDD\\51dc47334dee42b9bb8e53ee07aa8006";
    public static final String NAME9 = "D:\\DownloadsHDD\\rigged-anime-girl-2020-outfits-expressions\\source\\ANIME GIRLS 2020\\RIGGED\\RIGGED\\suit_girl_update_NEW.fbx";
    public static final String ROOT5 = "D:\\DownloadsHDD\\rigged-anime-girl-2020-outfits-expressions\\textures";
    public static final String NAME12 = "D:\\DownloadsHDD\\zero-two-fully-rigged\\source\\02_pose1.fbx";
    public static final String NAME13 = "D:\\DownloadsHDD\\02\\02noncel_T_rigged.FBX";
    public static final String ROOT7 = "D:\\DownloadsHDD\\zero-two-fully-rigged";
    public static final VertexFormatElement NORMAL_3F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.NORMAL, 3);
    public static final VertexFormatElement BONE_4I = new VertexFormatElement(0, VertexFormatElement.EnumType.INT, VertexFormatElement.EnumUsage.GENERIC, 4);
    public static final VertexFormatElement WEIGHT_4F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.GENERIC, 4);
    public static final VertexFormat POS_NORMAL_TEX = new VertexFormat();
    public static final VertexFormat POS_NORMAL_TEX_BONE_WEIGHT = new VertexFormat();
    public static final VertexFormat POS_NORMAL = new VertexFormat();
    public static final GlShader SHADER = GlShader.createShader("modelrender");
    public static final GlShader SHADER_BONELESS = GlShader.createShader("modelrenderboneless");
    private static Mesh modelMesh;
    private static IModel model;
    public static final List<Mesh> meshes;

    public static Animation[] loadAnimations(String animationPath, boolean debug) throws IOException {
        AiScene scene = Jassimp.importFile((String)animationPath, Collections.singleton(AiPostProcessSteps.TRIANGULATE));
        for (AiAnimation animation : scene.getAnimations()) {
            if (!debug) continue;
            System.out.println((Object)animation);
        }
        return null;
    }

    public static IModel loadModel(String model, String root, boolean debug) throws IOException {
        boolean rigged;
        ArrayList<Mesh> meshList = new ArrayList<Mesh>();
        HashSet<AiPostProcessSteps> steps = new HashSet<AiPostProcessSteps>();
        steps.add(AiPostProcessSteps.TRIANGULATE);
        steps.add(AiPostProcessSteps.FIX_INFACING_NORMALS);
        AiScene scene = Jassimp.importFile((String)model, steps);
        System.out.println((Object)scene);
        String name1 = ((SourceNode)scene.getSceneRoot((AiWrapperProvider)new AssimpJomlProvider())).getName();
        for (AiMesh mesh : scene.getMeshes()) {
            if (debug) {
                System.out.println("Mesh name: " + mesh.getName());
            }
            AiMaterial material = (AiMaterial)scene.getMaterials().get(mesh.getMaterialIndex());
            ArrayList<Texture> textures = new ArrayList<Texture>();
            if (material.hasProperties(Collections.singleton(AiMaterial.PropertyKey.TEX_FILE))) {
                int numTextures = material.getNumTextures(AiTextureType.DIFFUSE);
                for (int i = 0; i < numTextures; ++i) {
                    String name = material.getTextureFile(AiTextureType.DIFFUSE, i);
                    try {
                        if (debug) {
                            System.out.println("Texture name: " + name);
                        }
                        BufferedImage image = ImageUtil.createFlipped(ImageUtil.bufferedImageFromFile(new File(root + File.separator + name)));
                        String generatedString = UUID.randomUUID().toString().split("-")[0];
                        EfficientTexture texture = ImageUtil.cacheBufferedImage(image, "png", generatedString);
                        textures.add(new Texture(texture.getGlTextureId(), AiTextureType.DIFFUSE, texture, image.getWidth(), image.getHeight()));
                        continue;
                    }
                    catch (IOException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }
            int numFaces = mesh.getNumFaces();
            ArrayList<Vertex> vertices = new ArrayList<Vertex>();
            for (int j = 0; j < numFaces; ++j) {
                int numFacesIndices = mesh.getFaceNumIndices(j);
                for (int k = 0; k < numFacesIndices; ++k) {
                    int i = mesh.getFaceVertex(j, k);
                    float x = mesh.getPositionX(i);
                    float y = mesh.getPositionY(i);
                    float z = mesh.getPositionZ(i);
                    float normX = mesh.getNormalX(i);
                    float normY = mesh.getNormalX(i);
                    float normZ = mesh.getNormalX(i);
                    boolean flag = false;
                    float u = 0.0f;
                    float v = 0.0f;
                    if (mesh.hasTexCoords(0)) {
                        u = mesh.getTexCoordU(i, 0);
                        v = mesh.getTexCoordV(i, 0);
                    }
                    Vertex vertex = new Vertex(new Vec3d((double)x, (double)y, (double)z), new Vec3d((double)normX, (double)normY, (double)normZ), new Vec2d(u, v));
                    vertices.add(vertex);
                }
            }
            meshList.add(new Mesh(vertices, mesh.getIndexBuffer(), textures));
        }
        Mesh[] meshArray = new Mesh[meshList.size()];
        for (int i = 0; i < meshArray.length; ++i) {
            meshArray[i] = (Mesh)meshList.get(i);
        }
        AiMesh[] meshes = new AiMesh[scene.getNumMeshes()];
        for (int i = 0; i < meshes.length; ++i) {
            meshes[i] = (AiMesh)scene.getMeshes().get(i);
        }
        boolean bl = rigged = scene.getNumAnimations() > 0;
        if (rigged) {
            return new RiggedModel(scene, meshes, meshArray, name1);
        }
        return new Model(null, meshArray, name1);
    }

    public static Animation[] loadAnimations(AiScene scene) {
        int numAnimations = scene.getNumAnimations();
        Animation[] animations = new Animation[numAnimations];
        for (int i = 0; i < numAnimations; ++i) {
            AiAnimation animation = (AiAnimation)scene.getAnimations().get(i);
            double ticks = animation.getDuration();
            double tps = animation.getTicksPerSecond();
            int nodeID = 0;
            for (AiNodeAnim node : animation.getChannels()) {
                String name = node.getNodeName();
                int n = nodeID++;
            }
        }
        return animations;
    }

    public static void loadModel() throws IOException {
        HashSet<AiPostProcessSteps> steps = new HashSet<AiPostProcessSteps>();
        steps.add(AiPostProcessSteps.TRIANGULATE);
        steps.add(AiPostProcessSteps.FIX_INFACING_NORMALS);
        AiScene scene = Jassimp.importFile((String)NAME13, steps);
        System.out.println((Object)scene);
        String name1 = ((SourceNode)scene.getSceneRoot((AiWrapperProvider)new AssimpJomlProvider())).getName();
        for (AiMesh mesh : scene.getMeshes()) {
            AiMaterial material = (AiMaterial)scene.getMaterials().get(mesh.getMaterialIndex());
            ArrayList<Texture> textures = new ArrayList<Texture>();
            if (material.hasProperties(Collections.singleton(AiMaterial.PropertyKey.TEX_FILE))) {
                for (AiTextureType type : AiTextureType.values()) {
                    try {
                        int numTextures = material.getNumTextures(type);
                        for (int i = 0; i < numTextures; ++i) {
                            String name = material.getTextureFile(type, i);
                            try {
                                System.out.println("Texture name: " + name + ", Type: " + type.name());
                                BufferedImage image = ImageUtil.createFlipped(ImageUtil.bufferedImageFromFile(new File(ROOT7 + File.separator + name)));
                                String generatedString = UUID.randomUUID().toString().split("-")[0];
                                EfficientTexture texture = ImageUtil.cacheBufferedImage(image, "png", generatedString);
                                System.out.println("Texture dimensions: Width: " + image.getWidth() + " Height: " + image.getHeight());
                                textures.add(new Texture(texture.getGlTextureId(), type, texture, image.getWidth(), image.getHeight()));
                                System.out.println("Texture ID: " + texture.getGlTextureId());
                                continue;
                            }
                            catch (IOException | NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    catch (NullPointerException numTextures) {
                        // empty catch block
                    }
                }
            }
            int numVertices = mesh.getNumVertices();
            int numFaces = mesh.getNumFaces();
            ArrayList<Vertex> vertices = new ArrayList<Vertex>();
            System.out.println("Mesh name: " + mesh.getName());
            for (int j = 0; j < numFaces; ++j) {
                int numFacesIndices = mesh.getFaceNumIndices(j);
                for (int k = 0; k < numFacesIndices; ++k) {
                    int i = mesh.getFaceVertex(j, k);
                    float x = mesh.getPositionX(i);
                    float y = mesh.getPositionY(i);
                    float z = mesh.getPositionZ(i);
                    float normX = mesh.getNormalX(i);
                    float normY = mesh.getNormalX(i);
                    float normZ = mesh.getNormalX(i);
                    boolean flag = false;
                    float u = 0.0f;
                    float v = 0.0f;
                    if (mesh.hasTexCoords(0)) {
                        u = mesh.getTexCoordU(i, 0);
                        v = mesh.getTexCoordV(i, 0);
                    }
                    Vertex vertex = new Vertex(new Vec3d((double)x, (double)y, (double)z), new Vec3d((double)normX, (double)normY, (double)normZ), new Vec2d(u, v));
                    vertices.add(vertex);
                }
            }
            meshes.add(new Mesh(vertices, null, textures));
        }
        Mesh[] meshes1 = new Mesh[meshes.size()];
        meshes.toArray(meshes1);
        model = new Model(null, meshes1, name1);
    }

    public static void renderModel(Mesh[] meshArray, double x, double y, double z) {
        GL11.glPushAttrib((int)1048575);
        GL11.glPushMatrix();
        GL11.glPushClientAttrib((int)-1);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glHint((int)3155, (int)4354);
        GL11.glDisable((int)2896);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glTranslated((double)x, (double)y, (double)z);
        Mesh[] arrmesh = meshArray;
        int n = arrmesh.length;
        for (int i = 0; i < n; ++i) {
            int i2 = 0;
            Mesh mesh = arrmesh[i];
            if (i2 < mesh.getTextures().size()) {
                Texture texture = mesh.getTextures().get(i2);
                GL11.glBindTexture((int)3553, (int)texture.getId());
            }
            mesh.getBuffer().bindBuffer();
            GL11.glVertexPointer((int)3, (int)5126, (int)32, (long)0L);
            GL11.glEnableClientState((int)32884);
            GL11.glNormalPointer((int)5126, (int)32, (long)12L);
            GL11.glEnableClientState((int)32885);
            GL11.glTexCoordPointer((int)2, (int)5126, (int)32, (long)24L);
            GL11.glEnableClientState((int)32888);
            mesh.getBuffer().drawArrays(4);
            mesh.getBuffer().unbindBuffer();
            GL11.glBindTexture((int)3553, (int)0);
            GL11.glDisableClientState((int)32884);
            GL11.glDisableClientState((int)32885);
            GL11.glDisableClientState((int)32888);
        }
        GL11.glTranslated((double)(-x), (double)(-y), (double)(-z));
        GL11.glDisable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glPopClientAttrib();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void renderModel(double x, double y, double z) {
        model.render(x, y, z, mc.getRenderPartialTicks());
    }

    public static void renderModel1(double x, double y, double z) {
        GL11.glPushAttrib((int)1048575);
        GL11.glPushMatrix();
        GL11.glPushClientAttrib((int)-1);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glHint((int)3155, (int)4354);
        GL11.glDisable((int)2896);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glTranslated((double)x, (double)y, (double)z);
        for (Mesh mesh : meshes) {
            SHADER_BONELESS.bind();
            int i = 0;
            if (i < mesh.getTextures().size()) {
                Texture texture = mesh.getTextures().get(i);
                GL11.glBindTexture((int)3553, (int)texture.getId());
            }
            SHADER_BONELESS.set("sampler", 0);
            mesh.getBuffer().bindBuffer();
            GL30.glBindVertexArray((int)mesh.getVAO());
            mesh.getBuffer().drawArrays(4);
            mesh.getBuffer().unbindBuffer();
            GL30.glBindVertexArray((int)0);
            GL11.glBindTexture((int)3553, (int)0);
            SHADER_BONELESS.unbind();
        }
        GL11.glTranslated((double)(-x), (double)(-y), (double)(-z));
        GL11.glDisable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glPopClientAttrib();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    static {
        meshes = new ArrayList<Mesh>();
        POS_NORMAL_TEX.addElement(DefaultVertexFormats.POSITION_3F);
        POS_NORMAL_TEX.addElement(NORMAL_3F);
        POS_NORMAL_TEX.addElement(DefaultVertexFormats.TEX_2F);
        POS_NORMAL.addElement(DefaultVertexFormats.POSITION_3F);
        POS_NORMAL.addElement(NORMAL_3F);
        POS_NORMAL_TEX_BONE_WEIGHT.addElement(DefaultVertexFormats.POSITION_3F);
        POS_NORMAL_TEX_BONE_WEIGHT.addElement(NORMAL_3F);
        POS_NORMAL_TEX_BONE_WEIGHT.addElement(DefaultVertexFormats.TEX_2F);
        POS_NORMAL_TEX_BONE_WEIGHT.addElement(BONE_4I);
        POS_NORMAL_TEX_BONE_WEIGHT.addElement(WEIGHT_4F);
        Jassimp.setWrapperProvider((AiWrapperProvider)new AssimpJomlProvider());
    }
}

