/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL13
 *  org.lwjgl.opengl.GL20
 */
package me.earth.earthhack.impl.util.render;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.render.itemchams.ItemChams;
import me.earth.earthhack.impl.util.render.FramebufferShader;
import me.earth.earthhack.impl.util.render.image.EfficientTexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class ItemShader
extends FramebufferShader {
    private static final ModuleCache<ItemChams> ITEM_CHAMS = Caches.getModule(ItemChams.class);
    public boolean blur;
    public float mix = 0.0f;
    public float alpha = 1.0f;
    public float imageMix = 0.0f;
    public boolean useImage;
    public boolean rotate;
    public static final ItemShader ITEM_SHADER = new ItemShader();

    public ItemShader() {
        super("itemglow.frag");
    }

    @Override
    public void setupUniforms() {
        this.setupUniform("texture");
        this.setupUniform("texelSize");
        this.setupUniform("color");
        this.setupUniform("divider");
        this.setupUniform("radius");
        this.setupUniform("maxSample");
        this.setupUniform("dimensions");
        this.setupUniform("blur");
        this.setupUniform("mixFactor");
        this.setupUniform("minAlpha");
        this.setupUniform("image");
        this.setupUniform("imageMix");
        this.setupUniform("useImage");
    }

    @Override
    public void updateUniforms() {
        GL20.glUniform1i((int)this.getUniform("texture"), (int)0);
        GL20.glUniform2f((int)this.getUniform("texelSize"), (float)(1.0f / (float)ItemShader.mc.displayWidth * (this.radius * this.quality)), (float)(1.0f / (float)ItemShader.mc.displayHeight * (this.radius * this.quality)));
        GL20.glUniform3f((int)this.getUniform("color"), (float)this.red, (float)this.green, (float)this.blue);
        GL20.glUniform1f((int)this.getUniform("divider"), (float)140.0f);
        GL20.glUniform1f((int)this.getUniform("radius"), (float)this.radius);
        GL20.glUniform1f((int)this.getUniform("maxSample"), (float)10.0f);
        GL20.glUniform2f((int)this.getUniform("dimensions"), (float)ItemShader.mc.displayWidth, (float)ItemShader.mc.displayHeight);
        GL20.glUniform1i((int)this.getUniform("blur"), (int)(this.blur ? 1 : 0));
        GL20.glUniform1f((int)this.getUniform("mixFactor"), (float)this.mix);
        GL20.glUniform1f((int)this.getUniform("minAlpha"), (float)this.alpha);
        GL13.glActiveTexture((int)33992);
        if (((ItemChams)ItemShader.ITEM_CHAMS.get()).useGif.getValue().booleanValue()) {
            EfficientTexture texture = ((ItemChams)ItemShader.ITEM_CHAMS.get()).gif.getValue().getDynamicTexture();
            GL11.glBindTexture((int)3553, (int)(texture != null ? texture.getGlTextureId() : 0));
        } else {
            GL11.glBindTexture((int)3553, (int)(((ItemChams)ItemShader.ITEM_CHAMS.get()).image.getValue().getTexture() != null ? ((ItemChams)ItemShader.ITEM_CHAMS.get()).image.getValue().getTexture().getGlTextureId() : 0));
        }
        GL20.glUniform1i((int)this.getUniform("image"), (int)8);
        GL13.glActiveTexture((int)33984);
        GL20.glUniform1f((int)this.getUniform("imageMix"), (float)this.imageMix);
        GL20.glUniform1i((int)this.getUniform("useImage"), (int)(this.useImage ? 1 : 0));
    }
}

