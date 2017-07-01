package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.block.BityardBlocks;
import com.epolixa.bityard.block.BlockWitherBone;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemWitherBonemeal extends ItemBase implements ItemModelProvider
{
    public ItemWitherBonemeal()
    {
        super("wither_bonemeal");
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(this, 0, "wither_bonemeal");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (isGrass(worldIn, pos))
        {
            spreadOnGrass(worldIn, pos, player, 3);
            player.getHeldItem(hand).setCount(player.getHeldItem(hand).getCount() - 1);
        }

        return EnumActionResult.SUCCESS;
    }

    private boolean isGrass(World world, BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.GRASS ||
                world.getBlockState(pos).getBlock() == Blocks.MYCELIUM;
    }

    private void spreadOnGrass(World world, BlockPos pos, EntityPlayer player, int dist)
    {
        if (isGrass(world, pos))
        {
            world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            if (!world.isRemote)
            {
                WorldServer worldServer = (WorldServer) world;
                for (int i = itemRand.nextInt(2) + 1; i > 0; i--)
                {
                    worldServer.spawnParticle(
                            EnumParticleTypes.SMOKE_NORMAL,
                            pos.getX() + 0.5 + ((itemRand.nextInt(10) - 5)*0.1),
                            pos.getY() + 1 + ((itemRand.nextInt(4))*0.1),
                            pos.getZ() + 0.5 + ((itemRand.nextInt(10) - 5)*0.1),
                            1,
                            0,
                            0,
                            0,
                            0,
                            new int[] {}
                    );
                }
            }
            if (dist > 0)
            {
                for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL)
                {
                    spreadOnGrass(world, pos.offset(facing), player, dist - 1);
                }
            }
        }
    }
}
