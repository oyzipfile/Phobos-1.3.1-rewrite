/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL20
 */
package me.earth.earthhack.impl.util.render.shader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import me.earth.earthhack.api.util.interfaces.Nameable;
import me.earth.earthhack.impl.Earthhack;
import org.lwjgl.opengl.GL20;

public abstract class Shader
implements Nameable {
    private int program;
    private int vertex;
    private int fragment;
    private boolean initialized;
    private final String name;
    private final Map<String, Integer> uniformMap = new HashMap<String, Integer>();

    public Shader(String name, String path, String[] uniforms) {
        this(name, path);
        for (String uniformName : uniforms) {
            int uniform = GL20.glGetUniformLocation((int)this.program, (CharSequence)uniformName);
            if (uniform <= -1) continue;
            this.uniformMap.put(uniformName, uniform);
        }
    }

    public Shader(String name, String path) {
        String source;
        this.name = name;
        try {
            int read;
            InputStream stream = Shader.class.getResourceAsStream("/shaders/" + path);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            while ((read = stream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, read);
            }
            source = new String(out.toByteArray(), StandardCharsets.UTF_8);
        }
        catch (IOException | NullPointerException e) {
            Earthhack.getLogger().error("Shader " + name + " failed to read.");
            e.printStackTrace();
            return;
        }
        boolean vertexShader = false;
        StringBuilder vertexBuilder = new StringBuilder();
        StringBuilder fragmentBuilder = new StringBuilder();
        for (String line : source.split("\n")) {
            if (line.contains("#shader vertex")) {
                vertexShader = true;
                continue;
            }
            if (line.contains("#shader fragment")) {
                vertexShader = false;
                continue;
            }
            if (vertexShader) {
                vertexBuilder.append(line);
                continue;
            }
            fragmentBuilder.append(line);
        }
        this.vertex = GL20.glCreateShader((int)35633);
        this.fragment = GL20.glCreateShader((int)35632);
        GL20.glShaderSource((int)this.vertex, (CharSequence)vertexBuilder);
        GL20.glShaderSource((int)this.fragment, (CharSequence)fragmentBuilder);
        GL20.glCompileShader((int)this.vertex);
        GL20.glCompileShader((int)this.fragment);
        if (GL20.glGetShaderi((int)this.vertex, (int)35713) == 0) {
            Earthhack.getLogger().error("Shader " + name + "'s vertex shader failed to compile! Reason: " + GL20.glGetShaderInfoLog((int)this.vertex, (int)1024));
            return;
        }
        if (GL20.glGetShaderi((int)this.vertex, (int)35713) == 0) {
            Earthhack.getLogger().error("Shader " + name + "'s fragment shader failed to compile! Reason: " + GL20.glGetShaderInfoLog((int)this.fragment, (int)1024));
            return;
        }
        this.program = GL20.glCreateProgram();
        GL20.glAttachShader((int)this.program, (int)this.vertex);
        GL20.glAttachShader((int)this.program, (int)this.fragment);
        GL20.glLinkProgram((int)this.program);
        if (GL20.glGetProgrami((int)this.program, (int)35714) == 0) {
            Earthhack.getLogger().error("Shader " + name + "failed to link! Reason: " + GL20.glGetProgramInfoLog((int)this.fragment, (int)1024));
            return;
        }
        GL20.glDetachShader((int)this.program, (int)this.vertex);
        GL20.glDetachShader((int)this.program, (int)this.fragment);
        GL20.glValidateProgram((int)this.program);
        if (GL20.glGetProgrami((int)this.program, (int)35715) == 0) {
            Earthhack.getLogger().error("Shader " + name + "failed to validate! Reason: " + GL20.glGetProgramInfoLog((int)this.fragment, (int)1024));
            return;
        }
        this.initialized = true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public boolean isInitialized() {
        return this.initialized;
    }
}

