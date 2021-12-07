/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 */
package me.earth.earthhack.impl.modules.player.automine.util;

import me.earth.earthhack.impl.modules.player.automine.util.IConstellation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class EchestConstellation
implements IConstellation {
    private final BlockPos pos;

    public EchestConstellation(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public boolean isAffected(BlockPos pos, IBlockState state) {
        return this.pos.equals((Object)pos) && state.getBlock() != Blocks.ENDER_CHEST;
    }

    @Override
    public boolean isValid(IBlockAccess world, boolean checkPlayerState) {
        return world.getBlockState(this.pos).getBlock() == Blocks.ENDER_CHEST;
    }

    @Override
    public boolean cantBeImproved() {
        return false;
    }
}

