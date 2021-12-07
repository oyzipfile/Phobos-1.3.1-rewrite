/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.Vec2f
 */
package me.earth.earthhack.impl.util.render.shader;

import java.io.InputStream;
import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.api.util.interfaces.Nameable;
import me.earth.earthhack.impl.util.render.GlShader;
import net.minecraft.util.math.Vec2f;

public class SettingShader
implements Nameable,
Globals {
    private static final long startTime = System.currentTimeMillis();
    private final GlShader shader;
    private long lastTime = -1L;

    public SettingShader(InputStream sourceStream, String name) {
        this.shader = new GlShader(sourceStream, name);
    }

    public SettingShader(String name) {
        this.shader = new GlShader(name);
    }

    public SettingShader(int programId, int vertexShaderId, int fragmentShaderId) {
        this.shader = new GlShader(programId, vertexShaderId, fragmentShaderId);
    }

    public void updateUniforms(int texture) {
        long currentTime = System.currentTimeMillis();
        if (this.lastTime == -1L) {
            this.lastTime = currentTime;
        }
        long delta = currentTime - this.lastTime;
        this.shader.set("sampler", texture);
        this.shader.set("resolution", new Vec2f((float)SettingShader.mc.displayWidth, (float)SettingShader.mc.displayHeight));
        this.shader.set("time", currentTime - startTime);
    }

    public void bind() {
        this.shader.bind();
    }

    public void unbind() {
        this.shader.unbind();
    }

    @Override
    public String getName() {
        return this.shader.getName();
    }
}

