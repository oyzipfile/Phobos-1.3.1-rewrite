/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.EnumConnectionState
 *  net.minecraft.network.Packet
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package me.earth.earthhack.impl.core.mixins.network;

import java.util.Map;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={EnumConnectionState.class})
public interface IEnumConnectionState {
    @Accessor(value="STATES_BY_CLASS")
    public Map<Class<? extends Packet<?>>, EnumConnectionState> getStatesByClass();
}

