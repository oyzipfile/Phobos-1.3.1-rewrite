/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.modules.render.modeltotem;

import me.earth.earthhack.api.module.Module;
import me.earth.earthhack.api.module.util.Category;
import me.earth.earthhack.api.setting.Setting;
import me.earth.earthhack.api.setting.settings.BooleanSetting;
import me.earth.earthhack.api.setting.settings.ListSetting;
import me.earth.earthhack.api.setting.settings.NumberSetting;
import me.earth.earthhack.impl.managers.Managers;
import me.earth.earthhack.impl.modules.render.modeltotem.ListenerRenderItemActivation;
import me.earth.earthhack.impl.modules.render.modeltotem.ListenerRenderItemInFirstPerson;
import me.earth.earthhack.impl.util.render.model.IModel;
import me.earth.earthhack.impl.util.render.model.Mesh;

public class ModelTotem
extends Module {
    protected final Setting<IModel> fileSettingTest = this.register(new ListSetting<IModel>("Model", Managers.FILES.getInitialModel(), Managers.FILES.getModels()));
    protected final Setting<Boolean> debug = this.register(new BooleanSetting("Debug", false));
    protected final Setting<Float> rotateHorizontal = this.register(new NumberSetting<Float>("RotateX", Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(360.0f)));
    protected final Setting<Float> rotateVertical = this.register(new NumberSetting<Float>("RotateY", Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(360.0f)));
    protected final Setting<Float> rotateZ = this.register(new NumberSetting<Float>("RotateZ", Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(360.0f)));
    protected final Setting<Double> translateX = this.register(new NumberSetting<Double>("TranslateX", 0.0, -1.0, 1.0));
    protected final Setting<Double> translateY = this.register(new NumberSetting<Double>("TranslateY", 0.0, -1.0, 1.0));
    protected final Setting<Double> translateZ = this.register(new NumberSetting<Double>("TranslateZ", 0.0, -1.0, 1.0));
    protected final Setting<Double> scaleX = this.register(new NumberSetting<Double>("ScaleX", 1.0, 1.0E-5, 10.0));
    protected final Setting<Double> scaleY = this.register(new NumberSetting<Double>("ScaleY", 1.0, 1.0E-5, 10.0));
    protected final Setting<Double> scaleZ = this.register(new NumberSetting<Double>("ScaleZ", 1.0, 1.0E-5, 10.0));
    protected final Setting<Float> popRotateHorizontal = this.register(new NumberSetting<Float>("PopRotateX", Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(360.0f)));
    protected final Setting<Float> popRotateVertical = this.register(new NumberSetting<Float>("PopRotateY", Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(360.0f)));
    protected final Setting<Float> popRotateZ = this.register(new NumberSetting<Float>("PopRotateZ", Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(360.0f)));
    protected final Setting<Double> popTranslateX = this.register(new NumberSetting<Double>("PopTranslateX", 0.0, -1.0, 1.0));
    protected final Setting<Double> popTranslateY = this.register(new NumberSetting<Double>("PopTranslateY", 0.0, -1.0, 1.0));
    protected final Setting<Double> popTranslateZ = this.register(new NumberSetting<Double>("PopTranslateZ", 0.0, -1.0, 1.0));
    protected final Setting<Double> popScaleX = this.register(new NumberSetting<Double>("PopScaleX", 1.0, 1.0E-4, 10.0));
    protected final Setting<Double> popScaleY = this.register(new NumberSetting<Double>("PopScaleY", 1.0, 1.0E-4, 10.0));
    protected final Setting<Double> popScaleZ = this.register(new NumberSetting<Double>("PopScaleZ", 1.0, 1.0E-4, 10.0));
    private Mesh[] modelMeshes;

    public ModelTotem() {
        super("ModelTotem", Category.Render);
        this.listeners.add(new ListenerRenderItemInFirstPerson(this));
        this.listeners.add(new ListenerRenderItemActivation(this));
    }
}

