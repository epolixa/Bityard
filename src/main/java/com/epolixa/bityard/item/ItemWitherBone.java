package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWitherBone extends ItemBase implements ItemModelProvider
{
    public ItemWitherBone()
    {
        super("wither_bone");
        this.setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(this, 0, "wither_bone");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {


        return EnumActionResult.SUCCESS;
    }

}