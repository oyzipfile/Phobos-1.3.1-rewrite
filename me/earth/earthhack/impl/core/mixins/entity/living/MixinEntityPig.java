/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityPig
 *  net.minecraft.world.World
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package me.earth.earthhack.impl.core.mixins.entity.living;

import me.earth.earthhack.api.event.bus.instance.Bus;
import me.earth.earthhack.impl.event.events.misc.AIEvent;
import me.earth.earthhack.impl.event.events.movement.ControlEvent;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityPig.class})
public abstract class MixinEntityPig
extends EntityAnimal {
    public MixinEntityPig(World world) {
        super(world);
    }

    @Inject(method={"canBeSteered"}, at={@At(value="HEAD")}, cancellable=true)
    private void canBeSteeredHook(CallbackInfoReturnable<Boolean> info) {
        ControlEvent event = new ControlEvent();
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.setReturnValue((Object)true);
        }
    }

    @Redirect(method={"travel"}, at=@At(value="INVOKE", target="net/minecraft/entity/passive/EntityAnimal.travel(FFF)V"))
    private void travelHook(EntityAnimal var1, float strafe, float vertical, float forward) {
        AIEvent event = new AIEvent();
        Bus.EVENT_BUS.post(event);
        super.travel(strafe, vertical, event.isCancelled() ? 0.0f : forward);
    }
}

