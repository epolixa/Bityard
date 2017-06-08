package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.block.BityardBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;

public class ItemTomatoSeeds extends ItemSeeds implements ItemModelProvider
{
    public final Block crops;

    public ItemTomatoSeeds()
    {
        super(BityardBlocks.TOMATOES, Blocks.FARMLAND);
        setUnlocalizedName("tomato_seeds");
        setRegistryName("tomato_seeds");
        crops = BityardBlocks.TOMATOES;
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(this, 0, "tomato_seeds");
    }
}
