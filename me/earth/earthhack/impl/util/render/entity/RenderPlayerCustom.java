/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.renderer.entity.RenderLivingBase
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.entity.layers.LayerRenderer
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package me.earth.earthhack.impl.util.render.entity;

import java.util.ArrayList;
import javax.annotation.Nullable;
import me.earth.earthhack.impl.util.render.entity.CustomModelRenderer;
import me.earth.earthhack.impl.util.render.entity.IRenderable;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderPlayerCustom
extends RenderLivingBase<AbstractClientPlayer> {
    private IRenderable modelRenderer;
    private final ArrayList<LayerRenderer<AbstractClientPlayer>> renderers = new ArrayList();

    protected RenderPlayerCustom(RenderManager renderManager, CustomModelRenderer renderer) {
        super(renderManager, null, 0.0f);
    }

    public void setModelRenderer(IRenderable modelRenderer) {
        this.modelRenderer = modelRenderer;
    }

    public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender((EntityLivingBase)entity, x, y, z, entityYaw, partialTicks);
        GL11.glPushMatrix();
        GL11.glPushAttrib((int)1048575);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        this.modelRenderer.render(partialTicks);
        GL11.glPopAttrib();
        GL11.glPushAttrib((int)1048575);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2896);
        for (LayerRenderer<AbstractClientPlayer> layerRenderer : this.renderers) {
        }
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    @Nullable
    protected ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
        return null;
    }

    protected boolean setBrightness(AbstractClientPlayer entitylivingbaseIn, float partialTicks, boolean combineTextures) {
        return false;
    }

    public void unsetBrightness() {
    }
}

