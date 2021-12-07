/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.chunk.RenderChunk
 *  net.minecraft.util.BlockRenderLayer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package me.earth.earthhack.vanilla.mixins;

import me.earth.earthhack.api.event.bus.instance.Bus;
import me.earth.earthhack.impl.event.events.render.BlockLayerEvent;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockRenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={RenderChunk.class})
public abstract class MixinRenderChunk {
    @Redirect(method={"rebuildChunk"}, at=@At(value="INVOKE", target="Lnet/minecraft/block/Block;getRenderLayer()Lnet/minecraft/util/BlockRenderLayer;"))
    private BlockRenderLayer getRenderLayerHook(Block block) {
        BlockLayerEvent event = new BlockLayerEvent(block);
        Bus.EVENT_BUS.post(event);
        if (event.getLayer() != null) {
            return event.getLayer();
        }
        return block.getRenderLayer();
    }
}

