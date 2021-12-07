/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 */
package me.earth.earthhack.impl.core.mixins.network.client;

import me.earth.earthhack.impl.core.ducks.network.ICPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value={CPacketPlayerDigging.class})
public abstract class MixinCPacketPlayerDigging
implements ICPacketPlayerDigging {
    @Unique
    private boolean clientSideBreaking;

    @Override
    @Unique
    public void setClientSideBreaking(boolean breaking) {
        this.clientSideBreaking = breaking;
    }

    @Override
    @Unique
    public boolean isClientSideBreaking() {
        return this.clientSideBreaking;
    }
}

