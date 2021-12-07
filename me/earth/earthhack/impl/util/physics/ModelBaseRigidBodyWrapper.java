/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.jme3.bullet.collision.shapes.BoxCollisionShape
 *  com.jme3.bullet.collision.shapes.CollisionShape
 *  com.jme3.bullet.collision.shapes.CompoundCollisionShape
 *  com.jme3.math.Vector3f
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelBox
 *  net.minecraft.client.model.ModelRenderer
 *  org.joml.Matrix3f
 */
package me.earth.earthhack.impl.util.physics;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.math.Vector3f;
import me.earth.earthhack.api.util.interfaces.Globals;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import org.joml.Matrix3f;

public class ModelBaseRigidBodyWrapper
implements Globals {
    public ModelBaseRigidBodyWrapper(ModelBase model) {
        for (ModelRenderer renderer : model.boxList) {
            CompoundCollisionShape compoundShape = new CompoundCollisionShape();
            for (ModelBox box : renderer.cubeList) {
                float xWidth = Math.abs(box.posX2 - box.posX1);
                float yHeight = Math.abs(box.posY2 - box.posY1);
                float zWidth = Math.abs(box.posZ2 - box.posZ1);
                BoxCollisionShape shape = new BoxCollisionShape(xWidth / 2.0f, yHeight / 2.0f, zWidth / 2.0f);
                shape.setMargin(0.001f);
                Vector3f positionVector = new Vector3f(box.posX1 + xWidth / 2.0f, box.posY1 + yHeight / 2.0f, box.posZ1 + zWidth / 2.0f);
                compoundShape.addChildShape((CollisionShape)shape, positionVector);
            }
            compoundShape.setScale(new Vector3f(0.0625f, 0.0625f, 0.0625f));
            Matrix3f matrix3f = new Matrix3f();
        }
    }
}

