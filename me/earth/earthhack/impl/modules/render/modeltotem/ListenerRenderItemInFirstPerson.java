/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.init.Items
 */
package me.earth.earthhack.impl.modules.render.modeltotem;

import me.earth.earthhack.api.event.events.Stage;
import me.earth.earthhack.impl.event.events.render.RenderItemInFirstPersonEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.modeltotem.ModelTotem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;

public class ListenerRenderItemInFirstPerson
extends ModuleListener<ModelTotem, RenderItemInFirstPersonEvent> {
    public ListenerRenderItemInFirstPerson(ModelTotem module) {
        super(module, RenderItemInFirstPersonEvent.class);
    }

    @Override
    public void invoke(RenderItemInFirstPersonEvent event) {
        if (event.getStage() == Stage.PRE && event.getStack().getItem() == Items.TOTEM_OF_UNDYING) {
            GlStateManager.pushMatrix();
            event.setCancelled(true);
            GlStateManager.translate((double)((ModelTotem)this.module).translateX.getValue(), (double)((ModelTotem)this.module).translateY.getValue(), (double)((ModelTotem)this.module).translateZ.getValue());
            GlStateManager.scale((double)((ModelTotem)this.module).scaleX.getValue(), (double)((ModelTotem)this.module).scaleY.getValue(), (double)((ModelTotem)this.module).scaleZ.getValue());
            GlStateManager.rotate((float)((ModelTotem)this.module).rotateHorizontal.getValue().floatValue(), (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)((ModelTotem)this.module).rotateVertical.getValue().floatValue(), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)((ModelTotem)this.module).rotateZ.getValue().floatValue(), (float)0.0f, (float)0.0f, (float)1.0f);
            if (((ModelTotem)this.module).fileSettingTest.getValue().getMeshes().length != 0) {
                ((ModelTotem)this.module).fileSettingTest.getValue().render(0.0, 0.0, 0.0, mc.getRenderPartialTicks());
            }
            GlStateManager.rotate((float)(-((ModelTotem)this.module).rotateZ.getValue().floatValue()), (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.rotate((float)(-((ModelTotem)this.module).rotateVertical.getValue().floatValue()), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)(-((ModelTotem)this.module).rotateHorizontal.getValue().floatValue()), (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.scale((double)(1.0 / ((ModelTotem)this.module).scaleX.getValue()), (double)(1.0 / ((ModelTotem)this.module).scaleY.getValue()), (double)(1.0 / ((ModelTotem)this.module).scaleZ.getValue()));
            GlStateManager.translate((double)(-((ModelTotem)this.module).translateX.getValue().doubleValue()), (double)(-((ModelTotem)this.module).translateY.getValue().doubleValue()), (double)(-((ModelTotem)this.module).translateZ.getValue().doubleValue()));
            GlStateManager.popMatrix();
        }
    }
}

