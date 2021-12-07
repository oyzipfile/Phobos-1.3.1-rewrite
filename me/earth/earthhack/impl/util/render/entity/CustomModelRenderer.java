/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 */
package me.earth.earthhack.impl.util.render.entity;

import java.util.ArrayList;
import java.util.List;
import me.earth.earthhack.impl.util.render.entity.BoxRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class CustomModelRenderer {
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    public boolean isHidden;
    public boolean showModel = true;
    private List<BoxRenderer> renderers = new ArrayList<BoxRenderer>();
    private List<CustomModelRenderer> children = new ArrayList<CustomModelRenderer>();

    public void renderWithScale(float scale) {
        if (!this.isHidden && this.showModel) {
            GlStateManager.translate((float)this.offsetX, (float)this.offsetY, (float)this.offsetZ);
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX == 0.0f && this.rotationPointY == 0.0f && this.rotationPointZ == 0.0f) {
                    this.doRender(scale);
                    for (CustomModelRenderer child : this.children) {
                        child.renderWithScale(scale);
                    }
                } else {
                    GlStateManager.translate((float)(this.rotationPointX * scale), (float)(this.rotationPointY * scale), (float)(this.rotationPointZ * scale));
                    this.doRender(scale);
                    for (CustomModelRenderer child : this.children) {
                        child.renderWithScale(scale);
                    }
                    GlStateManager.translate((float)(-this.rotationPointX * scale), (float)(-this.rotationPointY * scale), (float)(-this.rotationPointZ * scale));
                }
            } else {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)(this.rotationPointX * scale), (float)(this.rotationPointY * scale), (float)(this.rotationPointZ * scale));
                if (this.rotateAngleZ != 0.0f) {
                    GlStateManager.rotate((float)(this.rotateAngleZ * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GlStateManager.rotate((float)(this.rotateAngleY * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GlStateManager.rotate((float)(this.rotateAngleX * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
                }
                this.doRender(scale);
                for (CustomModelRenderer child : this.children) {
                    child.renderWithScale(scale);
                }
                GlStateManager.popMatrix();
            }
            GlStateManager.translate((float)(-this.offsetX), (float)(-this.offsetY), (float)(-this.offsetZ));
        }
    }

    public void renderWithRotation(float scale) {
        if (!this.isHidden && this.showModel) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(this.rotationPointX * scale), (float)(this.rotationPointY * scale), (float)(this.rotationPointZ * scale));
            if (this.rotateAngleY != 0.0f) {
                GlStateManager.rotate((float)(this.rotateAngleY * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (this.rotateAngleX != 0.0f) {
                GlStateManager.rotate((float)(this.rotateAngleX * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
            }
            if (this.rotateAngleZ != 0.0f) {
                GlStateManager.rotate((float)(this.rotateAngleZ * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
            }
            this.doRender(scale);
            GlStateManager.popMatrix();
        }
    }

    public void postRender(float scale) {
        if (!this.isHidden && this.showModel) {
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX != 0.0f || this.rotationPointY != 0.0f || this.rotationPointZ != 0.0f) {
                    GlStateManager.translate((float)(this.rotationPointX * scale), (float)(this.rotationPointY * scale), (float)(this.rotationPointZ * scale));
                }
            } else {
                GlStateManager.translate((float)(this.rotationPointX * scale), (float)(this.rotationPointY * scale), (float)(this.rotationPointZ * scale));
                if (this.rotateAngleZ != 0.0f) {
                    GlStateManager.rotate((float)(this.rotateAngleZ * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GlStateManager.rotate((float)(this.rotateAngleY * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GlStateManager.rotate((float)(this.rotateAngleX * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
                }
            }
        }
    }

    public void doRender(float scale) {
        for (BoxRenderer renderer : this.renderers) {
            renderer.render(scale);
        }
    }

    public void addBox(float offX, float offY, float offZ, float width, float height, float depth, float delta) {
    }
}

