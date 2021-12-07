/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.crafting.CraftingManager
 *  net.minecraft.item.crafting.IRecipe
 */
package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.api.command.PossibleInputs;
import me.earth.earthhack.impl.commands.packet.AbstractArgument;
import me.earth.earthhack.impl.commands.packet.exception.ArgParseException;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class RecipeArgument
extends AbstractArgument<IRecipe> {
    public RecipeArgument() {
        super(IRecipe.class);
    }

    @Override
    public IRecipe fromString(String argument) throws ArgParseException {
        int id;
        try {
            id = Integer.parseInt(argument);
        }
        catch (Exception e) {
            throw new ArgParseException("Could not parse Recipe-ID to integer: " + argument + "!");
        }
        IRecipe recipe = CraftingManager.getRecipeById((int)id);
        if (recipe == null) {
            throw new ArgParseException("No Recipe found for id: " + id + "!");
        }
        return recipe;
    }

    @Override
    public PossibleInputs getPossibleInputs(String argument) {
        if (argument == null || argument.isEmpty()) {
            return new PossibleInputs("", "<Recipe-ID>");
        }
        return PossibleInputs.empty();
    }
}

