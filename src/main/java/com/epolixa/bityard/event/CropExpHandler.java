package com.epolixa.bityard.event;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class CropExpHandler
{
    Random random = new Random();

    @SubscribeEvent
    public void onCropHarvest(BlockEvent.HarvestDropsEvent event)
    {
        EntityPlayer player = event.getHarvester();
        IBlockState state = event.getState();
        BlockPos pos = event.getPos();

        if (player != null)
        {
            if (state.getBlock() instanceof BlockCrops && event.getDrops().size() > 1)
            {
                event.getWorld().spawnEntity(new EntityXPOrb(player.world, pos.getX(), pos.getY(), pos.getZ(), random.nextInt(3)));
            }
            else if (state.getBlock() instanceof BlockNetherWart && state.getValue(BlockNetherWart.AGE) >= 3)
            {
                event.getWorld().spawnEntity(new EntityXPOrb(player.world, pos.getX(), pos.getY(), pos.getZ(), random.nextInt(3)));
            }
        }
    }
}
