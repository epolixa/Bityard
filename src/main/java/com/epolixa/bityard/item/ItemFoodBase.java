package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class ItemFoodBase extends ItemFood implements ItemModelProvider
{
    private String name;

    public ItemFoodBase(String name, int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(CreativeTabs.FOOD);
        this.name = name;
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(this, 0, name);
    }
}
