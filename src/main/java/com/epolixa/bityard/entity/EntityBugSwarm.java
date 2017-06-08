package com.epolixa.bityard.entity;

import com.epolixa.bityard.item.BityardItems;
import com.epolixa.bityard.item.ItemBug;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class EntityBugSwarm extends EntityAmbientCreature
{
    Random random = new Random();
    ItemStack bug;

    public EntityBugSwarm(World world, BlockPos pos)
    {
        super(world);
        //bug = getBugByBiome(world, pos);
    }

    /*private ItemStack getBugByBiome(World world, BlockPos pos)
    {
        ItemStack stack = new ItemStack(BityardItems.BUG);

        Biome biome = world.getBiome(pos);

        return stack;
    }*/

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        world.spawnParticle(EnumParticleTypes.FALLING_DUST, posX, posY + 2, posZ, 0, 0, 0);
    }

    @Override protected void entityInit() { }
    @Override public void readEntityFromNBT(NBTTagCompound nbttagcompound) { }
    @Override public void writeEntityToNBT(NBTTagCompound nbttagcompound) { }
}
