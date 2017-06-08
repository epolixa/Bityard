package com.epolixa.bityard.event;

import com.epolixa.bityard.item.BityardItems;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BirchLeavesHarvestDropsHandler
{
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(BlockEvent.HarvestDropsEvent event)
    {
        int chance = 200;
        if (event.getFortuneLevel() > 0)
        {
            chance -= 10 << event.getFortuneLevel();
            if (chance < 40) chance = 40;
        }

        if (event.getState().getBlock() == Blocks.LEAVES && event.getState().getValue(BlockOldLeaf.VARIANT) == BlockPlanks.EnumType.BIRCH && event.getWorld().rand.nextInt(20) == 0)
        {
            event.getDrops().add(new ItemStack(BityardItems.LEMORANGE));
        }

    }
}
