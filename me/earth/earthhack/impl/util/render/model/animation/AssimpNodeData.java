/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.joml.Matrix4f
 */
package me.earth.earthhack.impl.util.render.model.animation;

import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;

public class AssimpNodeData {
    private Matrix4f transformation;
    private String name;
    private List<AssimpNodeData> children = new ArrayList<AssimpNodeData>();

    public AssimpNodeData(String name) {
        this();
        this.name = name;
    }

    public AssimpNodeData() {
        this.transformation = new Matrix4f();
    }

    public Matrix4f getTransformation() {
        return this.transformation;
    }

    public void setTransformation(Matrix4f transformation) {
        this.transformation = transformation;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AssimpNodeData> getChildren() {
        return this.children;
    }

    public void setChildren(List<AssimpNodeData> children) {
        this.children = children;
    }
}

