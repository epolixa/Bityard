package com.epolixa.bityard.world;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class BityardWorldGenerator implements IWorldGenerator
{
    /* Determines at which XZ coordinates to spawn something at. worldGenerator handles placement along Y axis and finer placement rules. */

    WorldGenerator worldGenerator;
    int chunkX;
    int chunkZ;
    BlockPos pos;
    int chance;
    Random random;

    public BityardWorldGenerator() {}

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.random = random;
        resetPosAndChance();

        switch(world.provider.getDimension())
        {
            case -1: generateNether(world);
                break;
            case 0: generateOverworld(world);
                break;
            case 1: generateEnd(world);
                break;
        }
    }

    private void resetPosAndChance()
    {
        pos = new BlockPos(chunkX * 16 + random.nextInt(16), 256, chunkZ * 16 + random.nextInt(16));
        chance = random.nextInt(100);
    }

    private void generateNether(World world) {}

    private void generateOverworld(World world)
    {
        Biome biome = world.getBiomeForCoordsBody(pos);

        // Nether Portals
        /*if (chance < 1 && (
            biome != Biomes.RIVER &&
            biome != Biomes.FROZEN_RIVER &&
            biome != Biomes.OCEAN &&
            biome != Biomes.DEEP_OCEAN &&
            biome != Biomes.FROZEN_OCEAN
        ))
        {
            worldGenerator = new NetherPortalGenerator();
            worldGenerator.generate(world, random, pos);
            resetPosAndChance();
        }*/


        // Sponge
        if (chance < 10 && (
                biome == Biomes.OCEAN ||
                biome == Biomes.FROZEN_OCEAN
        ))
        {
            worldGenerator = new SpongeGenerator();
            worldGenerator.generate(world, random, pos);
            resetPosAndChance();
        }


        // Sea Glass
        if (chance < 30 && (
                biome == Biomes.OCEAN ||
                biome == Biomes.DEEP_OCEAN ||
                biome == Biomes.FROZEN_OCEAN
        ))
        {
            worldGenerator = new SeaGlassGenerator();
            worldGenerator.generate(world, random, pos);
            resetPosAndChance();
        }
    }

    private void generateEnd(World world) {}
}
