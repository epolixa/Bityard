package com.epolixa.bityard.world;

import com.epolixa.bityard.block.BityardBlocks;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class SpongeGenerator extends WorldGenerator
{
    @Override
    public boolean generate(World world, Random random, BlockPos pos) {
        pos = new BlockPos(pos.getX(), 60, pos.getZ()); // Start at sea level
        while (!world.getBlockState(pos).getMaterial().isSolid()) // Move to sea floor
        {
            pos = pos.add(0, -1, 0);
        }

        IBlockState state = Blocks.SPONGE.getDefaultState().withProperty(PropertyBool.create("wet"), true);
        int radius = random.nextInt(1) + 2;
        int amount = random.nextInt(9) + 9;

        for (int i = 0; i < amount; i++) {
            BlockPos posOffset = pos.add(random.nextInt(radius * 2) - radius, random.nextInt(radius * 2) - radius, random.nextInt(radius * 2) - radius);
            while (posOffset.getDistance(pos.getX(), pos.getY(), pos.getZ()) > radius) {
                posOffset = pos.add(random.nextInt(radius * 2) - radius, random.nextInt(radius * 2) - radius, random.nextInt(radius * 2) - radius);
            }

            world.setBlockState(posOffset, state);
        }

        return true;
    }
}
