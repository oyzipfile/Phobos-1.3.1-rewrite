/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.network.NetworkManager
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketResourcePackStatus
 *  net.minecraft.network.play.client.CPacketResourcePackStatus$Action
 *  net.minecraft.network.play.server.SPacketPlayerPosLook
 *  net.minecraft.network.play.server.SPacketResourcePackSend
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Scoreboard
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.gen.Accessor
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.earth.earthhack.impl.core.mixins.network;

import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.impl.core.ducks.network.INetHandlerPlayClient;
import me.earth.earthhack.impl.managers.Managers;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.misc.packets.Packets;
import me.earth.earthhack.impl.util.thread.Locks;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={NetHandlerPlayClient.class})
public abstract class MixinNetHandlerPlayClient
implements INetHandlerPlayClient {
    private static final ModuleCache<Packets> PACKETS = Caches.getModule(Packets.class);
    @Shadow
    @Final
    private NetworkManager netManager;

    @Override
    @Accessor(value="doneLoadingTerrain")
    public abstract boolean isDoneLoadingTerrain();

    @Override
    @Accessor(value="doneLoadingTerrain")
    public abstract void setDoneLoadingTerrain(boolean var1);

    @Redirect(method={"handleEntityTeleport"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;setPositionAndRotationDirect(DDDFFIZ)V", ordinal=0))
    private void setPositionAndRotationDirectHook(Entity entity, double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        if (posRotationIncrements == 0 && PACKETS.returnIfPresent(Packets::areMiniTeleportsActive, false).booleanValue()) {
            entity.setPositionAndRotation(x, y, z, yaw, pitch);
        } else {
            entity.setPositionAndRotationDirect(x, y, z, yaw, pitch, posRotationIncrements, teleport);
        }
    }

    @Redirect(method={"handleTeams"}, at=@At(value="INVOKE", target="Lnet/minecraft/scoreboard/Scoreboard;removeTeam(Lnet/minecraft/scoreboard/ScorePlayerTeam;)V"))
    private void getScoreboardHook(Scoreboard scoreboard, ScorePlayerTeam playerTeam) {
        if (scoreboard != null && playerTeam != null) {
            scoreboard.removeTeam(playerTeam);
        }
    }

    @Inject(method={"handleResourcePack"}, at={@At(value="HEAD")}, cancellable=true)
    private void validateResourcePackHook(SPacketResourcePackSend packetIn, CallbackInfo ci) {
        if (packetIn.getURL() == null || packetIn.getHash() == null) {
            this.netManager.sendPacket((Packet)new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.FAILED_DOWNLOAD));
            ci.cancel();
        }
    }

    @Inject(method={"handlePlayerPosLook"}, at={@At(value="INVOKE", target="Lnet/minecraft/network/NetworkManager;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal=1, shift=At.Shift.BEFORE)})
    private void handlePlayerPosLookHook(SPacketPlayerPosLook packetIn, CallbackInfo ci) {
        Managers.ROTATION.setBlocking(true);
    }

    @Inject(method={"handlePlayerPosLook"}, at={@At(value="INVOKE", target="Lnet/minecraft/network/NetworkManager;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal=1, shift=At.Shift.AFTER)})
    private void handlePlayerPosLookHookPost(SPacketPlayerPosLook packetIn, CallbackInfo ci) {
        Managers.ROTATION.setBlocking(false);
    }

    @Redirect(method={"handleHeldItemChange"}, at=@At(value="FIELD", target="Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I"))
    private void handleHeldItemChangeHook(InventoryPlayer inventoryPlayer, int value) {
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
            inventoryPlayer.currentItem = value;
        });
    }
}

