/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  io.netty.buffer.Unpooled
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.nbt.NBTTagString
 *  net.minecraft.network.PacketBuffer
 *  net.minecraft.network.play.client.CPacketCustomPayload
 *  net.minecraft.util.EnumHand
 */
package me.earth.earthhack.impl.commands;

import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Scanner;
import me.earth.earthhack.api.command.Command;
import me.earth.earthhack.api.util.interfaces.Globals;
import me.earth.earthhack.impl.gui.chat.util.ColorEnum;
import me.earth.earthhack.impl.util.network.NetworkUtil;
import me.earth.earthhack.impl.util.text.ChatUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.util.EnumHand;

public class BookCommand
extends Command
implements Globals {
    private static final int MAX_CHARACTERS_PER_PAGE = 256;
    private static final int MAX_PAGES = 50;
    public static final String NUMBER_TOKEN = "\\{NUMBER\\}";
    public static final String NEW_PAGE = ":PAGE:";
    private static final Collection<Character> CHARS_NO_REPEATING = Lists.newArrayList((Object[])new Character[]{Character.valueOf(' '), Character.valueOf('\n'), Character.valueOf('\t'), Character.valueOf('\r')});
    private int page = 0;

    public BookCommand() {
        super(new String[][]{{"book"}, {"file"}});
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 2) {
            this.page = 0;
            File file = new File(args[1]);
            if (!file.exists() || file.isDirectory()) {
                ChatUtil.sendMessage(ColorEnum.Red + "File does not exist or is a directory!");
                return;
            }
            ItemStack book = BookCommand.mc.player.getHeldItem(EnumHand.MAIN_HAND);
            if (book.getItem() != Items.WRITABLE_BOOK) {
                ChatUtil.sendMessage(ColorEnum.Red + "Not holding a book!");
                return;
            }
            String contents = this.loadFile(file);
            Scanner scanner = this.newScanner(contents);
            mc.addScheduledTask(() -> {
                BookCommand.mc.player.openBook(BookCommand.mc.player.getHeldItemMainhand(), EnumHand.MAIN_HAND);
                this.sendBook(BookCommand.mc.player.getHeldItemMainhand(), scanner);
                mc.displayGuiScreen(null);
            });
        }
    }

    private static String parseText(String text, boolean wrap) {
        int i;
        text = text.replace('\r', '\n').replace('\t', ' ').replace("\u0000", "");
        StringBuilder builder = new StringBuilder();
        char next = '\u0000';
        int ls = -1;
        int p = i = 0;
        while (i < text.length()) {
            char last = next;
            next = text.charAt(i);
            if (p == 0) {
                builder.append(NEW_PAGE);
            }
            if (next == ' ') {
                ls = i;
            }
            if (CHARS_NO_REPEATING.contains(Character.valueOf(next)) && CHARS_NO_REPEATING.contains(Character.valueOf(last))) {
                --p;
            } else {
                int ns;
                int d;
                if (wrap && ls != -1 && last == ' ' && (d = (ns = text.indexOf(32, i)) - ls) < 256 && p + d > 256) {
                    builder.append(NEW_PAGE);
                    p = 0;
                }
                builder.append(next);
            }
            ++i;
            ++p;
            p %= 256;
        }
        return builder.toString();
    }

    private String loadFile(File file) throws RuntimeException {
        String text;
        Path data = file.getAbsoluteFile().toPath();
        if (!Files.exists(data, new LinkOption[0])) {
            throw new RuntimeException("File not found");
        }
        if (!Files.isRegularFile(data, new LinkOption[0])) {
            throw new RuntimeException("Not a file type");
        }
        try {
            text = new String(Files.readAllBytes(data), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to read file");
        }
        String name = data.getFileName().toString();
        if (name.endsWith(".txt") || name.endsWith(".book")) {
            return text;
        }
        throw new RuntimeException("File is not a .txt or .book type");
    }

    private Scanner newScanner(String contents) {
        return new Scanner(contents).useDelimiter(NEW_PAGE);
    }

    private void sendBook(ItemStack stack, Scanner parser) {
        NBTTagList pages = new NBTTagList();
        for (int i = 0; i < 50 && parser.hasNext(); ++i) {
            pages.appendTag((NBTBase)new NBTTagString(parser.next().trim()));
            ++this.page;
        }
        if (stack.hasTagCompound()) {
            stack.getTagCompound().setTag("pages", (NBTBase)pages);
        } else {
            stack.setTagInfo("pages", (NBTBase)pages);
        }
        stack.setTagInfo("author", (NBTBase)new NBTTagString(BookCommand.mc.player.getName()));
        stack.setTagInfo("title", (NBTBase)new NBTTagString("megyn own u".trim()));
        PacketBuffer buff = new PacketBuffer(Unpooled.buffer());
        buff.writeItemStack(stack);
        NetworkUtil.send(new CPacketCustomPayload("MC|BSign", buff));
    }

    public int getBook() {
        return this.page > 0 ? (int)Math.ceil((double)this.page / 50.0) : 0;
    }
}

