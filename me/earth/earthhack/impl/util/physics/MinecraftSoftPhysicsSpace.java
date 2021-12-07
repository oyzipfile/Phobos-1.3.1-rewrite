/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.jme3.bullet.PhysicsSoftSpace
 *  com.jme3.bullet.PhysicsSpace$BroadphaseType
 *  com.jme3.math.Vector3f
 */
package me.earth.earthhack.impl.util.physics;

import com.jme3.bullet.PhysicsSoftSpace;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;

public class MinecraftSoftPhysicsSpace
extends PhysicsSoftSpace {
    public MinecraftSoftPhysicsSpace(Vector3f worldMin, Vector3f worldMax, PhysicsSpace.BroadphaseType broadphaseType) {
        super(worldMin, worldMax, broadphaseType);
    }

    public void tick() {
    }
}

