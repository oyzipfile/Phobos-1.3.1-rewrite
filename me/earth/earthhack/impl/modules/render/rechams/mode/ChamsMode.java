/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.GLAllocation
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.entity.RenderEnderCrystal
 *  net.minecraft.client.renderer.entity.RenderLivingBase
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.Tuple
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec2f
 *  org.joml.Vector4f
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL13
 */
package me.earth.earthhack.impl.modules.render.rechams.mode;

import java.awt.Color;
import java.nio.FloatBuffer;
import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.impl.core.ducks.entity.IEntityRenderer;
import me.earth.earthhack.impl.core.mixins.render.entity.IRenderEnderCrystal;
import me.earth.earthhack.impl.event.events.render.BeginRenderEvent;
import me.earth.earthhack.impl.event.events.render.CrystalRenderEvent;
import me.earth.earthhack.impl.event.events.render.ModelRenderEvent;
import me.earth.earthhack.impl.event.events.render.PreRenderHandEvent;
import me.earth.earthhack.impl.event.events.render.Render2DEvent;
import me.earth.earthhack.impl.event.events.render.Render3DEvent;
import me.earth.earthhack.impl.event.events.render.RenderArmorEvent;
import me.earth.earthhack.impl.event.events.render.RenderCrystalCubeEvent;
import me.earth.earthhack.impl.event.events.render.RenderEntityEvent;
import me.earth.earthhack.impl.event.events.render.WorldRenderEvent;
import me.earth.earthhack.impl.managers.Managers;
import me.earth.earthhack.impl.modules.render.rechams.Chams;
import me.earth.earthhack.impl.modules.render.rechams.mode.ChamsPage;
import me.earth.earthhack.impl.util.minecraft.EntityType;
import me.earth.earthhack.impl.util.render.OutlineUtil;
import me.earth.earthhack.impl.util.render.Render2DUtil;
import me.earth.earthhack.impl.util.render.shader.FramebufferWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class ChamsMode
extends Enum<ChamsMode>
implements Globals {
    public static final /* enum */ ChamsMode None = new ChamsMode();
    public static final /* enum */ ChamsMode Normal = new ChamsMode();
    public static final /* enum */ ChamsMode Better = new ChamsMode(){

        @Override
        public void renderPre(ModelRenderEvent.Pre event, Chams module) {
            event.setCancelled(true);
            event.setCancelled(true);
            Color color = module.getColor((Entity)event.getEntity());
            Color wallsColor = module.getWallsColor((Entity)event.getEntity());
            GL11.glPushMatrix();
            GL11.glPushAttrib((int)1048575);
            GL11.glDisable((int)3008);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)1.5f);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2960);
            GL11.glEnable((int)10754);
            GL11.glDepthMask((boolean)false);
            GL11.glDisable((int)2929);
            GL11.glColor4f((float)((float)wallsColor.getRed() / 255.0f), (float)((float)wallsColor.getGreen() / 255.0f), (float)((float)wallsColor.getBlue() / 255.0f), (float)((float)wallsColor.getAlpha() / 255.0f));
            ChamsMode.render(event);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
            if (module.shouldXQZ((Entity)event.getEntity())) {
                GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
                ChamsMode.render(event);
            }
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2896);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3008);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
            if (module.shouldGlint((Entity)event.getEntity())) {
                GL11.glPushAttrib((int)1048575);
                GL11.glClearStencil((int)0);
                GL11.glClear((int)1024);
                GL11.glEnable((int)2960);
                GL11.glStencilOp((int)7680, (int)7680, (int)7681);
                GL11.glStencilFunc((int)519, (int)1, (int)255);
                GL11.glStencilMask((int)255);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                ChamsMode.render(event);
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GL11.glStencilMask((int)0);
                GL11.glStencilFunc((int)514, (int)1, (int)255);
                ChamsMode.renderGlint(event, module, module.getGlintColor((Entity)event.getEntity()));
                GL11.glStencilFunc((int)514, (int)0, (int)255);
                ChamsMode.renderGlint(event, module, module.getGlintWallsColor((Entity)event.getEntity()));
                GL11.glDisable((int)2960);
                GL11.glPopAttrib();
            }
            if (module.shouldLightning((Entity)event.getEntity())) {
                ChamsMode.renderLightning(event, module);
            }
        }
    };
    public static final /* enum */ ChamsMode JelloBottom = new ChamsMode(){

        @Override
        public void renderPre(ModelRenderEvent.Pre event, Chams module) {
            event.setCancelled(true);
            ChamsMode.render(event);
            Color color = module.getColor((Entity)event.getEntity());
            GL11.glPushMatrix();
            GL11.glPushAttrib((int)1048575);
            GL11.glDisable((int)3008);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)1.5f);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2960);
            GL11.glEnable((int)10754);
            GL11.glDepthMask((boolean)false);
            GL11.glDisable((int)2929);
            GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
            ChamsMode.render(event);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2896);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3008);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
            if (module.shouldGlint((Entity)event.getEntity())) {
                // empty if block
            }
            if (module.shouldLightning((Entity)event.getEntity())) {
                ChamsMode.renderLightning(event, module);
            }
        }
    };
    public static final /* enum */ ChamsMode JelloTop = new ChamsMode(){

        @Override
        public void renderPost(ModelRenderEvent.Post event, Chams module) {
            Color color = module.getColor((Entity)event.getEntity());
            GL11.glPushMatrix();
            GL11.glPushAttrib((int)1048575);
            GL11.glDisable((int)3008);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)1.5f);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2960);
            GL11.glEnable((int)10754);
            GL11.glDepthMask((boolean)false);
            GL11.glDisable((int)2929);
            GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
            ChamsMode.render(event);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2896);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3008);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
            event.setCancelled(true);
            ChamsMode.render(event);
            if (module.shouldGlint((Entity)event.getEntity())) {
                // empty if block
            }
            if (module.shouldLightning((Entity)event.getEntity())) {
                ChamsMode.renderLightning(event, module);
            }
        }
    };
    public static final /* enum */ ChamsMode Shader = new ChamsMode(){

        @Override
        public void renderPre(ModelRenderEvent.Pre event, Chams module) {
        }

        @Override
        public void renderCrystalPre(CrystalRenderEvent.Pre event, Chams module) {
        }

        @Override
        public void beginRender(BeginRenderEvent event, Chams module) {
            OutlineUtil.checkSetupFBO();
            GL11.glClear((int)1024);
            GL11.glClearStencil((int)0);
        }
    };
    public static final /* enum */ ChamsMode FramebufferImage = new ChamsMode(){

        @Override
        public void renderEntity(RenderEntityEvent.Pre event, Chams module) {
            if (module.isValid(event.getEntity(), this) && !module.forceRenderEntities) {
                event.setCancelled(true);
                GL11.glPushAttrib((int)1048575);
                if (module.shouldAlphaTest(event.getEntity())) {
                    GL11.glEnable((int)3008);
                }
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                if (module.shouldXQZ(event.getEntity())) {
                    GL11.glEnable((int)2960);
                    GL11.glStencilOp((int)7680, (int)7681, (int)7680);
                    GL11.glStencilFunc((int)517, (int)ChamsMode.getMaskFromEntity(event.getEntity()), (int)255);
                    GL11.glStencilMask((int)ChamsMode.getWallsMaskFromEntity(event.getEntity()));
                    ChamsMode.render(event);
                }
                GL11.glEnable((int)2960);
                GL11.glStencilOp((int)7680, (int)7680, (int)7681);
                GL11.glStencilFunc((int)519, (int)ChamsMode.getMaskFromEntity(event.getEntity()), (int)255);
                GL11.glStencilMask((int)255);
                ChamsMode.render(event);
                GL11.glStencilMask((int)0);
                GL11.glPopAttrib();
            }
        }

        @Override
        public void renderEntityPost(RenderEntityEvent.Post event, Chams module) {
        }

        @Override
        public void renderWorld(WorldRenderEvent event, Chams module) {
        }

        @Override
        public void renderHud(PreRenderHandEvent event, Chams module) {
        }

        @Override
        public void render3D(Render3DEvent event, Chams module) {
            for (Tuple<ChamsPage, FramebufferWrapper> tuple : module.getFramebuffersFromMode(this)) {
                ((FramebufferWrapper)tuple.getSecond()).updateFramebuffer();
            }
            for (Entity entity : 5.mc.world.loadedEntityList) {
                if (!module.isValid(entity, this)) continue;
                FramebufferWrapper wrapper = (FramebufferWrapper)module.getFrameBufferFromEntity(entity).getSecond();
                boolean renderShadows = 5.mc.gameSettings.entityShadows;
                5.mc.gameSettings.entityShadows = false;
                module.forceRenderEntities = true;
                wrapper.renderToFramebuffer(() -> {
                    ((IEntityRenderer)5.mc.entityRenderer).invokeSetupCameraTransform(mc.getRenderPartialTicks(), 0);
                    GlStateManager.enableColorMaterial();
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.enableDepth();
                    GlStateManager.enableAlpha();
                    GlStateManager.depthMask((boolean)true);
                    mc.getRenderManager().renderEntityStatic(entity, mc.getRenderPartialTicks(), true);
                    GlStateManager.depthMask((boolean)false);
                    GlStateManager.disableAlpha();
                    GlStateManager.disableDepth();
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.disableColorMaterial();
                });
                module.forceRenderEntities = false;
                5.mc.gameSettings.entityShadows = renderShadows;
            }
            GlStateManager.pushAttrib();
            GlStateManager.enableDepth();
            GlStateManager.depthMask((boolean)true);
            GL11.glEnable((int)2960);
            GL11.glStencilOp((int)7680, (int)7680, (int)7680);
            GL11.glStencilMask((int)0);
            for (Tuple tuple : module.getFramebuffersFromMode(this)) {
                GL11.glStencilFunc((int)514, (int)ChamsMode.getMaskFromEntityType((ChamsPage)((Object)tuple.getFirst())), (int)255);
                module.framebufferImageShader.bind();
                module.framebufferImageShader.set("colorMixFactor", module.colorMixFactor.getValue().floatValue());
                module.framebufferImageShader.set("mixFactor", module.crystalMixFactor.getValue().floatValue());
                Color color = module.crystalColor.getValue();
                module.framebufferImageShader.set("inputColor", new Vector4f((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f));
                int id = module.shouldGif((ChamsPage)((Object)tuple.getFirst())) ? module.getGif((ChamsPage)((Object)tuple.getFirst())).getDynamicTexture().getGlTextureId() : module.getImage((ChamsPage)((Object)tuple.getFirst())).getTexture().getGlTextureId();
                ((FramebufferWrapper)tuple.getSecond()).renderFramebuffer(() -> {
                    5.mc.entityRenderer.setupOverlayRendering();
                    module.framebufferImageShader.set("sampler", 0);
                    GL13.glActiveTexture((int)33992);
                    GL11.glBindTexture((int)3553, (int)id);
                    module.framebufferImageShader.set("overlaySampler", 8);
                    GL13.glActiveTexture((int)33984);
                });
                module.framebufferImageShader.unbind();
                GL11.glStencilFunc((int)514, (int)ChamsMode.getWallsMaskFromEntityType((ChamsPage)((Object)tuple.getFirst())), (int)255);
                module.framebufferImageShader.bind();
                module.framebufferImageShader.set("colorMixFactor", module.colorMixFactor.getValue().floatValue());
                module.framebufferImageShader.set("mixFactor", module.crystalMixFactor.getValue().floatValue());
                module.framebufferImageShader.set("inputColor", new Vector4f((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f));
                int wallsId = module.shouldWallsGif((ChamsPage)((Object)tuple.getFirst())) ? module.getWallsGif((ChamsPage)((Object)tuple.getFirst())).getDynamicTexture().getGlTextureId() : module.getWallsImage((ChamsPage)((Object)tuple.getFirst())).getTexture().getGlTextureId();
                ((FramebufferWrapper)tuple.getSecond()).renderFramebuffer(() -> {
                    5.mc.entityRenderer.setupOverlayRendering();
                    module.framebufferImageShader.set("sampler", 0);
                    GL13.glActiveTexture((int)33992);
                    GL11.glBindTexture((int)3553, (int)wallsId);
                    module.framebufferImageShader.set("overlaySampler", 8);
                    GL13.glActiveTexture((int)33984);
                });
                module.framebufferImageShader.unbind();
            }
            GlStateManager.depthMask((boolean)false);
            GlStateManager.disableDepth();
            GlStateManager.popAttrib();
            ((IEntityRenderer)5.mc.entityRenderer).invokeSetupCameraTransform(mc.getRenderPartialTicks(), 0);
        }

        @Override
        public void beginRender(BeginRenderEvent event, Chams module) {
            OutlineUtil.checkSetupFBO();
            GL11.glClear((int)1024);
            GL11.glClearStencil((int)0);
        }
    };
    public static final /* enum */ ChamsMode FramebufferTest = new ChamsMode(){

        @Override
        public void renderPre(ModelRenderEvent.Pre event, Chams module) {
        }

        @Override
        public void render3D(Render3DEvent event, Chams module) {
            GL11.glPushAttrib((int)1048575);
            GL11.glEnable((int)2960);
            GL11.glStencilFunc((int)514, (int)4, (int)255);
            GL11.glStencilMask((int)0);
            FloatBuffer buffer = GLAllocation.createDirectFloatBuffer((int)16);
            FloatBuffer modelViewBuffer = GLAllocation.createDirectFloatBuffer((int)16);
            GL11.glGetFloat((int)2983, (FloatBuffer)buffer);
            GL11.glGetFloat((int)2982, (FloatBuffer)modelViewBuffer);
            ScaledResolution scaledresolution = new ScaledResolution(mc);
            GL11.glMatrixMode((int)5889);
            GL11.glLoadIdentity();
            GlStateManager.ortho((double)0.0, (double)scaledresolution.getScaledWidth_double(), (double)scaledresolution.getScaledHeight_double(), (double)0.0, (double)1000.0, (double)3000.0);
            GL11.glMatrixMode((int)5888);
            GL11.glLoadIdentity();
            GlStateManager.translate((float)0.0f, (float)0.0f, (float)-2000.0f);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
            mc.getTextureManager().bindTexture(me.earth.earthhack.impl.modules.render.chams.Chams.GALAXY_LOCATION);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            Gui.drawModalRectWithCustomSizedTexture((int)0, (int)0, (float)0.0f, (float)0.0f, (int)scaledresolution.getScaledWidth(), (int)scaledresolution.getScaledHeight(), (float)scaledresolution.getScaledWidth(), (float)scaledresolution.getScaledHeight());
            GL11.glStencilFunc((int)514, (int)5, (int)255);
            Render2DUtil.drawRect(0.0f, 0.0f, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), Color.GREEN.getRGB());
            GL11.glMatrixMode((int)5889);
            GL11.glLoadMatrix((FloatBuffer)buffer);
            GL11.glMatrixMode((int)5888);
            GL11.glPopAttrib();
        }

        @Override
        public void beginRender(BeginRenderEvent event, Chams module) {
            OutlineUtil.checkSetupFBO();
            GL11.glClear((int)1024);
            GL11.glClearStencil((int)0);
        }

        @Override
        public void renderCrystalPre(CrystalRenderEvent.Pre event, Chams module) {
            event.setCancelled(true);
            GL11.glPushAttrib((int)1048575);
            GL11.glEnable((int)2960);
            GL11.glEnable((int)3008);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)3553);
            GL11.glStencilOp((int)7680, (int)7681, (int)7680);
            GL11.glStencilFunc((int)519, (int)5, (int)255);
            GL11.glStencilMask((int)255);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ChamsMode.render(event);
            GL11.glStencilOp((int)7680, (int)7680, (int)7681);
            GL11.glStencilFunc((int)519, (int)4, (int)255);
            GL11.glStencilMask((int)255);
            ChamsMode.render(event);
            GL11.glPopAttrib();
        }
    };
    public static final /* enum */ ChamsMode ShaderImage = new ChamsMode(){

        @Override
        public void renderPre(ModelRenderEvent.Pre event, Chams module) {
            ScaledResolution resolution = new ScaledResolution(mc);
            float[] rect = Render2DUtil.getOnScreen2DHitBox((Entity)event.getEntity(), resolution.getScaledWidth(), resolution.getScaledHeight());
            GL11.glPushMatrix();
            GL11.glPushAttrib((int)1048575);
            event.setCancelled(true);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glDisable((int)3008);
            GL11.glDisable((int)2896);
            if (module.shouldAlphaTest((Entity)event.getEntity())) {
                GL11.glEnable((int)3008);
            }
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            if (module.shouldXQZ((Entity)event.getEntity())) {
                GL11.glEnable((int)2960);
                GL11.glStencilOp((int)7680, (int)7681, (int)7680);
                GL11.glStencilFunc((int)517, (int)ChamsMode.getMaskFromEntity((Entity)event.getEntity()), (int)255);
                GL11.glStencilMask((int)ChamsMode.getWallsMaskFromEntity((Entity)event.getEntity()));
                ChamsMode.render(event);
            }
            GL11.glEnable((int)2960);
            GL11.glStencilOp((int)7680, (int)7680, (int)7681);
            GL11.glStencilFunc((int)519, (int)ChamsMode.getMaskFromEntity((Entity)event.getEntity()), (int)255);
            GL11.glStencilMask((int)255);
            ChamsMode.render(event);
            GL11.glStencilMask((int)0);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glStencilOp((int)7680, (int)7680, (int)7680);
            GL11.glStencilFunc((int)514, (int)ChamsMode.getMaskFromEntity((Entity)event.getEntity()), (int)255);
            module.imageShader.bind();
            module.imageShader.set("sampler", 0);
            GL13.glActiveTexture((int)33990);
            int id = module.shouldGif((Entity)event.getEntity()) ? module.getGif((Entity)event.getEntity()).getDynamicTexture().getGlTextureId() : module.getImage((Entity)event.getEntity()).getTexture().getGlTextureId();
            GL11.glBindTexture((int)3553, (int)id);
            module.imageShader.set("overlaySampler", 6);
            GL13.glActiveTexture((int)33984);
            module.imageShader.set("mixFactor", module.getMixFactor((Entity)event.getEntity()));
            module.imageShader.set("colorMixFactor", module.getColorMixFactor((Entity)event.getEntity()));
            module.imageShader.set("dimensions", new Vec2f((float)7.mc.displayWidth, (float)7.mc.displayHeight));
            module.imageShader.set("inputColor", module.getColor((Entity)event.getEntity()));
            if (!module.shouldFit((Entity)event.getEntity())) {
                rect = null;
            }
            if (rect != null) {
                rect[0] = MathHelper.clamp((float)rect[0], (float)0.0f, (float)7.mc.displayWidth);
                rect[1] = MathHelper.clamp((float)rect[1], (float)0.0f, (float)7.mc.displayHeight);
                rect[2] = MathHelper.clamp((float)rect[2], (float)0.0f, (float)7.mc.displayWidth);
                rect[3] = MathHelper.clamp((float)rect[3], (float)0.0f, (float)7.mc.displayHeight);
                module.imageShader.set("imageX", rect[2] * (float)resolution.getScaleFactor());
                module.imageShader.set("imageY", (float)7.mc.displayHeight - rect[3] * (float)resolution.getScaleFactor() - (rect[1] - rect[3]) * (float)resolution.getScaleFactor());
                module.imageShader.set("imageWidth", (rect[0] - rect[2]) * (float)resolution.getScaleFactor());
                module.imageShader.set("imageHeight", (rect[1] - rect[3]) * (float)resolution.getScaleFactor());
            } else {
                module.imageShader.set("imageX", 0.0f);
                module.imageShader.set("imageY", 0.0f);
                module.imageShader.set("imageWidth", (float)7.mc.displayWidth);
                module.imageShader.set("imageHeight", (float)7.mc.displayHeight);
            }
            ChamsMode.render(event);
            module.imageShader.unbind();
            GL11.glStencilFunc((int)514, (int)ChamsMode.getWallsMaskFromEntity((Entity)event.getEntity()), (int)255);
            module.imageShader.bind();
            module.imageShader.set("sampler", 0);
            GL13.glActiveTexture((int)33990);
            int wallsId = module.shouldWallsGif((Entity)event.getEntity()) ? module.getWallsGif((Entity)event.getEntity()).getDynamicTexture().getGlTextureId() : module.getWallsImage((Entity)event.getEntity()).getTexture().getGlTextureId();
            GL11.glBindTexture((int)3553, (int)wallsId);
            module.imageShader.set("overlaySampler", 6);
            GL13.glActiveTexture((int)33984);
            module.imageShader.set("mixFactor", module.getMixFactor((Entity)event.getEntity()));
            module.imageShader.set("colorMixFactor", module.getColorMixFactor((Entity)event.getEntity()));
            module.imageShader.set("dimensions", new Vec2f((float)7.mc.displayWidth, (float)7.mc.displayHeight));
            module.imageShader.set("inputColor", module.getColor((Entity)event.getEntity()));
            if (!module.shouldFit((Entity)event.getEntity())) {
                rect = null;
            }
            if (rect != null) {
                rect[0] = MathHelper.clamp((float)rect[0], (float)0.0f, (float)7.mc.displayWidth);
                rect[1] = MathHelper.clamp((float)rect[1], (float)0.0f, (float)7.mc.displayHeight);
                rect[2] = MathHelper.clamp((float)rect[2], (float)0.0f, (float)7.mc.displayWidth);
                rect[3] = MathHelper.clamp((float)rect[3], (float)0.0f, (float)7.mc.displayHeight);
                module.imageShader.set("imageX", rect[2] * (float)resolution.getScaleFactor());
                module.imageShader.set("imageY", (float)7.mc.displayHeight - rect[3] * (float)resolution.getScaleFactor() - (rect[1] - rect[3]) * (float)resolution.getScaleFactor());
                module.imageShader.set("imageWidth", (rect[0] - rect[2]) * (float)resolution.getScaleFactor());
                module.imageShader.set("imageHeight", (rect[1] - rect[3]) * (float)resolution.getScaleFactor());
            } else {
                module.imageShader.set("imageX", 0.0f);
                module.imageShader.set("imageY", 0.0f);
                module.imageShader.set("imageWidth", (float)7.mc.displayWidth);
                module.imageShader.set("imageHeight", (float)7.mc.displayHeight);
            }
            ChamsMode.render(event);
            module.imageShader.unbind();
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3008);
            GL11.glDisable((int)3042);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }

        @Override
        public void beginRender(BeginRenderEvent event, Chams module) {
            OutlineUtil.checkSetupFBO();
            GL11.glClear((int)1024);
            GL11.glClearStencil((int)0);
        }

        @Override
        public void renderCrystalPre(CrystalRenderEvent.Pre event, Chams module) {
            ScaledResolution resolution = new ScaledResolution(mc);
            float[] rect = Render2DUtil.getOnScreen2DHitBox(event.getEntity(), resolution.getScaledWidth(), resolution.getScaledHeight());
            GL11.glPushMatrix();
            GL11.glPushAttrib((int)1048575);
            event.setCancelled(true);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glDisable((int)3008);
            GL11.glDisable((int)2896);
            if (module.crystalAlphaTest.getValue().booleanValue()) {
                GL11.glEnable((int)3008);
            }
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            if (module.crystalXQZ.getValue().booleanValue()) {
                GL11.glEnable((int)2960);
                GL11.glStencilOp((int)7680, (int)7681, (int)7680);
                GL11.glStencilFunc((int)517, (int)ChamsMode.getMaskFromEntity(event.getEntity()), (int)255);
                GL11.glStencilMask((int)ChamsMode.getWallsMaskFromEntity(event.getEntity()));
                ChamsMode.render(event);
            }
            GL11.glEnable((int)2960);
            GL11.glStencilOp((int)7680, (int)7680, (int)7681);
            GL11.glStencilFunc((int)519, (int)ChamsMode.getMaskFromEntity(event.getEntity()), (int)255);
            GL11.glStencilMask((int)255);
            ChamsMode.render(event);
            GL11.glStencilMask((int)0);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glStencilOp((int)7680, (int)7680, (int)7680);
            GL11.glStencilFunc((int)514, (int)ChamsMode.getMaskFromEntity(event.getEntity()), (int)255);
            module.imageShader.bind();
            module.imageShader.set("sampler", 0);
            GL13.glActiveTexture((int)33990);
            int id = module.shouldGif(event.getEntity()) ? module.getGif(event.getEntity()).getDynamicTexture().getGlTextureId() : module.getImage(event.getEntity()).getTexture().getGlTextureId();
            GL11.glBindTexture((int)3553, (int)id);
            module.imageShader.set("overlaySampler", 6);
            GL13.glActiveTexture((int)33984);
            module.imageShader.set("mixFactor", module.getMixFactor(event.getEntity()));
            module.imageShader.set("colorMixFactor", module.getColorMixFactor(event.getEntity()));
            module.imageShader.set("dimensions", new Vec2f((float)7.mc.displayWidth, (float)7.mc.displayHeight));
            module.imageShader.set("inputColor", module.getColor(event.getEntity()));
            if (!module.shouldFit(event.getEntity())) {
                rect = null;
            }
            if (rect != null) {
                rect[0] = MathHelper.clamp((float)rect[0], (float)0.0f, (float)7.mc.displayWidth);
                rect[1] = MathHelper.clamp((float)rect[1], (float)0.0f, (float)7.mc.displayHeight);
                rect[2] = MathHelper.clamp((float)rect[2], (float)0.0f, (float)7.mc.displayWidth);
                rect[3] = MathHelper.clamp((float)rect[3], (float)0.0f, (float)7.mc.displayHeight);
                module.imageShader.set("imageX", rect[2] * (float)resolution.getScaleFactor());
                module.imageShader.set("imageY", (float)7.mc.displayHeight - rect[3] * (float)resolution.getScaleFactor() - (rect[1] - rect[3]) * (float)resolution.getScaleFactor());
                module.imageShader.set("imageWidth", (rect[0] - rect[2]) * (float)resolution.getScaleFactor());
                module.imageShader.set("imageHeight", (rect[1] - rect[3]) * (float)resolution.getScaleFactor());
            } else {
                module.imageShader.set("imageX", 0.0f);
                module.imageShader.set("imageY", 0.0f);
                module.imageShader.set("imageWidth", (float)7.mc.displayWidth);
                module.imageShader.set("imageHeight", (float)7.mc.displayHeight);
            }
            ChamsMode.render(event);
            module.imageShader.unbind();
            GL11.glStencilFunc((int)514, (int)ChamsMode.getWallsMaskFromEntity(event.getEntity()), (int)255);
            module.imageShader.bind();
            module.imageShader.set("sampler", 0);
            GL13.glActiveTexture((int)33990);
            int wallsId = module.shouldWallsGif(event.getEntity()) ? module.getWallsGif(event.getEntity()).getDynamicTexture().getGlTextureId() : module.getWallsImage(event.getEntity()).getTexture().getGlTextureId();
            GL11.glBindTexture((int)3553, (int)wallsId);
            module.imageShader.set("overlaySampler", 6);
            GL13.glActiveTexture((int)33984);
            module.imageShader.set("mixFactor", module.getMixFactor(event.getEntity()));
            module.imageShader.set("colorMixFactor", module.getColorMixFactor(event.getEntity()));
            module.imageShader.set("dimensions", new Vec2f((float)7.mc.displayWidth, (float)7.mc.displayHeight));
            module.imageShader.set("inputColor", module.getColor(event.getEntity()));
            if (!module.shouldFit(event.getEntity())) {
                rect = null;
            }
            if (rect != null) {
                rect[0] = MathHelper.clamp((float)rect[0], (float)0.0f, (float)7.mc.displayWidth);
                rect[1] = MathHelper.clamp((float)rect[1], (float)0.0f, (float)7.mc.displayHeight);
                rect[2] = MathHelper.clamp((float)rect[2], (float)0.0f, (float)7.mc.displayWidth);
                rect[3] = MathHelper.clamp((float)rect[3], (float)0.0f, (float)7.mc.displayHeight);
                module.imageShader.set("imageX", rect[2] * (float)resolution.getScaleFactor());
                module.imageShader.set("imageY", (float)7.mc.displayHeight - rect[3] * (float)resolution.getScaleFactor() - (rect[1] - rect[3]) * (float)resolution.getScaleFactor());
                module.imageShader.set("imageWidth", (rect[0] - rect[2]) * (float)resolution.getScaleFactor());
                module.imageShader.set("imageHeight", (rect[1] - rect[3]) * (float)resolution.getScaleFactor());
            } else {
                module.imageShader.set("imageX", 0.0f);
                module.imageShader.set("imageY", 0.0f);
                module.imageShader.set("imageWidth", (float)7.mc.displayWidth);
                module.imageShader.set("imageHeight", (float)7.mc.displayHeight);
            }
            ChamsMode.render(event);
            module.imageShader.unbind();
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3008);
            GL11.glDisable((int)3042);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
    };
    public static final /* enum */ ChamsMode Image = new ChamsMode(){

        @Override
        public void renderPre(ModelRenderEvent.Pre event, Chams module) {
        }

        @Override
        public void renderCrystalPre(CrystalRenderEvent.Pre event, Chams module) {
        }

        @Override
        public void render3D(Render3DEvent event, Chams module) {
        }
    };
    private static final ResourceLocation ENCHANTED_ITEM_GLINT_RES;
    private static final ResourceLocation LIGHTNING_TEXTURE;
    private static final int CRYSTAL_WALLS = 4;
    private static final int CRYSTAL = 5;
    private static final int PLAYER_WALLS = 6;
    private static final int PLAYER = 7;
    private static final int FRIEND_WALLS = 8;
    private static final int FRIEND = 9;
    private static final int ENEMY_WALLS = 10;
    private static final int ENEMY = 11;
    private static final /* synthetic */ ChamsMode[] $VALUES;

    public static ChamsMode[] values() {
        return (ChamsMode[])$VALUES.clone();
    }

    public static ChamsMode valueOf(String name) {
        return Enum.valueOf(ChamsMode.class, name);
    }

    public void renderPre(ModelRenderEvent.Pre event, Chams module) {
    }

    public void renderPost(ModelRenderEvent.Post event, Chams module) {
    }

    public void renderCrystalPre(CrystalRenderEvent.Pre event, Chams module) {
    }

    public void renderCrystalPost(CrystalRenderEvent.Post event, Chams module) {
    }

    public void renderEntity(RenderEntityEvent.Pre event, Chams module) {
    }

    public void renderWorld(WorldRenderEvent event, Chams module) {
    }

    public void renderHud(PreRenderHandEvent event, Chams module) {
    }

    public void render3D(Render3DEvent event, Chams module) {
    }

    public void renderEntityPost(RenderEntityEvent.Post event, Chams module) {
    }

    public void render2D(Render2DEvent event, Chams module) {
    }

    public void beginRender(BeginRenderEvent event, Chams module) {
    }

    public void renderCrystalCube(RenderCrystalCubeEvent event, Chams module) {
    }

    public void renderArmor(RenderArmorEvent event, Chams module) {
    }

    private static void render(ModelRenderEvent.Pre event) {
        event.getModel().render((Entity)event.getEntity(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScale());
    }

    private static void render(ModelRenderEvent.Post event) {
        event.getModel().render((Entity)event.getEntity(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScale());
    }

    private static void render(CrystalRenderEvent.Pre event) {
        event.getModel().render(event.getEntity(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScale());
    }

    private static void render(CrystalRenderEvent.Post event) {
        event.getModel().render(event.getEntity(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScale());
    }

    private static void render(RenderEntityEvent.Pre event) {
        event.getRenderer().doRender(event.getEntity(), event.getPosX(), event.getPosY(), event.getPosZ(), event.getEntityYaw(), event.getPartialTicks());
    }

    private static int getMaskFromEntity(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? 9 : (Managers.ENEMIES.contains(entity) ? 11 : 7)) : (entity instanceof EntityEnderCrystal ? 5 : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? 0 : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? 0 : 0)));
    }

    private static int getWallsMaskFromEntity(Entity entity) {
        return entity instanceof EntityPlayer ? (Managers.FRIENDS.contains(entity) ? 8 : (Managers.ENEMIES.contains(entity) ? 10 : 6)) : (entity instanceof EntityEnderCrystal ? 4 : (EntityType.isAngry(entity) || EntityType.isAnimal(entity) ? 0 : (EntityType.isMonster(entity) || EntityType.isBoss(entity) ? 0 : 0)));
    }

    private static int getMaskFromEntityType(ChamsPage entity) {
        switch (entity) {
            case Players: {
                return 7;
            }
            case Friends: {
                return 9;
            }
            case Enemies: {
                return 11;
            }
            case Crystals: {
                return 5;
            }
        }
        return -1;
    }

    private static int getWallsMaskFromEntityType(ChamsPage entity) {
        switch (entity) {
            case Players: {
                return 6;
            }
            case Friends: {
                return 8;
            }
            case Enemies: {
                return 10;
            }
            case Crystals: {
                return 4;
            }
        }
        return -1;
    }

    private static void renderEntities(double renderPosX, double renderPosY, double renderPosZ) {
        for (Entity e : ChamsMode.mc.world.loadedEntityList) {
            if (!(e instanceof EntityEnderCrystal) || e == mc.getRenderViewEntity()) continue;
            if (e.ticksExisted == 0) {
                e.lastTickPosX = e.posX;
                e.lastTickPosY = e.posY;
                e.lastTickPosZ = e.posZ;
            }
            double d0 = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)mc.getRenderPartialTicks();
            double d1 = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)mc.getRenderPartialTicks();
            double d2 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)mc.getRenderPartialTicks();
            double f = e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * mc.getRenderPartialTicks();
            mc.getRenderManager().renderEntity(e, d0 - renderPosX, d1 - renderPosY, d2 - renderPosZ, (float)f, mc.getRenderPartialTicks(), true);
        }
    }

    private static void renderWireframe(ModelRenderEvent.Pre event, Color color, boolean xqz, Color wallsColor, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)lineWidth);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2960);
        GL11.glEnable((int)10754);
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        GL11.glPolygonMode((int)1032, (int)6913);
        GL11.glColor4f((float)((float)wallsColor.getRed() / 255.0f), (float)((float)wallsColor.getGreen() / 255.0f), (float)((float)wallsColor.getBlue() / 255.0f), (float)((float)wallsColor.getAlpha() / 255.0f));
        ChamsMode.render(event);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        if (xqz) {
            GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
            ChamsMode.render(event);
        }
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2896);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    private static void renderWireframe(ModelRenderEvent.Post event, Chams module, Color color, boolean xqz, Color wallsColor, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)lineWidth);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2960);
        GL11.glEnable((int)10754);
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        GL11.glPolygonMode((int)1032, (int)6913);
        GL11.glColor4f((float)((float)wallsColor.getRed() / 255.0f), (float)((float)wallsColor.getGreen() / 255.0f), (float)((float)wallsColor.getBlue() / 255.0f), (float)((float)wallsColor.getAlpha() / 255.0f));
        ChamsMode.render(event);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        if (xqz) {
            GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
            ChamsMode.render(event);
        }
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2896);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    private static void renderGlint(ModelRenderEvent.Pre event, Chams module, Color color) {
        ChamsMode.renderEnchantEffect(event.getEntity(), event.getModel(), event.getLimbSwing(), event.getLimbSwingAmount(), mc.getRenderPartialTicks(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScale(), 1.0f, color);
    }

    private static void renderGlint(ModelRenderEvent.Post event, Chams module, Color color) {
        ChamsMode.renderEnchantEffect(event.getEntity(), event.getModel(), event.getLimbSwing(), event.getLimbSwingAmount(), mc.getRenderPartialTicks(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScale(), 1.0f, color);
    }

    private static void renderEnchantEffect(EntityLivingBase p_188364_1_, ModelBase model, float p_188364_3_, float p_188364_4_, float p_188364_5_, float p_188364_6_, float p_188364_7_, float p_188364_8_, float p_188364_9_, float glintScale, Color color) {
        float f3 = (float)p_188364_1_.ticksExisted + p_188364_5_;
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(ENCHANTED_ITEM_GLINT_RES);
        float f4 = MathHelper.sin((float)(f3 * 0.2f)) / 2.0f + 0.5f;
        f4 += f4 * f4;
        GL11.glPushAttrib((int)1048575);
        GL11.glPolygonMode((int)1032, (int)6914);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_COLOR, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
        for (int i = 0; i < 2; ++i) {
            GlStateManager.matrixMode((int)5890);
            GlStateManager.loadIdentity();
            float tScale = 0.33333334f;
            GL11.glScalef((float)glintScale, (float)glintScale, (float)glintScale);
            GlStateManager.rotate((float)(30.0f - (float)i * 60.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.translate((float)0.0f, (float)(f3 * (0.001f + (float)i * 0.003f) * 20.0f), (float)0.0f);
            GlStateManager.matrixMode((int)5888);
            model.render((Entity)p_188364_1_, p_188364_3_, p_188364_4_, p_188364_6_, p_188364_7_, p_188364_8_, p_188364_9_);
        }
        GlStateManager.matrixMode((int)5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode((int)5888);
        GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    private static void renderLightning(ModelRenderEvent.Pre event, Chams module) {
    }

    private static void renderLightning(ModelRenderEvent.Post event, Chams module) {
    }

    private static void renderLightning(ModelBase modelBase, EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, Color color) {
        Render render = mc.getRenderManager().getEntityRenderObject((Entity)entitylivingbaseIn);
        RenderLivingBase renderLivingBase = (RenderLivingBase)render;
        assert (renderLivingBase != null);
        boolean flag = entitylivingbaseIn.isInvisible();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask((!flag ? 1 : 0) != 0);
        mc.getTextureManager().bindTexture(LIGHTNING_TEXTURE);
        GlStateManager.matrixMode((int)5890);
        GlStateManager.loadIdentity();
        float f = (float)entitylivingbaseIn.ticksExisted + partialTicks;
        GlStateManager.translate((float)(f * 0.01f), (float)(f * 0.01f), (float)0.0f);
        GlStateManager.matrixMode((int)5888);
        GlStateManager.enableBlend();
        float f1 = 0.5f;
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GlStateManager.disableLighting();
        GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
        modelBase.setModelAttributes(renderLivingBase.getMainModel());
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        modelBase.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        GlStateManager.matrixMode((int)5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode((int)5888);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.depthMask((boolean)flag);
    }

    private static void renderLightning(ModelBase modelBase, EntityEnderCrystal entityEnderCrystal, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, Color color) {
        Render render = mc.getRenderManager().getEntityRenderObject((Entity)entityEnderCrystal);
        RenderEnderCrystal renderLivingBase = (RenderEnderCrystal)render;
        assert (renderLivingBase != null);
        boolean flag = entityEnderCrystal.isInvisible();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask((!flag ? 1 : 0) != 0);
        mc.getTextureManager().bindTexture(LIGHTNING_TEXTURE);
        GlStateManager.matrixMode((int)5890);
        GlStateManager.loadIdentity();
        float f = (float)entityEnderCrystal.ticksExisted + partialTicks;
        GlStateManager.translate((float)(f * 0.01f), (float)(f * 0.01f), (float)0.0f);
        GlStateManager.matrixMode((int)5888);
        GlStateManager.enableBlend();
        float f1 = 0.5f;
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        GlStateManager.disableLighting();
        GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
        modelBase.setModelAttributes(((IRenderEnderCrystal)renderLivingBase).getModelEnderCrystal());
        Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
        modelBase.render((Entity)entityEnderCrystal, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
        GlStateManager.matrixMode((int)5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode((int)5888);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.depthMask((boolean)flag);
    }

    static {
        $VALUES = new ChamsMode[]{None, Normal, Better, JelloBottom, JelloTop, Shader, FramebufferImage, FramebufferTest, ShaderImage, Image};
        ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        LIGHTNING_TEXTURE = new ResourceLocation("earthhack:textures/client/lightning.png");
    }
}

