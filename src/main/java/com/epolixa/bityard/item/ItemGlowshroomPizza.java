package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemGlowshroomPizza extends ItemFood implements ItemModelProvider
{
    public ItemGlowshroomPizza()
    {
        super(5, 5.2f, false);
        this.setUnlocalizedName("glowshroom_pizza");
        this.setRegistryName("glowshroom_pizza");
        this.setCreativeTab(CreativeTabs.FOOD);
        this.setPotionEffect(new PotionEffect(MobEffects.GLOWING, 100, 0), 0.5F);
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(this, 0, "glowshroom_pizza");
    }
}
