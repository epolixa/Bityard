package com.epolixa.bityard.block;

import com.epolixa.bityard.item.ItemBlockObsidianBrick;
import com.epolixa.bityard.item.ItemBlockSeaGlass;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BityardBlocks
{
    public static BlockSeaGlass SEA_GLASS;
    public static BlockCucumbers CUCUMBERS;
    public static BlockTomatoes TOMATOES;
    public static BlockObsidianBrick OBSIDIAN_BRICK;
    public static BlockReinforcedGlass REINFORCED_GLASS;
    public static BlockSoulGlass SOUL_GLASS;
    public static BlockWitherBone WITHER_BONE_BLOCK;
    public static BlockScaffolding SCAFFOLDING;

    public static void init()
    {
        SEA_GLASS = new BlockSeaGlass(); SEA_GLASS = register(SEA_GLASS, new ItemBlockSeaGlass(SEA_GLASS));
        CUCUMBERS = register(new BlockCucumbers());
        TOMATOES = register(new BlockTomatoes());
        OBSIDIAN_BRICK = new BlockObsidianBrick(); OBSIDIAN_BRICK = register(OBSIDIAN_BRICK, new ItemBlockObsidianBrick(OBSIDIAN_BRICK));
        REINFORCED_GLASS = register(new BlockReinforcedGlass());
        SOUL_GLASS = register(new BlockSoulGlass());
        WITHER_BONE_BLOCK = register(new BlockWitherBone());
        SCAFFOLDING = register(new BlockScaffolding());
    }

    private static <T extends Block> T register(T block, ItemBlock itemBlock)
    {
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);

        if (block instanceof BlockBase)
        {
            ((BlockBase)block).registerItemModel(itemBlock);
        }

        return block;
    }

    private static <T extends Block> T register(T block)
    {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }
}
