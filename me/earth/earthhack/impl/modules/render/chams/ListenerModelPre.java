/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.ActiveRenderInfo
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec2f
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL13
 *  org.lwjgl.util.vector.Vector4f
 */
package me.earth.earthhack.impl.modules.render.chams;

import java.awt.Color;
import me.earth.earthhack.impl.core.mixins.render.entity.IEntityRenderer;
import me.earth.earthhack.impl.event.events.render.ModelRenderEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.chams.Chams;
import me.earth.earthhack.impl.modules.render.chams.mode.ChamsMode;
import me.earth.earthhack.impl.modules.render.esp.ESP;
import me.earth.earthhack.impl.util.math.Vec2d;
import me.earth.earthhack.impl.util.render.Render2DUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector4f;

final class ListenerModelPre
extends ModuleListener<Chams, ModelRenderEvent.Pre> {
    public ListenerModelPre(Chams module) {
        super(module, ModelRenderEvent.Pre.class);
    }

    @Override
    public void invoke(ModelRenderEvent.Pre event) {
        Color color;
        EntityLivingBase entity;
        if (!ESP.isRendering && ((Chams)this.module).mode.getValue() == ChamsMode.CSGO) {
            entity = event.getEntity();
            if (((Chams)this.module).isValid((Entity)entity)) {
                event.setCancelled(true);
                boolean lightning = GL11.glIsEnabled((int)2896);
                boolean blend = GL11.glIsEnabled((int)3042);
                GL11.glPushAttrib((int)1048575);
                GL11.glDisable((int)3008);
                if (!((Chams)this.module).texture.getValue().booleanValue()) {
                    GL11.glDisable((int)3553);
                }
                if (lightning) {
                    GL11.glDisable((int)2896);
                }
                if (!blend) {
                    GL11.glEnable((int)3042);
                }
                GL11.glBlendFunc((int)770, (int)771);
                if (((Chams)this.module).xqz.getValue().booleanValue()) {
                    GL11.glColor4f((float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glDepthMask((boolean)false);
                    GL11.glDisable((int)2929);
                    OpenGlHelper.setLightmapTextureCoords((int)OpenGlHelper.lightmapTexUnit, (float)240.0f, (float)240.0f);
                    this.render(event);
                }
                GL11.glDisable((int)3042);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)2896);
                if (!((Chams)this.module).texture.getValue().booleanValue()) {
                    GL11.glEnable((int)3553);
                }
                GL11.glEnable((int)3008);
                GL11.glPopAttrib();
                GL11.glPushAttrib((int)1048575);
                GL11.glDisable((int)3008);
                if (!((Chams)this.module).texture.getValue().booleanValue()) {
                    GL11.glDisable((int)3553);
                }
                GL11.glDisable((int)2896);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glColor4f((float)0.0f, (float)1.0f, (float)0.0f, (float)1.0f);
                OpenGlHelper.setLightmapTextureCoords((int)OpenGlHelper.lightmapTexUnit, (float)240.0f, (float)240.0f);
                this.render(event);
                if (!blend) {
                    GL11.glDisable((int)3042);
                }
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                if (lightning) {
                    GL11.glEnable((int)2896);
                }
                if (!((Chams)this.module).texture.getValue().booleanValue()) {
                    GL11.glEnable((int)3553);
                }
                GL11.glEnable((int)3008);
                GL11.glPopAttrib();
            }
        } else if (!ESP.isRendering && ((Chams)this.module).mode.getValue() == ChamsMode.Better && event.getEntity() instanceof EntityPlayer) {
            event.setCancelled(true);
            color = ((Chams)this.module).getVisibleColor((Entity)event.getEntity());
            Color wallsColor = ((Chams)this.module).getWallsColor((Entity)event.getEntity());
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
            this.render(event);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
            if (((Chams)this.module).xqz.getValue().booleanValue()) {
                GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
                this.render(event);
            }
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2896);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3008);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        } else if (!ESP.isRendering && ((Chams)this.module).isValid((Entity)(entity = event.getEntity())) && ((Chams)this.module).mode.getValue() == ChamsMode.JelloBottom) {
            event.setCancelled(true);
            this.render(event);
            Color color2 = ((Chams)this.module).getVisibleColor((Entity)event.getEntity());
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
            GL11.glColor4f((float)((float)color2.getRed() / 255.0f), (float)((float)color2.getGreen() / 255.0f), (float)((float)color2.getBlue() / 255.0f), (float)((float)color2.getAlpha() / 255.0f));
            this.render(event);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2896);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3008);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
        if (((Chams)this.module).mode.getValue() == ChamsMode.FireShader && !ESP.isRendering && ((Chams)this.module).fireShader != null) {
            if (!((Chams)this.module).isValid((Entity)event.getEntity())) {
                return;
            }
            event.setCancelled(true);
            GL11.glPushAttrib((int)1048575);
            GL11.glPushMatrix();
            color = ((Chams)this.module).getVisibleColor((Entity)event.getEntity());
            ((Chams)this.module).fireShader.bind();
            ((Chams)this.module).fireShader.set("time", (float)(System.currentTimeMillis() - ((Chams)this.module).initTime) / 2000.0f);
            ((Chams)this.module).fireShader.set("resolution", new Vec2f((float)(ListenerModelPre.mc.displayWidth * 2), (float)(ListenerModelPre.mc.displayHeight * 2)));
            ((Chams)this.module).fireShader.set("tex", 0);
            GlStateManager.pushMatrix();
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)color.getAlpha());
            ((Chams)this.module).fireShader.set("alpha", (float)color.getAlpha() / 255.0f);
            GL11.glEnable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)-2000000.0f);
            this.render(event);
            GL11.glDisable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)2000000.0f);
            GlStateManager.popMatrix();
            ((Chams)this.module).fireShader.unbind();
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
        if (((Chams)this.module).mode.getValue() == ChamsMode.GalaxyShader && !ESP.isRendering && ((Chams)this.module).galaxyShader != null) {
            if (!((Chams)this.module).isValid((Entity)event.getEntity())) {
                return;
            }
            event.setCancelled(true);
            GL11.glPushAttrib((int)1048575);
            GL11.glPushMatrix();
            color = ((Chams)this.module).getVisibleColor((Entity)event.getEntity());
            ((Chams)this.module).galaxyShader.bind();
            ((Chams)this.module).galaxyShader.set("time", (float)(System.currentTimeMillis() - ((Chams)this.module).initTime) / 2000.0f);
            ((Chams)this.module).galaxyShader.set("resolution", new Vec2f((float)(ListenerModelPre.mc.displayWidth * 2), (float)(ListenerModelPre.mc.displayHeight * 2)));
            ((Chams)this.module).galaxyShader.set("tex", 0);
            GlStateManager.pushMatrix();
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)color.getAlpha());
            ((Chams)this.module).galaxyShader.set("alpha", (float)color.getAlpha() / 255.0f);
            GL11.glEnable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)-2000000.0f);
            this.render(event);
            GL11.glDisable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)2000000.0f);
            GlStateManager.popMatrix();
            ((Chams)this.module).galaxyShader.unbind();
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
        if (((Chams)this.module).mode.getValue() == ChamsMode.WaterShader && !ESP.isRendering && ((Chams)this.module).waterShader != null) {
            if (!((Chams)this.module).isValid((Entity)event.getEntity())) {
                return;
            }
            event.setCancelled(true);
            GL11.glPushAttrib((int)1048575);
            GL11.glPushMatrix();
            color = ((Chams)this.module).getVisibleColor((Entity)event.getEntity());
            ((Chams)this.module).waterShader.bind();
            ((Chams)this.module).waterShader.set("time", (float)(System.currentTimeMillis() - ((Chams)this.module).initTime) / 2000.0f);
            ((Chams)this.module).waterShader.set("resolution", new Vec2f((float)(ListenerModelPre.mc.displayWidth * 2), (float)(ListenerModelPre.mc.displayHeight * 2)));
            ((Chams)this.module).waterShader.set("tex", 0);
            GlStateManager.pushMatrix();
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)color.getAlpha());
            ((Chams)this.module).waterShader.set("alpha", (float)color.getAlpha() / 255.0f);
            GL11.glEnable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)-2000000.0f);
            this.render(event);
            GL11.glDisable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)2000000.0f);
            GlStateManager.popMatrix();
            ((Chams)this.module).waterShader.unbind();
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
        if (((Chams)this.module).mode.getValue() == ChamsMode.Image && ((Chams)this.module).isValid((Entity)event.getEntity()) && ((Chams)this.module).imageShader != null) {
            ScaledResolution resolution = new ScaledResolution(mc);
            float[] rect = Render2DUtil.getOnScreen2DHitBox((Entity)event.getEntity(), resolution.getScaledWidth(), resolution.getScaledHeight());
            GL11.glPushMatrix();
            GL11.glPushAttrib((int)1048575);
            event.setCancelled(true);
            ((Chams)this.module).imageShader.bind();
            int currentTexture = GL11.glGetInteger((int)32873);
            ((Chams)this.module).imageShader.set("sampler", 0);
            GL13.glActiveTexture((int)33990);
            if (!((Chams)this.module).gif && ((Chams)this.module).dynamicTexture != null) {
                GL11.glBindTexture((int)3553, (int)((Chams)this.module).dynamicTexture.getGlTextureId());
            }
            ((Chams)this.module).imageShader.set("overlaySampler", 6);
            GL13.glActiveTexture((int)33984);
            ((Chams)this.module).imageShader.set("mixFactor", ((Chams)this.module).mixFactor.getValue().floatValue());
            ((Chams)this.module).imageShader.set("alpha", (float)((Chams)this.module).color.getValue().getAlpha() / 255.0f);
            Vec3d gl_FragCoord = new Vec3d(1920.0, 1080.0, 0.0);
            Vector4f imageDimensions = new Vector4f(0.0f, 0.0f, 1920.0f, 1080.0f);
            Vec2d d = new Vec2d((gl_FragCoord.x - (double)imageDimensions.x) / (double)imageDimensions.z, (gl_FragCoord.y - (double)imageDimensions.y) / (double)imageDimensions.w);
            rect = null;
            if (rect != null) {
                rect[0] = MathHelper.clamp((float)rect[0], (float)0.0f, (float)ListenerModelPre.mc.displayWidth);
                rect[1] = MathHelper.clamp((float)rect[1], (float)0.0f, (float)ListenerModelPre.mc.displayHeight);
                rect[2] = MathHelper.clamp((float)rect[2], (float)0.0f, (float)ListenerModelPre.mc.displayWidth);
                rect[3] = MathHelper.clamp((float)rect[3], (float)0.0f, (float)ListenerModelPre.mc.displayHeight);
                ((Chams)this.module).imageShader.set("imageX", rect[2]);
                ((Chams)this.module).imageShader.set("imageY", rect[3]);
                ((Chams)this.module).imageShader.set("imageWidth", rect[0] - rect[2]);
                ((Chams)this.module).imageShader.set("imageHeight", rect[1] - rect[3]);
            } else {
                ((Chams)this.module).imageShader.set("imageX", 0.0f);
                ((Chams)this.module).imageShader.set("imageY", 0.0f);
                ((Chams)this.module).imageShader.set("imageWidth", (float)ListenerModelPre.mc.displayWidth);
                ((Chams)this.module).imageShader.set("imageHeight", (float)ListenerModelPre.mc.displayHeight);
            }
            boolean shadows = ListenerModelPre.mc.gameSettings.entityShadows;
            ListenerModelPre.mc.gameSettings.entityShadows = false;
            ((Chams)this.module).renderLayers = false;
            this.render(event);
            ((Chams)this.module).renderLayers = true;
            ((Chams)this.module).imageShader.unbind();
            ListenerModelPre.mc.gameSettings.entityShadows = shadows;
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
    }

    private void render(ModelRenderEvent.Pre event) {
        event.getModel().render((Entity)event.getEntity(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScale());
    }

    private float getFOVModifier(float partialTicks, boolean useFOVSetting) {
        IBlockState iblockstate;
        if (((IEntityRenderer)ListenerModelPre.mc.entityRenderer).isDebugView()) {
            return 90.0f;
        }
        Entity entity = mc.getRenderViewEntity();
        float f = 70.0f;
        if (useFOVSetting) {
            f = ListenerModelPre.mc.gameSettings.fovSetting;
            f *= ((IEntityRenderer)ListenerModelPre.mc.entityRenderer).getFovModifierHandPrev() + (((IEntityRenderer)ListenerModelPre.mc.entityRenderer).getFovModifierHand() - ((IEntityRenderer)ListenerModelPre.mc.entityRenderer).getFovModifierHandPrev()) * partialTicks;
        }
        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0f) {
            float f1 = (float)((EntityLivingBase)entity).deathTime + partialTicks;
            f /= (1.0f - 500.0f / (f1 + 500.0f)) * 2.0f + 1.0f;
        }
        if ((iblockstate = ActiveRenderInfo.getBlockStateAtEntityViewpoint((World)ListenerModelPre.mc.world, (Entity)entity, (float)partialTicks)).getMaterial() == Material.WATER) {
            f = f * 60.0f / 70.0f;
        }
        return f;
    }
}

