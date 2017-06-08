package com.epolixa.bityard.block;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.item.ItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements ItemModelProvider
{
    protected String name;

    public BlockBase(Material material, String name)
    {
        super(material);
        this.name = name;
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
    }

    public BlockBase(Material material, MapColor mapColor, String name)
    {
        super(material, mapColor);
        this.name = name;
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
    }

    @Override
    public BlockBase setCreativeTab(CreativeTabs tab)
    {
        super.setCreativeTab(tab);
        return this;
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(item, 0, name);
    }
}
