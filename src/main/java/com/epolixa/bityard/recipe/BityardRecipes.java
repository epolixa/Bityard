package com.epolixa.bityard.recipe;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.block.BityardBlocks;
import com.epolixa.bityard.item.BityardItems;
import com.epolixa.bityard.item.ItemFish;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.village.VillageCollection;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.List;

public class BityardRecipes
{
    public static void init()
    {
        /* REMOVED/REPLACED */
        List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < recipeList.size(); i++)
        {
            IRecipe recipe = recipeList.get(i);
            if (recipe.getRecipeOutput().getItem() == Items.FISHING_ROD) recipeList.remove(i--);
            else if (recipe.getRecipeOutput().getItem() == Items.CARROT_ON_A_STICK) recipeList.remove(i--);
        }

        /* Items */
        GameRegistry.addRecipe(new ItemStack(Items.LEATHER), new Object[] {"ww", "ww", 'w', BityardItems.BAT_WING});
        GameRegistry.addRecipe(new ItemStack(BityardItems.FISHING_ROD), new Object[] {"eew", "ews", "wei", 'e', Items.AIR, 'w', Items.STICK, 's', Items.STRING, 'i', Items.field_191525_da});
        GameRegistry.addShapelessRecipe(new ItemStack(Items.CARROT_ON_A_STICK), BityardItems.FISHING_ROD, Items.CARROT);
        //GameRegistry.addRecipe(new ItemStack(BityardItems.BUG_NET), new Object[] {"ess", "ews", "ewe", 'e', Items.AIR, 'w', Items.STICK, 's', Items.STRING});
        GameRegistry.addRecipe(new ItemStack(BityardItems.CHEESE_PIZZA, 6), new Object[] {"ctc", "bbb", 'c', BityardItems.CHEESE, 't', BityardItems.TOMATO, 'b', Items.BREAD});
        GameRegistry.addRecipe(new ItemStack(BityardItems.VEGGIE_PIZZA, 6), new Object[] {"ctv", "bbb", 'c', BityardItems.CHEESE, 't', BityardItems.TOMATO, 'v', Items.CARROT, 'b', Items.BREAD});
        GameRegistry.addRecipe(new ItemStack(BityardItems.VEGGIE_PIZZA, 6), new Object[] {"ctv", "bbb", 'c', BityardItems.CHEESE, 't', BityardItems.TOMATO, 'v', BityardItems.CUCUMBER, 'b', Items.BREAD});
        GameRegistry.addRecipe(new ItemStack(BityardItems.BURGER), new Object[] {"ebe", "cst", "ebe", 'e', Items.AIR, 'b', Items.BREAD, 'c', BityardItems.CHEESE, 's', Items.COOKED_BEEF, 't', BityardItems.TOMATO});
        GameRegistry.addRecipe(new ItemStack(BityardItems.CHICKEN_POT_PIE), new Object[] {"pcv", "www", 'w', Items.WHEAT, 'c', Items.CHICKEN, 'p', Items.POTATO, 'v', Items.CARROT});

        GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.CORN_DOG), Items.STICK, Items.COOKED_PORKCHOP, Items.BREAD);
        GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.HARP), Blocks.NOTEBLOCK, Blocks.DIRT, Items.STICK, Items.STRING, Items.LEATHER);
        GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.BASS_GUITAR), Blocks.NOTEBLOCK, Blocks.LOG, Items.STICK, Items.STRING, Items.LEATHER);
        GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.SNARE_DRUM), Blocks.NOTEBLOCK, Blocks.GRAVEL, Items.STICK, Items.STRING, Items.LEATHER);
        GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.CLICK_STICKS), Blocks.NOTEBLOCK, Blocks.GLASS, Items.STICK, Items.STRING, Items.LEATHER);
        GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.BASS_DRUM), Blocks.NOTEBLOCK, Blocks.STONE, Items.STICK, Items.STRING, Items.LEATHER);
        GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.PICKLE), BityardItems.CUCUMBER, Blocks.BROWN_MUSHROOM, Items.SUGAR);
        GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.CHEESE), Items.MILK_BUCKET.setContainerItem(Items.BUCKET));
        GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.WITHER_BONEMEAL, 3), BityardItems.WITHER_BONE);
        GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.WITHER_BONEMEAL, 9), BityardBlocks.WITHER_BONE_BLOCK);
        GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.ICE_CREAM), Items.BOWL, Blocks.ICE, Items.MILK_BUCKET.setContainerItem(Items.BUCKET), Items.SUGAR);
        GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.ICE_CREAM_CHOCOLATE), Items.BOWL, Blocks.ICE, Items.MILK_BUCKET.setContainerItem(Items.BUCKET), Items.SUGAR, new ItemStack(Items.DYE, 1, 3));

        for (int i = 0; i < ItemFish.FishType.values().length; i++)
        {
            int foodAmount = ItemFish.FishType.byMetadata(i).getFoodAmount();
            if (foodAmount > 0 && i != 15)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(BityardItems.RAW_FISH, foodAmount), new ItemStack(BityardItems.FISH, 1, i));
            }
        }
        GameRegistry.addShapelessRecipe(new ItemStack(Items.GOLD_NUGGET), new ItemStack(BityardItems.FISH, 1, 15));

        GameRegistry.addSmelting(Items.EGG, new ItemStack(BityardItems.COOKED_EGG), 0.35f);
        GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(BityardItems.ZOMBIE_JERKY), 0.35f);
        GameRegistry.addSmelting(BityardItems.RAW_FISH, new ItemStack(BityardItems.COOKED_FISH), 0.35f);

        /* Blocks */
        GameRegistry.addRecipe(new ItemStack(BityardBlocks.WITHER_BONE_BLOCK), new Object[] {"www", "www", "www", 'w', BityardItems.WITHER_BONEMEAL});
        GameRegistry.addRecipe(new ItemStack(BityardBlocks.OBSIDIAN_BRICK, 4), new Object[] {"oo", "oo", 'o', Blocks.OBSIDIAN});
        GameRegistry.addRecipe(new ItemStack(BityardBlocks.OBSIDIAN_BRICK, 2, 1), new Object[] {"o", "o", 'o', Blocks.OBSIDIAN});
        GameRegistry.addRecipe(new ItemStack(BityardBlocks.REINFORCED_GLASS), new Object[] {"iii", "igi", "iii", 'i', Items.field_191525_da, 'g', Blocks.GLASS});
        GameRegistry.addRecipe(new ItemStack(BityardBlocks.SCAFFOLDING), new Object[] {"sss", "sss", "sss", 's', Items.STICK});

        GameRegistry.addSmelting(Blocks.SOUL_SAND, new ItemStack(BityardBlocks.SOUL_GLASS), 0.35f);
    }
}
