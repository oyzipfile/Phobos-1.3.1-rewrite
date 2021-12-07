/*
 * Decompiled with CFR 0.150.
 */
package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.command.Command;
import me.earth.earthhack.api.command.Completer;
import me.earth.earthhack.api.command.PossibleInputs;
import me.earth.earthhack.impl.commands.util.CommandDescriptions;
import me.earth.earthhack.impl.managers.Managers;
import me.earth.earthhack.impl.modules.client.commands.Commands;
import me.earth.earthhack.impl.util.text.ChatUtil;

public class PrefixCommand
extends Command {
    public PrefixCommand() {
        super(new String[][]{{"prefix"}, {"symbol"}});
        CommandDescriptions.register(this, "Manage the clients prefix.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 1) {
            String prefix = args[1];
            Commands.setPrefix(prefix);
            Managers.CHAT.sendDeleteMessage("Prefix has been set to: \u00a7b" + prefix + "\u00a7f" + ".", "Prefix", 3000);
        } else {
            ChatUtil.sendMessage("\u00a7cPlease specify a prefix.");
        }
    }

    @Override
    public PossibleInputs getPossibleInputs(String[] args) {
        if (args.length > 1) {
            return PossibleInputs.empty();
        }
        return super.getPossibleInputs(args);
    }

    @Override
    public Completer onTabComplete(Completer completer) {
        if (completer.getArgs().length > 1 || completer.getArgs()[0].equalsIgnoreCase("prefix")) {
            return completer;
        }
        return super.onTabComplete(completer);
    }
}

