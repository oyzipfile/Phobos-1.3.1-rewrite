/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.DamageSource
 */
package me.earth.earthhack.impl.core.ducks.entity;

import net.minecraft.util.DamageSource;

public interface IEntityOtherPlayerMP {
    default public boolean attackEntitySuper(DamageSource source, float amount) {
        return true;
    }

    default public boolean returnFromSuperAttack(DamageSource source, float amount) {
        return this.attackEntitySuper(source, amount);
    }

    default public boolean shouldAttackSuper() {
        return false;
    }
}

