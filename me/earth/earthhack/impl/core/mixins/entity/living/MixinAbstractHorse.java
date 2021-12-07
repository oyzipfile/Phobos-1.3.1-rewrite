/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.passive.AbstractHorse
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package me.earth.earthhack.impl.core.mixins.entity.living;

import me.earth.earthhack.api.event.bus.instance.Bus;
import me.earth.earthhack.impl.core.mixins.entity.living.MixinEntityLivingBase;
import me.earth.earthhack.impl.event.events.movement.ControlEvent;
import me.earth.earthhack.impl.event.events.movement.HorseEvent;
import net.minecraft.entity.passive.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={AbstractHorse.class})
public abstract class MixinAbstractHorse
extends MixinEntityLivingBase {
    @Inject(method={"getHorseJumpStrength"}, at={@At(value="HEAD")}, cancellable=true)
    private void getHorseJumpStrengthHook(CallbackInfoReturnable<Double> info) {
        HorseEvent event = new HorseEvent();
        Bus.EVENT_BUS.post(event);
        if (event.getJumpHeight() != 0.0) {
            info.setReturnValue((Object)event.getJumpHeight());
        }
    }

    @Inject(method={"canBeSteered"}, at={@At(value="HEAD")}, cancellable=true)
    private void canBeSteeredHook(CallbackInfoReturnable<Boolean> info) {
        ControlEvent event = new ControlEvent();
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.setReturnValue((Object)true);
        }
    }

    @Inject(method={"isHorseSaddled"}, at={@At(value="HEAD")}, cancellable=true)
    private void isHorseSaddledHook(CallbackInfoReturnable<Boolean> info) {
        ControlEvent event = new ControlEvent();
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.setReturnValue((Object)true);
        }
    }
}

