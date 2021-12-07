/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  jassimp.AiScene
 */
package me.earth.earthhack.impl.util.render.model;

import jassimp.AiScene;
import me.earth.earthhack.api.util.interfaces.Nameable;
import me.earth.earthhack.impl.util.render.model.Mesh;

public interface IModel
extends Nameable {
    public void setupMesh(Mesh var1);

    public Mesh[] genMeshes(AiScene var1);

    public void render(double var1, double var3, double var5, double var7);

    public Mesh[] getMeshes();

    default public void setupMeshes() {
        for (Mesh mesh : this.getMeshes()) {
            this.setupMesh(mesh);
        }
    }

    public void setName(String var1);
}

