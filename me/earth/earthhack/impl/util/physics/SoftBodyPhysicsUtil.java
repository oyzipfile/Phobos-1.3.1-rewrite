/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.jme3.bullet.PhysicsSoftSpace
 *  com.jme3.bullet.PhysicsSpace$BroadphaseType
 *  com.jme3.bullet.collision.shapes.BoxCollisionShape
 *  com.jme3.bullet.collision.shapes.CollisionShape
 *  com.jme3.bullet.collision.shapes.infos.IndexedMesh
 *  com.jme3.bullet.objects.PhysicsRigidBody
 *  com.jme3.bullet.objects.PhysicsSoftBody
 *  com.jme3.bullet.util.NativeSoftBodyUtil
 *  com.jme3.math.Vector3f
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.BlockPos$MutableBlockPos
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.World
 *  org.joml.Vector3f
 */
package me.earth.earthhack.impl.util.physics;

import com.jme3.bullet.PhysicsSoftSpace;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.infos.IndexedMesh;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.bullet.objects.PhysicsSoftBody;
import com.jme3.bullet.util.NativeSoftBodyUtil;
import com.jme3.math.Vector3f;
import me.earth.earthhack.impl.util.math.geocache.Sphere;
import me.earth.earthhack.impl.util.render.model.IModel;
import me.earth.earthhack.impl.util.render.model.Mesh;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class SoftBodyPhysicsUtil {
    private static final BlockPos.MutableBlockPos mPos = new BlockPos.MutableBlockPos();

    public static PhysicsSoftBody createSoftBodyPhysicsMeshFromModel(IModel model) {
        PhysicsSoftBody body = new PhysicsSoftBody();
        for (Mesh mesh : model.getMeshes()) {
            Vector3f[] vectors = new Vector3f[mesh.getVertices().size()];
            for (int i = 0; i < vectors.length; ++i) {
                org.joml.Vector3f vector = mesh.getVerticesAsVector().get(i);
                vectors[i] = new Vector3f(vector.x, vector.y, vector.z);
            }
            IndexedMesh indexedMesh = new IndexedMesh(vectors, mesh.getIndices().array());
            NativeSoftBodyUtil.appendFromNativeMesh((IndexedMesh)indexedMesh, (PhysicsSoftBody)body);
        }
        return body;
    }

    public static PhysicsSoftSpace getSoftSpaceFromWorld(World world, BlockPos origin) {
        PhysicsSoftSpace space = new PhysicsSoftSpace(new Vector3f((float)(origin.getX() - 3), (float)(origin.getY() - 3), (float)(origin.getZ() - 3)), new Vector3f((float)(origin.getX() + 3), (float)(origin.getY() + 3), (float)(origin.getZ() + 3)), PhysicsSpace.BroadphaseType.SIMPLE);
        int maxRadius = Sphere.getRadius(3.0);
        for (int i = 1; i < maxRadius; ++i) {
            mPos.setPos((Vec3i)origin.add(Sphere.get(i)));
            if (world.getBlockState((BlockPos)mPos).getBlock() == Blocks.AIR) continue;
            BoxCollisionShape shape = new BoxCollisionShape(0.5f);
            shape.setMargin(0.001f);
            PhysicsRigidBody body = new PhysicsRigidBody((CollisionShape)shape);
            body.setMass(0.0f);
            body.setGravity(new Vector3f(0.0f, 0.0f, 0.0f));
            space.add((Object)body);
        }
        return space;
    }

    public static PhysicsSoftSpace updateSoftSpace(World world, BlockPos origin, PhysicsSoftSpace space) {
        for (PhysicsRigidBody physicsRigidBody : space.getRigidBodyList()) {
        }
        return null;
    }

    public static PhysicsSoftBody createClothBody(IndexedMesh mesh) {
        PhysicsSoftBody body = new PhysicsSoftBody();
        NativeSoftBodyUtil.appendFromNativeMesh((IndexedMesh)mesh, (PhysicsSoftBody)body);
        body.getSoftMaterial().setAngularStiffness(0.0f);
        return body;
    }
}

