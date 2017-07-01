package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemCornDog extends ItemFood implements ItemModelProvider
{
    public ItemCornDog()
    {
        super(13, 18.8f, true);
        setUnlocalizedName("corn_dog");
        setRegistryName("corn_dog");
        setCreativeTab(CreativeTabs.FOOD);
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(this, 0, "corn_dog");
    }

    @Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        if (!worldIn.isRemote)
        {
            worldIn.spawnEntity(
                    new EntityItem(
                            worldIn,
                            entityLiving.posX,
                            entityLiving.posY,
                            entityLiving.posZ,
                            new ItemStack(Items.STICK)
                    )
            );
        }
        return stack;
    }
}
