/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketEntityStatus
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package me.earth.earthhack.impl.core.mixins.network.server;

import net.minecraft.network.play.server.SPacketEntityStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={SPacketEntityStatus.class})
public interface ISPacketEntityStatus {
    @Accessor(value="entityId")
    public int getEntityId();

    @Accessor(value="logicOpcode")
    public byte getLogicOpcode();
}

