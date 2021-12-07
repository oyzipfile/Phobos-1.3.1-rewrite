/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.itemchams;

import java.awt.Color;
import me.earth.earthhack.api.module.Module;
import me.earth.earthhack.api.module.util.Category;
import me.earth.earthhack.api.setting.Setting;
import me.earth.earthhack.api.setting.settings.BooleanSetting;
import me.earth.earthhack.api.setting.settings.ColorSetting;
import me.earth.earthhack.api.setting.settings.EnumSetting;
import me.earth.earthhack.api.setting.settings.ListSetting;
import me.earth.earthhack.api.setting.settings.NumberSetting;
import me.earth.earthhack.impl.gui.visibility.PageBuilder;
import me.earth.earthhack.impl.gui.visibility.Visibilities;
import me.earth.earthhack.impl.managers.Managers;
import me.earth.earthhack.impl.modules.render.itemchams.ItemChamsPage;
import me.earth.earthhack.impl.modules.render.itemchams.ListenerRenderItemPre;
import me.earth.earthhack.impl.modules.render.itemchams.ListenerRenderWorld;
import me.earth.earthhack.impl.util.render.GlShader;
import me.earth.earthhack.impl.util.render.image.GifImage;
import me.earth.earthhack.impl.util.render.image.NameableImage;
import me.earth.earthhack.impl.util.render.shader.FramebufferWrapper;

public class ItemChams
extends Module {
    protected final Setting<ItemChamsPage> page = this.register(new EnumSetting<ItemChamsPage>("Page", ItemChamsPage.Glint));
    protected final Setting<Boolean> glint = this.register(new BooleanSetting("ModifyGlint", false));
    protected final Setting<Float> scale = this.register(new NumberSetting<Float>("GlintScale", Float.valueOf(8.0f), Float.valueOf(0.1f), Float.valueOf(20.0f)));
    protected final Setting<Float> glintMult = this.register(new NumberSetting<Float>("GlintMultiplier", Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(10.0f)));
    protected final Setting<Float> glintRotate = this.register(new NumberSetting<Float>("GlintRotate", Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(10.0f)));
    protected final Setting<Color> glintColor = this.register(new ColorSetting("GlintColor", Color.RED));
    protected final Setting<Boolean> chams = this.register(new BooleanSetting("Chams", false));
    protected final Setting<Boolean> blur = this.register(new BooleanSetting("Blur", false));
    protected final Setting<Float> radius = this.register(new NumberSetting<Float>("Radius", Float.valueOf(2.0f), Float.valueOf(0.1f), Float.valueOf(10.0f)));
    protected final Setting<Float> mix = this.register(new NumberSetting<Float>("Mix", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    protected final Setting<Boolean> useImage = this.register(new BooleanSetting("UseImage", false));
    public final Setting<Boolean> useGif = this.register(new BooleanSetting("UseGif", false));
    public final Setting<GifImage> gif = this.register(new ListSetting<GifImage>("Gif", Managers.FILES.getInitialGif(), Managers.FILES.getGifs()));
    public final Setting<NameableImage> image = this.register(new ListSetting<NameableImage>("Image", Managers.FILES.getInitialImage(), Managers.FILES.getImages()));
    protected final Setting<Float> imageMix = this.register(new NumberSetting<Float>("ImageMix", Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    protected final Setting<Boolean> rotate = this.register(new BooleanSetting("Rotate", false));
    protected final Setting<Color> chamColor = this.register(new ColorSetting("Color", Color.RED));
    protected final GlShader shader = new GlShader("item");
    protected final FramebufferWrapper wrapper = new FramebufferWrapper();
    protected boolean forceRender = false;

    public ItemChams() {
        super("ItemChams", Category.Render);
        this.listeners.add(new ListenerRenderItemPre(this));
        this.listeners.add(new ListenerRenderWorld(this));
        new PageBuilder<ItemChamsPage>(this, this.page).addPage(p -> p == ItemChamsPage.Glint, (Setting<?>)this.glint, (Setting<?>)this.glintColor).addPage(p -> p == ItemChamsPage.Chams, (Setting<?>)this.chams, (Setting<?>)this.chamColor).register(Visibilities.VISIBILITY_MANAGER);
    }

    public boolean isModifyingGlint() {
        return this.glint.getValue();
    }

    public Color getGlintColor() {
        return this.glintColor.getValue();
    }

    public float getScale() {
        return this.scale.getValue().floatValue();
    }

    public float getFactor() {
        return this.glintMult.getValue().floatValue();
    }

    public float getGlintRotate() {
        return this.glintRotate.getValue().floatValue();
    }
}

