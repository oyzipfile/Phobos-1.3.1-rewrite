/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.SoundType
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumActionResult
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
 */
package me.earth.earthhack.vanilla.mixins;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.player.noglitchblocks.NoGlitchBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value={ItemBlock.class})
public abstract class MixinItemBlock {
    @Shadow
    @Final
    protected Block block;
    private static final ModuleCache<NoGlitchBlocks> NO_GLITCH_BLOCKS = Caches.getModule(NoGlitchBlocks.class);

    @Inject(method={"onItemUse"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z")}, locals=LocalCapture.CAPTURE_FAILHARD, cancellable=true)
    private void setBlockStateHook(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, CallbackInfoReturnable<EnumActionResult> cir, ItemStack itemStack_1) {
        if (worldIn.isRemote && NO_GLITCH_BLOCKS.returnIfPresent(NoGlitchBlocks::noPlace, false).booleanValue()) {
            SoundType soundtype = this.block.getSoundType();
            worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f);
            itemStack_1.shrink(1);
            cir.setReturnValue((Object)EnumActionResult.SUCCESS);
        }
    }
}

