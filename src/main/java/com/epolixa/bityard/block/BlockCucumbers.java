package com.epolixa.bityard.block;

import com.epolixa.bityard.item.BityardItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCucumbers extends BlockCrops implements IGrowable
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);

    public BlockCucumbers()
    {
        setUnlocalizedName("cucumbers");
        setRegistryName("cucumbers");
        this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), Integer.valueOf(0)));
    }

    @Override
    protected Item getSeed()
    {
        return BityardItems.CUCUMBER_SEEDS;
    }

    @Override
    protected Item getCrop()
    {
        return BityardItems.CUCUMBER;
    }

    @Override
    public int getMaxAge()
    {
        return 7;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        return worldIn.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND;
    }
}
