/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  jassimp.AiNodeAnim
 *  jassimp.AiWrapperProvider
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 */
package me.earth.earthhack.impl.util.render.model.animation;

import jassimp.AiNodeAnim;
import jassimp.AiWrapperProvider;
import java.util.ArrayList;
import java.util.List;
import me.earth.earthhack.impl.util.math.GlMathUtil;
import me.earth.earthhack.impl.util.math.MathUtil;
import me.earth.earthhack.impl.util.render.model.AssimpJomlProvider;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Bone {
    private final List<KeyPosition> positions = new ArrayList<KeyPosition>();
    private final List<KeyRotation> rotations = new ArrayList<KeyRotation>();
    private final List<KeyScale> scales = new ArrayList<KeyScale>();
    private final String name;
    private final int id;
    private Matrix4f localTransformation = new Matrix4f();

    public Bone(String name, int id, AiNodeAnim node) {
        this.name = name;
        this.id = id;
        int numPositions = node.getNumPosKeys();
        for (int i = 0; i < numPositions; ++i) {
            double x = node.getPosKeyX(i);
            double y = node.getPosKeyY(i);
            double z = node.getPosKeyZ(i);
            double timeStamp = node.getPosKeyTime(i);
            this.positions.add(new KeyPosition(new Vector3f((float)x, (float)y, (float)z), timeStamp));
        }
        int numRotations = node.getNumRotKeys();
        for (int i = 0; i < numRotations; ++i) {
            Quaternionf x = (Quaternionf)node.getRotKeyQuaternion(i, (AiWrapperProvider)new AssimpJomlProvider());
            double timeStamp = node.getRotKeyTime(i);
            this.rotations.add(new KeyRotation(x, timeStamp));
        }
        int numScales = node.getNumScaleKeys();
        for (int i = 0; i < numScales; ++i) {
            double x = node.getScaleKeyX(i);
            double y = node.getScaleKeyY(i);
            double z = node.getScaleKeyZ(i);
            double timeStamp = node.getScaleKeyTime(i);
            this.scales.add(new KeyScale(new Vector3f((float)x, (float)y, (float)z), timeStamp));
        }
        System.out.println("Bone: " + name + " ID: " + id + " NumPositions: " + numPositions);
    }

    public void update(double partialTicks) {
        this.localTransformation = this.interpolatePos(partialTicks).mul((Matrix4fc)this.interpolateRotation(partialTicks), new Matrix4f()).mul((Matrix4fc)this.interpolateScale(partialTicks), new Matrix4f());
    }

    private float getScaleFactor(double lastTimeStamp, double nextTimeStamp, double animationTime) {
        double midWayLength = animationTime - lastTimeStamp;
        double framesDiff = nextTimeStamp - lastTimeStamp;
        float scaleFactor = (float)(midWayLength / framesDiff);
        return scaleFactor;
    }

    private Matrix4f interpolatePos(double timestamp) {
        if (this.positions.size() == 1) {
            KeyPosition position = this.positions.get(0);
            return new Matrix4f().translate((Vector3fc)position.getPosition());
        }
        int pos = this.getPosition(timestamp);
        int pos1 = pos + 1;
        float scaleFactor = this.getScaleFactor(this.positions.get(pos).getTimestamp(), this.positions.get(pos1).getTimestamp(), timestamp);
        Vector3f finalPosition = MathUtil.lerp(this.positions.get(pos).getPosition(), this.positions.get(pos1).getPosition(), scaleFactor);
        return GlMathUtil.initTranslationJoml(finalPosition);
    }

    private Matrix4f interpolateRotation(double timestamp) {
        if (this.rotations.size() == 1) {
            KeyRotation rotation = this.rotations.get(0);
            return rotation.getQuaternion().normalize().get(new Matrix4f());
        }
        int rot = this.getRotation(timestamp);
        int rot1 = rot + 1;
        float scaleFactor = this.getScaleFactor(this.rotations.get(rot).getTimestamp(), this.rotations.get(rot1).getTimestamp(), timestamp);
        return GlMathUtil.toRotationMatrixJoml(GlMathUtil.normalizedLerp(this.rotations.get(rot).getQuaternion(), this.rotations.get(rot1).getQuaternion(), scaleFactor));
    }

    private Matrix4f interpolateScale(double timestamp) {
        if (this.scales.size() == 1) {
            KeyScale scale = this.scales.get(0);
            return new Matrix4f().translate((Vector3fc)scale.getScale());
        }
        int scale = this.getScale(timestamp);
        int scale1 = scale + 1;
        float scaleFactor = this.getScaleFactor(this.scales.get(scale).getTimestamp(), this.scales.get(scale1).getTimestamp(), timestamp);
        Vector3f finalScale = MathUtil.mix(this.scales.get(scale).getScale(), this.scales.get(scale1).getScale(), scaleFactor);
        return GlMathUtil.initScaleJoml(finalScale);
    }

    public int getPosition(double timestamp) {
        for (int i = 0; i < this.positions.size(); ++i) {
            if (!(this.positions.get(i).getTimestamp() >= timestamp)) continue;
            return i;
        }
        return -1;
    }

    public int getRotation(double timestamp) {
        for (int i = 0; i < this.rotations.size(); ++i) {
            if (!(this.rotations.get(i).getTimestamp() >= timestamp)) continue;
            return i;
        }
        return -1;
    }

    public int getScale(double timestamp) {
        for (int i = 0; i < this.scales.size(); ++i) {
            if (!(this.scales.get(i).getTimestamp() >= timestamp)) continue;
            return i;
        }
        return -1;
    }

    public String getName() {
        return this.name;
    }

    public Matrix4f getLocalTransformation() {
        return this.localTransformation;
    }

    static class KeyScale {
        private final Vector3f scale;
        private final double timestamp;

        public KeyScale(Vector3f scale, double timestamp) {
            this.scale = scale;
            this.timestamp = timestamp;
        }

        public Vector3f getScale() {
            return this.scale;
        }

        public double getTimestamp() {
            return this.timestamp;
        }
    }

    static class KeyRotation {
        private final Quaternionf quaternion;
        private final double timestamp;

        public KeyRotation(Quaternionf quaternion, double timestamp) {
            this.quaternion = quaternion;
            this.timestamp = timestamp;
        }

        public Quaternionf getQuaternion() {
            return this.quaternion;
        }

        public double getTimestamp() {
            return this.timestamp;
        }
    }

    static class KeyPosition {
        private final Vector3f position;
        private final double timestamp;

        public KeyPosition(Vector3f position, double timestamp) {
            this.position = position;
            this.timestamp = timestamp;
        }

        public Vector3f getPosition() {
            return this.position;
        }

        public double getTimestamp() {
            return this.timestamp;
        }
    }
}

