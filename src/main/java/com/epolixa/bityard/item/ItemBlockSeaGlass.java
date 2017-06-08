package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.block.BlockSeaGlass;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSeaGlass extends ItemBlock
{
    public ItemBlockSeaGlass(BlockSeaGlass block)
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
        EnumDyeColor color = EnumDyeColor.byMetadata(stack.getMetadata());
        return block.getUnlocalizedName() + "." + color.toString();
    }
}
