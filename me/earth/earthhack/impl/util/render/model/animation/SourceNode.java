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

public class SourceNode {
    private SourceNode parent;
    private String name;
    private int[] meshes;
    private Matrix4f transformation;
    private final List<SourceNode> children = new ArrayList<SourceNode>();

    public SourceNode(SourceNode parent, String name, int[] meshes, Matrix4f transformation) {
        this.parent = parent;
        this.name = name;
        this.meshes = meshes;
        this.transformation = transformation;
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public Matrix4f getTransformation() {
        return this.transformation;
    }

    public int[] getMeshes() {
        return this.meshes;
    }

    public String getName() {
        return this.name;
    }

    public SourceNode getParent() {
        return this.parent;
    }

    public void addChild(SourceNode child) {
        this.children.add(child);
    }

    public List<SourceNode> getChildren() {
        return this.children;
    }
}

