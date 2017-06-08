package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.block.BityardBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.math.BlockPos;

public class ItemCucumberSeeds extends ItemSeeds implements ItemModelProvider
{
    public final Block crops;

    public ItemCucumberSeeds()
    {
        super(BityardBlocks.CUCUMBERS, Blocks.FARMLAND);
        setUnlocalizedName("cucumber_seeds");
        setRegistryName("cucumber_seeds");
        crops = BityardBlocks.CUCUMBERS;
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(this, 0, "cucumber_seeds");
    }
}
