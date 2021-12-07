/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.badlogic.gdx.math.Vector3
 *  com.badlogic.gdx.physics.bullet.collision.btAxisSweep3
 *  com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
 *  com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration
 *  com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher
 *  com.badlogic.gdx.physics.bullet.collision.btDispatcher
 *  com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver
 *  com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
 *  com.badlogic.gdx.physics.bullet.softbody.btSoftBody
 *  com.badlogic.gdx.physics.bullet.softbody.btSoftBodyRigidBodyCollisionConfiguration
 *  com.badlogic.gdx.physics.bullet.softbody.btSoftRigidDynamicsWorld
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.World
 */
package me.earth.earthhack.impl.util.physics.gdx;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBodyRigidBodyCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.softbody.btSoftRigidDynamicsWorld;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class GDXSoftBodyUtil {
    public static btSoftRigidDynamicsWorld createSoftPhysicsWorld(World world, Vec3i center) {
        btSoftBodyRigidBodyCollisionConfiguration collisionConfiguration = new btSoftBodyRigidBodyCollisionConfiguration();
        btCollisionDispatcher dispatcher = new btCollisionDispatcher((btCollisionConfiguration)collisionConfiguration);
        btAxisSweep3 broadphase = new btAxisSweep3(new Vector3(-1000.0f, -1000.0f, -1000.0f), new Vector3(1000.0f, 1000.0f, 1000.0f), 1024);
        btSequentialImpulseConstraintSolver solver = new btSequentialImpulseConstraintSolver();
        btSoftRigidDynamicsWorld dynamicsWorld = new btSoftRigidDynamicsWorld((btDispatcher)dispatcher, (btBroadphaseInterface)broadphase, (btConstraintSolver)solver, (btCollisionConfiguration)collisionConfiguration);
        return dynamicsWorld;
    }

    public static btSoftBody getCapeBody() {
        return null;
    }
}

