/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.DamageSource
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package me.earth.earthhack.impl.core.mixins.entity.living.player;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.impl.core.ducks.entity.IEntityOtherPlayerMP;
import me.earth.earthhack.impl.core.mixins.entity.living.player.MixinAbstractClientPlayer;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.misc.nointerp.NoInterp;
import me.earth.earthhack.impl.modules.player.spectate.Spectate;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityOtherPlayerMP.class})
public abstract class MixinEntityOtherPlayerMP
extends MixinAbstractClientPlayer
implements IEntityOtherPlayerMP {
    private static final ModuleCache<NoInterp> NOINTERP = Caches.getModule(NoInterp.class);
    private static final ModuleCache<Spectate> SPECTATE = Caches.getModule(Spectate.class);
    private float theYaw;
    private float thePitch;

    @Override
    public boolean attackEntitySuper(DamageSource source, float amount) {
        return super.attackEntityFrom(source, amount);
    }

    @Inject(method={"attackEntityFrom"}, at={@At(value="HEAD")}, cancellable=true)
    private void attackEntityFromHook(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.shouldAttackSuper()) {
            cir.setReturnValue((Object)this.returnFromSuperAttack(source, amount));
        }
    }

    @Inject(method={"setPositionAndRotationDirect"}, at={@At(value="RETURN")})
    private void setPositionAndRotationDirectHook(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport, CallbackInfo ci) {
        if (NOINTERP.isEnabled()) {
            NoInterp.handleNoInterp((NoInterp)NOINTERP.get(), (Entity)Entity.class.cast(this), posRotationIncrements, x, y, z, yaw, pitch);
        }
    }

    @Inject(method={"onLivingUpdate"}, at={@At(value="HEAD")})
    private void onLivingUpdateHead(CallbackInfo ci) {
        this.theYaw = this.rotationYaw;
        this.thePitch = this.rotationPitch;
    }

    @Redirect(method={"onLivingUpdate"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/EntityOtherPlayerMP;setRotation(FF)V"))
    private void setRotationHook(EntityOtherPlayerMP entityOtherPlayerMP, float yaw, float pitch) {
        if (SPECTATE.isEnabled() && ((Spectate)SPECTATE.get()).shouldTurn() && entityOtherPlayerMP.equals((Object)((Spectate)SPECTATE.get()).getRender())) {
            this.setRotation(this.theYaw, this.thePitch);
            return;
        }
        this.setRotation(yaw, pitch);
    }
}

