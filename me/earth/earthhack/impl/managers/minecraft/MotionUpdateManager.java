/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 */
package me.earth.earthhack.impl.managers.minecraft;

import java.util.LinkedList;
import java.util.Queue;
import me.earth.earthhack.api.event.bus.EventListener;
import me.earth.earthhack.api.event.bus.SubscriberImpl;
import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.impl.event.events.misc.GameLoopEvent;
import me.earth.earthhack.impl.event.events.network.PacketEvent;
import me.earth.earthhack.impl.event.listeners.CPacketPlayerListener;
import net.minecraft.network.play.client.CPacketPlayer;

public class MotionUpdateManager
extends SubscriberImpl
implements Globals {
    private final Queue<CPacketPlayer> delayed = new LinkedList<CPacketPlayer>();

    public MotionUpdateManager() {
        this.listeners.addAll(new CPacketPlayerListener(){

            @Override
            protected void onPacket(PacketEvent.Send<CPacketPlayer> event) {
            }

            @Override
            protected void onPosition(PacketEvent.Send<CPacketPlayer.Position> event) {
            }

            @Override
            protected void onRotation(PacketEvent.Send<CPacketPlayer.Rotation> event) {
            }

            @Override
            protected void onPositionRotation(PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
            }
        }.getListeners());
        this.listeners.add(new EventListener<GameLoopEvent>(GameLoopEvent.class){

            @Override
            public void invoke(GameLoopEvent event) {
            }
        });
    }
}

