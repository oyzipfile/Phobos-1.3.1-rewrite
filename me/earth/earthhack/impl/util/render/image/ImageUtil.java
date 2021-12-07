/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.renderer.texture.ITextureObject
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL30
 *  org.newdawn.slick.Animation
 *  org.newdawn.slick.Image
 *  org.newdawn.slick.SlickException
 *  org.newdawn.slick.imageout.ImageWriterFactory
 *  org.newdawn.slick.opengl.TextureLoader
 */
package me.earth.earthhack.impl.util.render.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.DataBufferUShort;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.impl.core.ducks.render.ITextureManager;
import me.earth.earthhack.impl.util.render.image.EfficientTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageWriterFactory;
import org.newdawn.slick.opengl.TextureLoader;

public class ImageUtil
implements Globals {
    public static BufferedImage createFlipped(BufferedImage image) {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1.0, -1.0));
        at.concatenate(AffineTransform.getTranslateInstance(0.0, -image.getHeight()));
        return ImageUtil.createTransformed(image, at);
    }

    public static BufferedImage createTransformed(BufferedImage image, AffineTransform at) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), 2);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage((Image)image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public static BufferedImage bufferedImageFromFile(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        String format = file.getName().split("\\.")[1];
        BufferedImage bimage = new BufferedImage(((Image)image).getWidth(null), ((Image)image).getHeight(null), 2);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage((Image)image, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    public static EfficientTexture cacheBufferedImage(BufferedImage image, String format, String name) throws NoSuchAlgorithmException, IOException {
        EfficientTexture texture = new EfficientTexture(image);
        ResourceLocation location = ((ITextureManager)mc.getTextureManager()).getEfficientTextureResourceLocation(name, texture);
        mc.getTextureManager().loadTexture(location, (ITextureObject)texture);
        return texture;
    }

    public static void bindImage(BufferedImage bufferedImage) throws IOException, NoSuchAlgorithmException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String format = ImageUtil.getImageFormat(bufferedImage);
        if (format == null) {
            throw new IOException();
        }
        ImageIO.write((RenderedImage)bufferedImage, format, outputStream);
        byte[] data = outputStream.toByteArray();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        byte[] hash = md.digest();
        String name = new String(hash);
        DynamicTexture texture = new DynamicTexture(bufferedImage);
        ResourceLocation location = mc.getTextureManager().getDynamicTextureLocation(name, texture);
        mc.getTextureManager().bindTexture(location);
    }

    public static void bindImage(BufferedImage bufferedImage, String format) throws IOException, NoSuchAlgorithmException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (format == null) {
            throw new IOException();
        }
        ImageIO.write((RenderedImage)bufferedImage, format, outputStream);
        byte[] data = outputStream.toByteArray();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        byte[] hash = md.digest();
        String name = new String(hash);
        DynamicTexture texture = new DynamicTexture(bufferedImage);
        ResourceLocation location = mc.getTextureManager().getDynamicTextureLocation(name, texture);
        mc.getTextureManager().bindTexture(location);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getImageFormat(BufferedImage image) throws IOException {
        ImageInputStream stream = ImageIO.createImageInputStream(image);
        Iterator<ImageReader> iter = ImageIO.getImageReaders(stream);
        if (!iter.hasNext()) {
            return null;
        }
        ImageReader reader = iter.next();
        ImageReadParam param = reader.getDefaultReadParam();
        reader.setInput(stream, true, true);
        try {
            BufferedImage bi = reader.read(0, param);
            String string = reader.getFormatName();
            return string;
        }
        finally {
            reader.dispose();
            stream.close();
        }
    }

    public static org.newdawn.slick.Image bufferedImageToSlickImage(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage)image, format, baos);
        ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
        return new org.newdawn.slick.Image(TextureLoader.getTexture((String)format, (InputStream)is));
    }

    public static BufferedImage slickImageToBufferedImage(org.newdawn.slick.Image image, String format) throws SlickException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageWriterFactory.getWriterForFormat((String)format).saveImage(image, format, (OutputStream)baos, true);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return ImageIO.read(bais);
    }

    public static BufferedImage getCurrentAnimationFrame(Animation animation) throws SlickException, IOException {
        return ImageUtil.slickImageToBufferedImage(animation.getCurrentFrame(), "GIF");
    }

    public static int loadImage(BufferedImage image) throws IOException, URISyntaxException {
        ByteBuffer buffer = ImageUtil.bufferedImageToByteBuffer(image);
        int texture = GL11.glGenTextures();
        GL11.glBindTexture((int)3553, (int)texture);
        GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)image.getWidth(), (int)image.getHeight(), (int)0, (int)6408, (int)5121, (ByteBuffer)buffer);
        GL30.glGenerateMipmap((int)3553);
        GL11.glTexParameteri((int)3553, (int)10242, (int)10497);
        GL11.glTexParameteri((int)3553, (int)10243, (int)10497);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9987);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        return texture;
    }

    public static ByteBuffer bufferedImageToByteBuffer(BufferedImage image) {
        DataBuffer dataBuffer = image.getRaster().getDataBuffer();
        if (dataBuffer instanceof DataBufferByte) {
            byte[] pixelData = ((DataBufferByte)dataBuffer).getData();
            ByteBuffer byteBuffer = ByteBuffer.wrap(pixelData);
        } else if (dataBuffer instanceof DataBufferUShort) {
            short[] pixelData = ((DataBufferUShort)dataBuffer).getData();
            ByteBuffer byteBuffer = ByteBuffer.allocate(pixelData.length * 2);
            byteBuffer.asShortBuffer().put(ShortBuffer.wrap(pixelData));
        } else if (dataBuffer instanceof DataBufferShort) {
            short[] pixelData = ((DataBufferShort)dataBuffer).getData();
            ByteBuffer byteBuffer = ByteBuffer.allocate(pixelData.length * 2);
            byteBuffer.asShortBuffer().put(ShortBuffer.wrap(pixelData));
        } else if (dataBuffer instanceof DataBufferInt) {
            int[] pixelData = ((DataBufferInt)dataBuffer).getData();
            ByteBuffer byteBuffer = ByteBuffer.allocate(pixelData.length * 4);
            byteBuffer.asIntBuffer().put(IntBuffer.wrap(pixelData));
        } else {
            throw new IllegalArgumentException("Not implemented for data buffer type: " + dataBuffer.getClass());
        }
        return null;
    }
}

