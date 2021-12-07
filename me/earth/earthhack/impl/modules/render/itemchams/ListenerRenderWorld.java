/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  org.lwjgl.opengl.Display
 */
package me.earth.earthhack.impl.modules.render.itemchams;

import me.earth.earthhack.impl.core.ducks.entity.IEntityRenderer;
import me.earth.earthhack.impl.event.events.render.WorldRenderEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.render.itemchams.ItemChams;
import me.earth.earthhack.impl.util.render.ItemShader;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.Display;

public class ListenerRenderWorld
extends ModuleListener<ItemChams, WorldRenderEvent> {
    public ListenerRenderWorld(ItemChams module) {
        super(module, WorldRenderEvent.class);
    }

    @Override
    public void invoke(WorldRenderEvent event) {
        if ((Display.isActive() || Display.isVisible()) && ((ItemChams)this.module).chams.getValue().booleanValue()) {
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
            GlStateManager.enableDepth();
            GlStateManager.depthMask((boolean)true);
            GlStateManager.enableAlpha();
            ItemShader shader = ItemShader.ITEM_SHADER;
            shader.blur = ((ItemChams)this.module).blur.getValue();
            shader.mix = ((ItemChams)this.module).mix.getValue().floatValue();
            shader.alpha = (float)((ItemChams)this.module).chamColor.getValue().getAlpha() / 255.0f;
            shader.imageMix = ((ItemChams)this.module).imageMix.getValue().floatValue();
            shader.useImage = ((ItemChams)this.module).useImage.getValue();
            shader.startDraw(mc.getRenderPartialTicks());
            ((ItemChams)this.module).forceRender = true;
            ((IEntityRenderer)ListenerRenderWorld.mc.entityRenderer).invokeRenderHand(mc.getRenderPartialTicks(), 2);
            ((ItemChams)this.module).forceRender = false;
            shader.stopDraw(((ItemChams)this.module).chamColor.getValue(), ((ItemChams)this.module).radius.getValue().floatValue(), 1.0f, new Runnable[0]);
            GlStateManager.disableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.disableDepth();
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
        }
    }
}

