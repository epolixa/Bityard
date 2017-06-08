package com.epolixa.bityard.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockScaffolding extends BlockBase
{
    public BlockScaffolding()
    {
        super(Material.WOOD, MapColor.WOOD, "scaffolding");
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this)
            {
                worldIn.scheduleUpdate(pos.offset(enumfacing), this, 1);
            }
        }
        if (worldIn.getBlockState(pos.up()).getBlock() == this)
        {
            worldIn.scheduleUpdate(pos.up(), this, 1);
        }

        worldIn.destroyBlock(pos, true);
    }

    @Override
    @Deprecated
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
    {
        worldIn.scheduleUpdate(pos.up(), this, 1);
    }
}
