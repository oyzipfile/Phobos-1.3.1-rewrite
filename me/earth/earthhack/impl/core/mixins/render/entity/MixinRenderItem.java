/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.client.renderer.block.model.IBakedModel
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.gen.Accessor
 *  org.spongepowered.asm.mixin.gen.Invoker
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyArg
 *  org.spongepowered.asm.mixin.injection.ModifyArgs
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.invoke.arg.Args
 */
package me.earth.earthhack.impl.core.mixins.render.entity;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.api.event.bus.instance.Bus;
import me.earth.earthhack.impl.core.ducks.render.IRenderItem;
import me.earth.earthhack.impl.event.events.render.RenderHeldItemEvent;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.render.itemchams.ItemChams;
import me.earth.earthhack.impl.modules.render.rainbowenchant.RainbowEnchant;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value={RenderItem.class})
public abstract class MixinRenderItem
implements IRenderItem {
    private static final ResourceLocation RESOURCE = new ResourceLocation("textures/rainbow.png");
    private static final ModuleCache<RainbowEnchant> ENCHANT = Caches.getModule(RainbowEnchant.class);
    private static final ModuleCache<ItemChams> ITEM_CHAMS = Caches.getModule(ItemChams.class);

    @Shadow
    protected abstract void renderModel(IBakedModel var1, ItemStack var2);

    @Override
    @Accessor(value="notRenderingEffectsInGUI")
    public abstract void setNotRenderingEffectsInGUI(boolean var1);

    @Override
    @Invoker(value="renderModel")
    public abstract void invokeRenderModel(IBakedModel var1, ItemStack var2);

    @Redirect(method={"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/RenderItem;renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
    public void renderItemHook(RenderItem renderItem, IBakedModel model, ItemStack stack) {
        RenderHeldItemEvent.NonBuiltInRenderer.Pre pre = new RenderHeldItemEvent.NonBuiltInRenderer.Pre(stack, model, renderItem);
        Bus.EVENT_BUS.post(pre);
        if (!pre.isCancelled()) {
            this.renderModel(model, stack);
        }
        RenderHeldItemEvent.NonBuiltInRenderer.Post post = new RenderHeldItemEvent.NonBuiltInRenderer.Post(stack, model, renderItem);
        Bus.EVENT_BUS.post(post);
    }

    @Redirect(method={"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/tileentity/TileEntityItemStackRenderer;renderByItem(Lnet/minecraft/item/ItemStack;)V"))
    public void renderByItemHook(TileEntityItemStackRenderer tileEntityItemStackRenderer, ItemStack itemStackIn) {
        RenderHeldItemEvent.BuiltInRenderer.Pre pre = new RenderHeldItemEvent.BuiltInRenderer.Pre(itemStackIn, tileEntityItemStackRenderer);
        Bus.EVENT_BUS.post(pre);
        if (!pre.isCancelled()) {
            itemStackIn.getItem().getTileEntityItemStackRenderer().renderByItem(itemStackIn);
        }
        RenderHeldItemEvent.BuiltInRenderer.Post post = new RenderHeldItemEvent.BuiltInRenderer.Post(itemStackIn, tileEntityItemStackRenderer);
        Bus.EVENT_BUS.post(post);
    }

    @Redirect(method={"renderEffect"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", ordinal=0))
    public void bindHook(TextureManager textureManager, ResourceLocation resource) {
        if (ENCHANT.isEnabled()) {
            textureManager.bindTexture(RESOURCE);
        } else {
            textureManager.bindTexture(resource);
        }
    }

    @ModifyArg(method={"renderEffect"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/RenderItem;renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;I)V"))
    private int renderEffectHook(int glintColor) {
        if (ITEM_CHAMS.isEnabled() && ((ItemChams)ITEM_CHAMS.get()).isModifyingGlint()) {
            return ((ItemChams)ITEM_CHAMS.get()).getGlintColor().getRGB();
        }
        return glintColor;
    }

    @ModifyArgs(method={"renderEffect"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    private void scaleArgsHook(Args args) {
        if (ITEM_CHAMS.isEnabled() && ((ItemChams)ITEM_CHAMS.get()).isModifyingGlint()) {
            float scale = ((ItemChams)ITEM_CHAMS.get()).getScale();
            args.set(0, (Object)Float.valueOf(scale));
            args.set(1, (Object)Float.valueOf(scale));
            args.set(2, (Object)Float.valueOf(scale));
        }
    }

    @ModifyArgs(method={"renderEffect"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"))
    private void translateHook(Args args) {
        if (ITEM_CHAMS.isEnabled() && ((ItemChams)ITEM_CHAMS.get()).isModifyingGlint()) {
            args.set(0, (Object)Float.valueOf(((Float)args.get(0)).floatValue() * ((ItemChams)ITEM_CHAMS.get()).getFactor()));
        }
    }

    @ModifyArgs(method={"renderEffect"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V"))
    private void rotateHook(Args args) {
        if (ITEM_CHAMS.isEnabled() && ((ItemChams)ITEM_CHAMS.get()).isModifyingGlint()) {
            args.set(0, (Object)Float.valueOf(((Float)args.get(0)).floatValue() * ((ItemChams)ITEM_CHAMS.get()).getGlintRotate()));
        }
    }
}

