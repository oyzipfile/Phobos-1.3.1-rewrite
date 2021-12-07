/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer
 *  net.minecraft.item.ItemStack
 */
package me.earth.earthhack.impl.event.events.render;

import me.earth.earthhack.api.event.events.Event;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class RenderHeldItemEvent
extends Event {
    private final ItemStack stack;

    private RenderHeldItemEvent(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public static class BuiltInRenderer
    extends RenderHeldItemEvent {
        private final TileEntityItemStackRenderer renderer;

        private BuiltInRenderer(ItemStack stack, TileEntityItemStackRenderer renderer) {
            super(stack);
            this.renderer = renderer;
        }

        public TileEntityItemStackRenderer getRenderer() {
            return this.renderer;
        }

        public static class Post
        extends BuiltInRenderer {
            public Post(ItemStack stack, TileEntityItemStackRenderer renderer) {
                super(stack, renderer);
            }
        }

        public static class Pre
        extends BuiltInRenderer {
            public Pre(ItemStack stack, TileEntityItemStackRenderer renderer) {
                super(stack, renderer);
            }
        }
    }

    public static class NonBuiltInRenderer
    extends RenderHeldItemEvent {
        private final IBakedModel model;
        private final RenderItem renderItem;

        private NonBuiltInRenderer(ItemStack stack, IBakedModel model, RenderItem renderItem) {
            super(stack);
            this.model = model;
            this.renderItem = renderItem;
        }

        public IBakedModel getModel() {
            return this.model;
        }

        public RenderItem getRenderItem() {
            return this.renderItem;
        }

        public static class Post
        extends NonBuiltInRenderer {
            public Post(ItemStack stack, IBakedModel model, RenderItem renderItem) {
                super(stack, model, renderItem);
            }
        }

        public static class Pre
        extends NonBuiltInRenderer {
            public Pre(ItemStack stack, IBakedModel model, RenderItem renderItem) {
                super(stack, model, renderItem);
            }
        }
    }
}

