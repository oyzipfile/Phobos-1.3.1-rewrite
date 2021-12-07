/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 */
package me.earth.earthhack.impl.util.render.model.animation;

import java.util.Map;
import me.earth.earthhack.api.util.interfaces.Nameable;
import me.earth.earthhack.impl.util.render.model.animation.Animation;
import me.earth.earthhack.impl.util.render.model.animation.AssimpNodeData;
import me.earth.earthhack.impl.util.render.model.animation.Bone;
import me.earth.earthhack.impl.util.render.model.animation.BoneInfo;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

public class AnimationWrapper
implements Nameable {
    private final Animation animation;
    private double currentTime;
    private double deltaTime;
    private Matrix4f[] matrices = new Matrix4f[100];

    public AnimationWrapper(Animation animation) {
        this.animation = animation;
        this.currentTime = 0.0;
        for (int i = 0; i < 100; ++i) {
            this.matrices[i] = new Matrix4f();
        }
    }

    public void update(double partialTicks) {
        this.deltaTime = partialTicks;
        this.currentTime += partialTicks * this.animation.getTPS();
        this.currentTime %= this.animation.getLength();
        this.calculateBoneTransformations(this.animation.getRoot(), new Matrix4f());
    }

    public void calculateBoneTransformations(AssimpNodeData node, Matrix4f parent) {
        String name = node.getName();
        Matrix4f transform = node.getTransformation();
        Bone bone = this.animation.findBone(name);
        if (bone != null) {
            bone.update(this.currentTime);
            transform = bone.getLocalTransformation();
        }
        Matrix4f globalTransform = transform.mul((Matrix4fc)parent);
        Map<String, BoneInfo> map = this.animation.getBoneInfoMap();
        if (!map.isEmpty() && map.containsKey(name)) {
            int index = map.get(name).getId();
            Matrix4f off = map.get(name).getOffset();
            this.matrices[index] = off.mul((Matrix4fc)globalTransform);
        }
        for (AssimpNodeData child : node.getChildren()) {
            this.calculateBoneTransformations(child, globalTransform);
        }
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public Matrix4f[] getMatrices() {
        return this.matrices;
    }

    @Override
    public String getName() {
        return this.animation.getName();
    }
}

