package com.epolixa.bityard.world;

import com.epolixa.bityard.block.BityardBlocks;
import com.epolixa.bityard.block.BlockSeaGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class SeaGlassGenerator extends WorldGenerator
{
    @Override
    public boolean generate(World world, Random random, BlockPos pos)
    {
        pos = new BlockPos(pos.getX(), 60, pos.getZ()); // Start at sea level
        while (!world.getBlockState(pos).getMaterial().isSolid()) // Move to sea floor
        {
            pos = pos.add(0, -1, 0);
        }

        IBlockState state;
        int radius;
        int amount;

        // normal clump
        if (random.nextInt(100) < 97)
        {
            state = BityardBlocks.SEA_GLASS.getStateFromMeta(random.nextInt(16));
            radius = random.nextInt(3) + 1;
            amount = random.nextInt(9) + 9;

            for (int i = 0; i < amount; i++)
            {
                BlockPos posOffset = pos.add(random.nextInt(radius * 2) - radius, random.nextInt(2), random.nextInt(radius * 2) - radius);
                while (posOffset.getDistance(pos.getX(), pos.getY(),pos.getZ()) > radius)
                {
                    posOffset = pos.add(random.nextInt(radius * 2) - radius, random.nextInt(2), random.nextInt(radius * 2) - radius);
                }

                while (world.getBlockState(posOffset.add(0, -1, 0)).getBlock() == Blocks.AIR || world.getBlockState(posOffset.add(0, -1, 0)).getBlock() == Blocks.WATER) // Assert sea floor
                {
                    posOffset = posOffset.add(0, -1, 0);
                }

                world.setBlockState(posOffset.add(0, -1, 0), state);
            }
        }
        else // big cluster
        {
            radius = random.nextInt(8) + 20;
            amount = random.nextInt(100) + 400;

            for (int i = 0; i < amount; i++)
            {
                state = BityardBlocks.SEA_GLASS.getStateFromMeta(random.nextInt(16));
                BlockPos posOffset = pos.add(random.nextInt(radius * 2) - radius, random.nextInt(2), random.nextInt(radius * 2) - radius);
                while (posOffset.getDistance(pos.getX(), pos.getY(),pos.getZ()) > radius)
                {
                    posOffset = pos.add(random.nextInt(radius * 2) - radius, random.nextInt(2), random.nextInt(radius * 2) - radius);
                }

                while (world.getBlockState(posOffset.add(0, -1, 0)).getBlock() == Blocks.AIR || world.getBlockState(posOffset.add(0, -1, 0)).getBlock() == Blocks.WATER) // Assert sea floor
                {
                    posOffset = posOffset.add(0, -1, 0);
                }

                world.setBlockState(posOffset.add(0, -1, 0), state);
            }
        }

        return true;
    }
}
