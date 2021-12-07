/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.init.Items
 */
package me.earth.earthhack.impl.modules.render.modeltotem;

import me.earth.earthhack.impl.event.events.render.RenderItemActivationEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.modeltotem.ModelTotem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;

public class ListenerRenderItemActivation
extends ModuleListener<ModelTotem, RenderItemActivationEvent> {
    public ListenerRenderItemActivation(ModelTotem module) {
        super(module, RenderItemActivationEvent.class);
    }

    @Override
    public void invoke(RenderItemActivationEvent event) {
        if (event.getStack().getItem() == Items.TOTEM_OF_UNDYING) {
            GlStateManager.pushMatrix();
            event.setCancelled(true);
            GlStateManager.translate((double)((ModelTotem)this.module).popTranslateX.getValue(), (double)((ModelTotem)this.module).popTranslateY.getValue(), (double)((ModelTotem)this.module).popTranslateZ.getValue());
            GlStateManager.scale((double)((ModelTotem)this.module).popScaleX.getValue(), (double)((ModelTotem)this.module).popScaleY.getValue(), (double)((ModelTotem)this.module).popScaleZ.getValue());
            GlStateManager.rotate((float)((ModelTotem)this.module).popRotateHorizontal.getValue().floatValue(), (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)((ModelTotem)this.module).popRotateVertical.getValue().floatValue(), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)((ModelTotem)this.module).popRotateZ.getValue().floatValue(), (float)0.0f, (float)0.0f, (float)1.0f);
            if (((ModelTotem)this.module).fileSettingTest.getValue().getMeshes().length != 0) {
                ((ModelTotem)this.module).fileSettingTest.getValue().render(0.0, 0.0, 0.0, mc.getRenderPartialTicks());
            }
            GlStateManager.rotate((float)(-((ModelTotem)this.module).popRotateZ.getValue().floatValue()), (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.rotate((float)(-((ModelTotem)this.module).popRotateVertical.getValue().floatValue()), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)(-((ModelTotem)this.module).popRotateHorizontal.getValue().floatValue()), (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.scale((double)(1.0 / ((ModelTotem)this.module).popScaleX.getValue()), (double)(1.0 / ((ModelTotem)this.module).popScaleY.getValue()), (double)(1.0 / ((ModelTotem)this.module).popScaleZ.getValue()));
            GlStateManager.translate((double)(-((ModelTotem)this.module).popTranslateX.getValue().doubleValue()), (double)(-((ModelTotem)this.module).popTranslateY.getValue().doubleValue()), (double)(-((ModelTotem)this.module).popTranslateZ.getValue().doubleValue()));
            GlStateManager.popMatrix();
        }
    }
}

