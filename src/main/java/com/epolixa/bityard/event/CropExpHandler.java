package com.epolixa.bityard.event;

import com.epolixa.bityard.Bityard;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
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

        if (player != null && state.getBlock() instanceof BlockCrops && event.getDrops().size() > 1)
        {
            event.getWorld().spawnEntity(new EntityXPOrb(player.world, pos.getX(), pos.getY(), pos.getZ(), random.nextInt(3)));
        }
    }
}
