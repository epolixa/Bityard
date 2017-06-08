package com.epolixa.bityard.item;

import com.epolixa.bityard.block.BlockObsidianBrick;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockObsidianBrick extends ItemBlock
{
    public ItemBlockObsidianBrick(BlockObsidianBrick block)
    {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        setRegistryName(block.getRegistryName());
    }

    @Override
    public int getMetadata(int metadata)
    {
        return metadata;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (stack.getMetadata() == 1)
        {
            return "tile.carved_obsidian";
        }
        else
        {
            return "tile.obsidian_brick";
        }
    }
}
