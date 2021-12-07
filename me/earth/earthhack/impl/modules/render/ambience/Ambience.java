/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.ambience;

import java.awt.Color;
import java.lang.reflect.Field;
import me.earth.earthhack.api.module.Module;
import me.earth.earthhack.api.module.util.Category;
import me.earth.earthhack.api.setting.Setting;
import me.earth.earthhack.api.setting.settings.BooleanSetting;
import me.earth.earthhack.api.setting.settings.ColorSetting;
import me.earth.earthhack.api.setting.settings.NumberSetting;
import me.earth.earthhack.impl.core.ducks.entity.IEntityRenderer;
import me.earth.earthhack.impl.util.render.WorldRenderUtil;
import me.earth.earthhack.vanilla.Environment;

public class Ambience
extends Module {
    protected final Setting<Color> color = this.register(new ColorSetting("Color", new Color(255, 255, 255, 255)));
    protected final Setting<Boolean> useSaturation = this.register(new BooleanSetting("UseSaturation", false));
    protected final Setting<Float> saturation = this.register(new NumberSetting<Float>("Saturation", Float.valueOf(0.5f), Float.valueOf(0.0f), Float.valueOf(1.0f)));
    protected boolean lightPipeLine;

    public Ambience() {
        super("Ambience", Category.Render);
        this.color.addObserver(setting -> this.loadRenderers());
        if (Environment.hasForge()) {
            try {
                Field field = Class.forName("net.minecraftforge.common.ForgeModContainer", true, this.getClass().getClassLoader()).getDeclaredField("forgeLightPipelineEnabled");
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                this.lightPipeLine = field.getBoolean(null);
                field.setAccessible(accessible);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Color getColor() {
        return new Color(this.color.getValue().getRed(), this.color.getValue().getGreen(), this.color.getValue().getBlue(), this.color.getValue().getAlpha());
    }

    public boolean useSaturation() {
        return this.useSaturation.getValue();
    }

    public float getSaturation() {
        return this.saturation.getValue().floatValue();
    }

    @Override
    protected void onEnable() {
        if (Environment.hasForge()) {
            try {
                Field field = Class.forName("net.minecraftforge.common.ForgeModContainer", true, this.getClass().getClassLoader()).getDeclaredField("forgeLightPipelineEnabled");
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                this.lightPipeLine = field.getBoolean(null);
                field.set(null, false);
                field.setAccessible(accessible);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.loadRenderers();
        ((IEntityRenderer)Ambience.mc.entityRenderer).setLightmapUpdateNeeded(true);
    }

    @Override
    public void onDisable() {
        if (Environment.hasForge()) {
            try {
                Field field = Class.forName("net.minecraftforge.common.ForgeModContainer", true, this.getClass().getClassLoader()).getDeclaredField("forgeLightPipelineEnabled");
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                field.set(null, this.lightPipeLine);
                field.setAccessible(accessible);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.loadRenderers();
    }

    public void loadRenderers() {
        if (Ambience.mc.world != null && Ambience.mc.player != null && Ambience.mc.renderGlobal != null && Ambience.mc.gameSettings != null) {
            WorldRenderUtil.reload(true);
        }
    }
}

