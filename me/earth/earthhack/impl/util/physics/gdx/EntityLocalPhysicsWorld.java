/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.badlogic.gdx.math.Matrix4
 *  com.badlogic.gdx.math.Vector3
 *  com.badlogic.gdx.physics.bullet.collision.btAxisSweep3
 *  com.badlogic.gdx.physics.bullet.collision.btBoxShape
 *  com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface
 *  com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration
 *  com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher
 *  com.badlogic.gdx.physics.bullet.collision.btCollisionObject
 *  com.badlogic.gdx.physics.bullet.collision.btCollisionShape
 *  com.badlogic.gdx.physics.bullet.collision.btCompoundShape
 *  com.badlogic.gdx.physics.bullet.collision.btDispatcher
 *  com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver
 *  com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
 *  com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw
 *  com.badlogic.gdx.physics.bullet.softbody.btSoftBody
 *  com.badlogic.gdx.physics.bullet.softbody.btSoftBodyHelpers
 *  com.badlogic.gdx.physics.bullet.softbody.btSoftBodyRigidBodyCollisionConfiguration
 *  com.badlogic.gdx.physics.bullet.softbody.btSoftBodyWorldInfo
 *  com.badlogic.gdx.physics.bullet.softbody.btSoftRigidDynamicsWorld
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$MutableBlockPos
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.World
 */
package me.earth.earthhack.impl.util.physics.gdx;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCompoundShape;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBodyHelpers;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBodyRigidBodyCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBodyWorldInfo;
import com.badlogic.gdx.physics.bullet.softbody.btSoftRigidDynamicsWorld;
import me.earth.earthhack.impl.util.math.geocache.Sphere;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class EntityLocalPhysicsWorld {
    private static final BlockPos.MutableBlockPos MUTABLE_POS = new BlockPos.MutableBlockPos();
    private final btSoftRigidDynamicsWorld world;
    private final btSoftBodyWorldInfo worldInfo;
    private final Entity entity;

    public EntityLocalPhysicsWorld(World world, Entity entity) {
        this.entity = entity;
        float posX = (float)entity.posX;
        float posY = (float)entity.posY;
        float posZ = (float)entity.posZ;
        btSoftBodyRigidBodyCollisionConfiguration collisionConfiguration = new btSoftBodyRigidBodyCollisionConfiguration();
        btCollisionDispatcher dispatcher = new btCollisionDispatcher((btCollisionConfiguration)collisionConfiguration);
        btAxisSweep3 broadphase = new btAxisSweep3(new Vector3(posX - 5.0f, posY - 5.0f, posZ - 5.0f), new Vector3(posX + 5.0f, posY + 5.0f, posZ + 5.0f), 1024);
        btSequentialImpulseConstraintSolver solver = new btSequentialImpulseConstraintSolver();
        this.world = new btSoftRigidDynamicsWorld((btDispatcher)dispatcher, (btBroadphaseInterface)broadphase, (btConstraintSolver)solver, (btCollisionConfiguration)collisionConfiguration);
        btCompoundShape shape = new btCompoundShape();
        int radius = Sphere.getRadius(6.0);
        for (int i = 0; i < radius; ++i) {
            Vec3i vec = Sphere.get(i);
            MUTABLE_POS.setPos(vec);
            if (world.getBlockState((BlockPos)MUTABLE_POS).getBlock() == Blocks.AIR) continue;
            btBoxShape box = new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f));
            shape.addChildShape(new Matrix4().translate((float)vec.getX() + 0.5f, (float)vec.getY() + 0.5f, (float)vec.getZ() + 0.5f), (btCollisionShape)box);
        }
        btCollisionObject object = new btCollisionObject();
        object.setCollisionShape((btCollisionShape)shape);
        this.world.addCollisionObject(object);
        this.world.setGravity(new Vector3(0.0f, 9.81f, 0.0f));
        this.worldInfo = new btSoftBodyWorldInfo();
        this.worldInfo.setBroadphase((btBroadphaseInterface)broadphase);
        this.worldInfo.setDispatcher((btDispatcher)dispatcher);
        this.worldInfo.getSparsesdf().Initialize();
        AxisAlignedBB bb = entity.getEntityBoundingBox();
        Vector3 vector00 = new Vector3((float)bb.minX, (float)bb.minY, (float)bb.minZ);
        Vector3 vector01 = new Vector3((float)bb.minX, (float)bb.maxY, (float)bb.minZ);
        Vector3 vector10 = new Vector3((float)bb.maxX, (float)bb.minY, (float)bb.minZ);
        Vector3 vector11 = new Vector3((float)bb.minX, (float)bb.maxY, (float)bb.minZ);
        btSoftBody body = btSoftBodyHelpers.CreatePatch((btSoftBodyWorldInfo)this.worldInfo, (Vector3)vector00, (Vector3)vector10, (Vector3)vector01, (Vector3)vector11, (int)15, (int)15, (int)15, (boolean)false);
        body.takeOwnership();
        body.setTotalMass(50.0f);
        btIDebugDraw drawer = new btIDebugDraw();
        this.world.setDebugDrawer(drawer);
        this.world.addSoftBody(body);
    }
}

