/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.BlockPos
 */
package me.earth.earthhack.impl.util.math.path;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public class BlockingEntity {
    private final Entity entity;
    private final BlockPos pos;

    public BlockingEntity(Entity entity, BlockPos pos) {
        this.entity = entity;
        this.pos = pos;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public BlockPos getBlockedPos() {
        return this.pos;
    }
}

