/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelRenderer
 */
package me.earth.earthhack.impl.event.events.render;

import me.earth.earthhack.api.event.events.Stage;
import me.earth.earthhack.api.event.events.StageEvent;
import net.minecraft.client.model.ModelRenderer;

public class RenderCrystalCubeEvent
extends StageEvent {
    private float scale;
    private ModelRenderer model;
    private final Model modelType;

    public RenderCrystalCubeEvent(float scale, ModelRenderer model, Model modelType, Stage stage) {
        super(stage);
        this.scale = scale;
        this.modelType = modelType;
        this.model = model;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Model getModelType() {
        return this.modelType;
    }

    public ModelRenderer getModel() {
        return this.model;
    }

    public void setModel(ModelRenderer model) {
        this.model = model;
    }

    public static enum Model {
        GLASS_1,
        GLASS_2,
        CUBE,
        BASE;

    }
}

