/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.joml.Matrix4f
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 *  org.lwjgl.util.vector.Matrix4f
 *  org.lwjgl.util.vector.Quaternion
 */
package me.earth.earthhack.impl.util.math;

import java.nio.FloatBuffer;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.util.vector.Quaternion;

public class GlMathUtil {
    public static Quaternionf normalizedLerp(Quaternionf a, Quaternionf b, float blend) {
        Quaternionf result = new Quaternionf(0.0f, 0.0f, 0.0f, 1.0f);
        float dot = a.w * b.w + a.x * b.x + a.y * b.y + a.z * b.z;
        float blendI = 1.0f - blend;
        if (dot < 0.0f) {
            result.w = blendI * a.w + blend * -b.w;
            result.x = blendI * a.x + blend * -b.x;
            result.y = blendI * a.y + blend * -b.y;
            result.z = blendI * a.z + blend * -b.z;
        } else {
            result.w = blendI * a.w + blend * b.w;
            result.x = blendI * a.x + blend * b.x;
            result.y = blendI * a.y + blend * b.y;
            result.z = blendI * a.z + blend * b.z;
        }
        result.normalize();
        return result;
    }

    public static org.lwjgl.util.vector.Matrix4f jomlMatrixToLwjglMatrix(Matrix4f matrix) {
        return (org.lwjgl.util.vector.Matrix4f)new org.lwjgl.util.vector.Matrix4f().load(matrix.get(FloatBuffer.allocate(16)));
    }

    public static Matrix4f lwjglMatrixToJomlMatrix(org.lwjgl.util.vector.Matrix4f matrix) {
        return new Matrix4f(matrix.m00, matrix.m01, matrix.m02, matrix.m03, matrix.m10, matrix.m11, matrix.m12, matrix.m13, matrix.m20, matrix.m21, matrix.m22, matrix.m23, matrix.m30, matrix.m31, matrix.m32, matrix.m33);
    }

    public static Quaternion jomlQuaternionToLwjglQuaternion(Quaternionf quaternion) {
        return new Quaternion(quaternion.x, quaternion.y, quaternion.z, quaternion.w);
    }

    public static org.lwjgl.util.vector.Matrix4f initScale(Vector3f scale) {
        org.lwjgl.util.vector.Matrix4f matrix = new org.lwjgl.util.vector.Matrix4f();
        matrix.m00 = scale.x;
        matrix.m01 = 0.0f;
        matrix.m02 = 0.0f;
        matrix.m03 = 0.0f;
        matrix.m10 = 0.0f;
        matrix.m11 = scale.y;
        matrix.m12 = 0.0f;
        matrix.m13 = 0.0f;
        matrix.m20 = 0.0f;
        matrix.m21 = 0.0f;
        matrix.m22 = scale.z;
        matrix.m23 = 0.0f;
        matrix.m30 = 0.0f;
        matrix.m31 = 0.0f;
        matrix.m32 = 0.0f;
        matrix.m33 = 1.0f;
        return matrix;
    }

    public static Matrix4f initScaleJoml(Vector3f scale) {
        return new Matrix4f(scale.x, 0.0f, 0.0f, 0.0f, 0.0f, scale.y, 0.0f, 0.0f, 0.0f, 0.0f, scale.z, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }

    public static org.lwjgl.util.vector.Matrix4f toRotationMatrix(Quaternionf quaternion) {
        Vector3f forward = new Vector3f(2.0f * (quaternion.x * quaternion.z - quaternion.w * quaternion.y), 2.0f * (quaternion.y * quaternion.z + quaternion.w * quaternion.x), 1.0f - 2.0f * (quaternion.x * quaternion.x + quaternion.y * quaternion.y));
        Vector3f up = new Vector3f(2.0f * (quaternion.x * quaternion.y + quaternion.w * quaternion.z), 1.0f - 2.0f * (quaternion.x * quaternion.x + quaternion.z * quaternion.z), 2.0f * (quaternion.y * quaternion.z - quaternion.w * quaternion.x));
        Vector3f right = new Vector3f(1.0f - 2.0f * (quaternion.y * quaternion.y + quaternion.z * quaternion.z), 2.0f * (quaternion.x * quaternion.y - quaternion.w * quaternion.z), 2.0f * (quaternion.x * quaternion.z + quaternion.w * quaternion.y));
        org.lwjgl.util.vector.Matrix4f matrix = new org.lwjgl.util.vector.Matrix4f();
        matrix.m00 = right.x;
        matrix.m01 = right.y;
        matrix.m02 = right.z;
        matrix.m03 = 0.0f;
        matrix.m10 = up.x;
        matrix.m11 = up.y;
        matrix.m12 = up.z;
        matrix.m13 = 0.0f;
        matrix.m20 = forward.x;
        matrix.m21 = forward.y;
        matrix.m22 = forward.z;
        matrix.m23 = 0.0f;
        matrix.m30 = 0.0f;
        matrix.m31 = 0.0f;
        matrix.m32 = 0.0f;
        matrix.m33 = 1.0f;
        return matrix;
    }

    public static Matrix4f toRotationMatrixJoml(Quaternionf quaternion) {
        Vector3f forward = new Vector3f(2.0f * (quaternion.x * quaternion.z - quaternion.w * quaternion.y), 2.0f * (quaternion.y * quaternion.z + quaternion.w * quaternion.x), 1.0f - 2.0f * (quaternion.x * quaternion.x + quaternion.y * quaternion.y));
        Vector3f up = new Vector3f(2.0f * (quaternion.x * quaternion.y + quaternion.w * quaternion.z), 1.0f - 2.0f * (quaternion.x * quaternion.x + quaternion.z * quaternion.z), 2.0f * (quaternion.y * quaternion.z - quaternion.w * quaternion.x));
        Vector3f right = new Vector3f(1.0f - 2.0f * (quaternion.y * quaternion.y + quaternion.z * quaternion.z), 2.0f * (quaternion.x * quaternion.y - quaternion.w * quaternion.z), 2.0f * (quaternion.x * quaternion.z + quaternion.w * quaternion.y));
        return new Matrix4f(right.x, right.y, right.z, 0.0f, up.x, up.y, up.z, 0.0f, forward.x, forward.y, forward.z, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }

    public static org.lwjgl.util.vector.Matrix4f initTranslation(Vector3f translation) {
        org.lwjgl.util.vector.Matrix4f matrix = new org.lwjgl.util.vector.Matrix4f();
        matrix.m00 = 0.0f;
        matrix.m01 = 0.0f;
        matrix.m02 = 0.0f;
        matrix.m03 = translation.x;
        matrix.m10 = 0.0f;
        matrix.m11 = 0.0f;
        matrix.m12 = 0.0f;
        matrix.m13 = translation.y;
        matrix.m20 = 0.0f;
        matrix.m21 = 0.0f;
        matrix.m22 = 0.0f;
        matrix.m23 = translation.z;
        matrix.m30 = 0.0f;
        matrix.m31 = 0.0f;
        matrix.m32 = 0.0f;
        matrix.m33 = 1.0f;
        return matrix;
    }

    public static Matrix4f initTranslationJoml(Vector3f translation) {
        return new Matrix4f(0.0f, 0.0f, 0.0f, translation.x, 0.0f, 0.0f, 0.0f, translation.y, 0.0f, 0.0f, 0.0f, translation.z, 0.0f, 0.0f, 0.0f, 1.0f);
    }
}

