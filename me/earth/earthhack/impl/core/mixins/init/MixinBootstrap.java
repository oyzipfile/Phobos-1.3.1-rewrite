/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.advancements.AdvancementManager
 *  net.minecraft.init.Bootstrap
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package me.earth.earthhack.impl.core.mixins.init;

import java.io.File;
import me.earth.earthhack.impl.commands.packet.arguments.AdvancementArgument;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.init.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={Bootstrap.class})
public abstract class MixinBootstrap {
    @Redirect(method={"register"}, at=@At(value="NEW", target="net/minecraft/advancements/AdvancementManager"))
    private static AdvancementManager advancementManagerHook(File file) {
        return AdvancementArgument.MANAGER;
    }
}

