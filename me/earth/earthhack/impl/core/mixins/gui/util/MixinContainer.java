/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.Container
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package me.earth.earthhack.impl.core.mixins.gui.util;

import me.earth.earthhack.impl.core.ducks.util.IContainer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={Container.class})
public abstract class MixinContainer
implements IContainer {
    @Override
    @Accessor(value="transactionID")
    public abstract void setTransactionID(short var1);

    @Override
    @Accessor(value="transactionID")
    public abstract short getTransactionID();
}

