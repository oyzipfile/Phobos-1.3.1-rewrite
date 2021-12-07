/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.entity.item.EntityEnderPearl
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 */
package me.earth.earthhack.impl.modules.movement.reversestep;

import java.util.List;
import java.util.stream.Collectors;
import me.earth.earthhack.api.cache.ModuleCache;
import me.earth.earthhack.api.cache.SettingCache;
import me.earth.earthhack.api.event.events.Stage;
import me.earth.earthhack.api.setting.Setting;
import me.earth.earthhack.api.setting.settings.EnumSetting;
import me.earth.earthhack.impl.event.events.network.MotionUpdateEvent;
import me.earth.earthhack.impl.event.listeners.ModuleListener;
import me.earth.earthhack.impl.modules.Caches;
import me.earth.earthhack.impl.modules.movement.blocklag.BlockLag;
import me.earth.earthhack.impl.modules.movement.holetp.HoleTP;
import me.earth.earthhack.impl.modules.movement.longjump.LongJump;
import me.earth.earthhack.impl.modules.movement.packetfly.PacketFly;
import me.earth.earthhack.impl.modules.movement.reversestep.ReverseStep;
import me.earth.earthhack.impl.modules.movement.speed.Speed;
import me.earth.earthhack.impl.modules.movement.speed.SpeedMode;
import me.earth.earthhack.impl.util.math.position.PositionUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

final class ListenerMotion
extends ModuleListener<ReverseStep, MotionUpdateEvent> {
    private static final ModuleCache<PacketFly> PACKET_FLY = Caches.getModule(PacketFly.class);
    private static final ModuleCache<BlockLag> BLOCK_LAG = Caches.getModule(BlockLag.class);
    private static final ModuleCache<Speed> SPEED = Caches.getModule(Speed.class);
    private static final ModuleCache<LongJump> LONGJUMP = Caches.getModule(LongJump.class);
    private static final ModuleCache<HoleTP> HOLETP = Caches.getModule(HoleTP.class);
    private static final SettingCache<SpeedMode, EnumSetting<SpeedMode>, Speed> SPEED_MODE = Caches.getSetting(Speed.class, Setting.class, "Mode", SpeedMode.Instant);
    private boolean reset = false;

    public ListenerMotion(ReverseStep module) {
        super(module, MotionUpdateEvent.class);
    }

    @Override
    public void invoke(MotionUpdateEvent event) {
        if (event.getStage() == Stage.POST) {
            if (PositionUtil.inLiquid(true) || PositionUtil.inLiquid(false) || PACKET_FLY.isEnabled() || BLOCK_LAG.isEnabled() || LONGJUMP.isEnabled() || ((HoleTP)HOLETP.get()).isInHole() && HOLETP.isEnabled() || SPEED.isEnabled() && SPEED_MODE.getValue() != SpeedMode.Instant) {
                this.reset = true;
                return;
            }
            List pearls = ListenerMotion.mc.world.loadedEntityList.stream().filter(EntityEnderPearl.class::isInstance).map(EntityEnderPearl.class::cast).collect(Collectors.toList());
            if (!pearls.isEmpty()) {
                ((ReverseStep)this.module).waitForOnGround = true;
            }
            if (!ListenerMotion.mc.player.onGround) {
                if (ListenerMotion.mc.gameSettings.keyBindJump.isKeyDown()) {
                    ((ReverseStep)this.module).jumped = true;
                }
            } else {
                ((ReverseStep)this.module).jumped = false;
                this.reset = false;
                ((ReverseStep)this.module).waitForOnGround = false;
            }
            if (!((ReverseStep)this.module).jumped && (double)ListenerMotion.mc.player.fallDistance < 0.5 && ListenerMotion.mc.player.posY - ((ReverseStep)this.module).getNearestBlockBelow() > 0.625 && ListenerMotion.mc.player.posY - ((ReverseStep)this.module).getNearestBlockBelow() <= ((ReverseStep)this.module).distance.getValue() && !this.reset && !((ReverseStep)this.module).waitForOnGround) {
                if (!ListenerMotion.mc.player.onGround) {
                    ++((ReverseStep)this.module).packets;
                }
                if (!(ListenerMotion.mc.player.onGround || !(ListenerMotion.mc.player.motionY < 0.0) || ListenerMotion.mc.player.isOnLadder() || ListenerMotion.mc.player.isEntityInsideOpaqueBlock() || ((ReverseStep)this.module).strictLiquid.getValue().booleanValue() && (ListenerMotion.mc.player.isInsideOfMaterial(Material.LAVA) || ListenerMotion.mc.player.isInsideOfMaterial(Material.WATER)) || ListenerMotion.mc.gameSettings.keyBindJump.isKeyDown() || ((ReverseStep)this.module).packets <= 0)) {
                    ListenerMotion.mc.player.motionY = -((ReverseStep)this.module).speed.getValue().doubleValue();
                    ((ReverseStep)this.module).packets = 0;
                }
            }
        }
    }

    private boolean isLiquid(BlockPos position) {
        Block block = ListenerMotion.mc.world.getBlockState(position).getBlock();
        return block == Blocks.LAVA || block == Blocks.FLOWING_LAVA || block == Blocks.WATER || block == Blocks.FLOWING_WATER;
    }
}

