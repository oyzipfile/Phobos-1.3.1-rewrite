/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  jassimp.AiScene
 */
package me.earth.earthhack.impl.managers.client;

import jassimp.AiScene;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import me.earth.earthhack.impl.Earthhack;
import me.earth.earthhack.impl.util.render.image.GifConverter;
import me.earth.earthhack.impl.util.render.image.GifImage;
import me.earth.earthhack.impl.util.render.image.ImageUtil;
import me.earth.earthhack.impl.util.render.image.NameableImage;
import me.earth.earthhack.impl.util.render.model.IModel;
import me.earth.earthhack.impl.util.render.model.Mesh;
import me.earth.earthhack.impl.util.render.model.ModelUtil;
import me.earth.earthhack.impl.util.render.shader.SettingShader;

public class FileManager {
    private static final File EARTHHACK_ROOT = new File("earthhack/");
    private static final File IMAGES = new File("earthhack/images");
    private static final File MODELS = new File("earthhack/models");
    private static final File SHADERS = new File("earthhack/shaders");
    private final Map<String, GifImage> gifs = new ConcurrentHashMap<String, GifImage>();
    private final Map<String, NameableImage> textures = new ConcurrentHashMap<String, NameableImage>();
    private final Map<String, IModel> models = new ConcurrentHashMap<String, IModel>();
    private final Map<String, SettingShader> shaders = new ConcurrentHashMap<String, SettingShader>();
    private final List<GifImage> gifList = new ArrayList<GifImage>();
    private final List<NameableImage> imageList = new ArrayList<NameableImage>();
    private final List<IModel> modelList = new ArrayList<IModel>();
    private final List<SettingShader> shaderList = new ArrayList<SettingShader>();

    public FileManager() {
        this.init();
    }

    public void init() {
        if (!IMAGES.exists()) {
            IMAGES.mkdir();
        }
        if (!MODELS.exists()) {
            MODELS.mkdir();
        }
        if (!SHADERS.exists()) {
            SHADERS.mkdir();
        }
        this.handleImageDir(IMAGES);
        for (File file : IMAGES.listFiles()) {
            if (!file.isDirectory()) continue;
            this.handleImageDir(file);
        }
        this.handleModelDir(MODELS);
        for (File file : MODELS.listFiles()) {
            if (!file.isDirectory()) continue;
            this.handleModelDir(file);
        }
    }

    private void handleImageDir(File dir) {
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles((dir1, name) -> name.endsWith("gif") || name.endsWith("png") || name.endsWith("jpg") || name.endsWith("jpeg")))) {
                if (file.getName().endsWith("gif")) {
                    if (this.gifs.containsKey(file.getName())) continue;
                    try {
                        GifImage gif = GifConverter.readGifImage(new FileInputStream(file), file.getName());
                        this.gifList.add(gif);
                        this.gifs.put(file.getName(), gif);
                    }
                    catch (IOException e) {
                        Earthhack.getLogger().error("Failed to load gif image " + file.getName() + "!");
                        e.printStackTrace();
                    }
                    continue;
                }
                try {
                    if (this.textures.containsKey(file.getName())) continue;
                    String[] split = file.getName().split("\\.");
                    String format = split[split.length - 1];
                    NameableImage image = new NameableImage(ImageUtil.cacheBufferedImage(ImageUtil.createFlipped(ImageUtil.bufferedImageFromFile(file)), format, file.getName()), file.getName());
                    this.imageList.add(image);
                    this.textures.put(file.getName(), image);
                }
                catch (IOException | NoSuchAlgorithmException e) {
                    Earthhack.getLogger().error("Failed to load image " + file.getName() + "!");
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleModelDir(File dir) {
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isDirectory()) continue;
                try {
                    if (this.models.containsKey(file.getName()) || file.getName().endsWith("gif") || file.getName().endsWith("png") || file.getName().endsWith("jpg") || file.getName().endsWith("jpg")) continue;
                    IModel model = ModelUtil.loadModel(file.getAbsolutePath(), file.getParent(), true);
                    model.setName(file.getName());
                    this.modelList.add(model);
                    this.models.put(file.getName(), model);
                }
                catch (IOException e) {
                    Earthhack.getLogger().error("Failed to load model: " + file.getName() + "!");
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleShaderDir(File dir) {
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                try {
                    if (this.shaders.containsKey(file.getName()) || !file.getName().endsWith(".shader")) continue;
                    SettingShader shader = new SettingShader(new FileInputStream(file), file.getName());
                    this.shaders.put(file.getName(), shader);
                    this.shaderList.add(shader);
                }
                catch (IOException e) {
                    Earthhack.getLogger().error("Failed to shader model: " + file.getName() + "!");
                    e.printStackTrace();
                }
            }
        }
    }

    public GifImage getGif(String image) {
        return this.gifs.get(image);
    }

    public NameableImage getImage(String image) {
        return this.textures.get(image);
    }

    public IModel getModel(String model) {
        return this.models.get(model);
    }

    public List<GifImage> getGifs() {
        return this.gifList;
    }

    public List<NameableImage> getImages() {
        return this.imageList;
    }

    public List<IModel> getModels() {
        return this.modelList;
    }

    public List<SettingShader> getShaders() {
        return this.shaderList;
    }

    public IModel getInitialModel() {
        if (!this.modelList.isEmpty()) {
            return this.modelList.get(0);
        }
        return new IModel(){

            @Override
            public void setupMesh(Mesh mesh) {
            }

            @Override
            public Mesh[] genMeshes(AiScene scene) {
                return new Mesh[0];
            }

            @Override
            public void render(double x, double y, double z, double partialTicks) {
            }

            @Override
            public Mesh[] getMeshes() {
                return new Mesh[0];
            }

            @Override
            public String getName() {
                return "None!";
            }

            @Override
            public void setName(String name) {
            }
        };
    }

    public NameableImage getInitialImage() {
        if (!this.imageList.isEmpty()) {
            return this.imageList.get(0);
        }
        return new NameableImage(null, "None!");
    }

    public GifImage getInitialGif() {
        if (!this.gifList.isEmpty()) {
            return this.gifList.get(0);
        }
        return new GifImage(new ArrayList<BufferedImage>(), 0, "None!");
    }

    public SettingShader getInitialShader() {
        if (!this.shaderList.isEmpty()) {
            return this.shaderList.get(0);
        }
        return new SettingShader("default");
    }
}

