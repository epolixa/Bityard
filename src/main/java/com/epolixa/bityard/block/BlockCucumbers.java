package com.epolixa.bityard.block;

import com.epolixa.bityard.item.BityardItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.item.Item;

public class BlockCucumbers extends BlockCrops
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
}
