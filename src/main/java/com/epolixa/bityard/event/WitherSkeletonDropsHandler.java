package com.epolixa.bityard.event;

import com.epolixa.bityard.item.BityardItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WitherSkeletonDropsHandler
{
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(LivingDropsEvent event)
    {
        if (event.getEntity() instanceof EntityWitherSkeleton)
        {
            int numBones = 0;
            for (int i = 0; i < event.getDrops().size(); i++)
            {
                EntityItem entityItem = event.getDrops().get(i);
                if (entityItem.getEntityItem().getItem() == Items.BONE)
                {
                    event.getDrops().remove(i--);
                    numBones++;
                }
            }
            event.getDrops().add(
                    new EntityItem(
                            event.getEntity().world,
                            event.getEntity().posX,
                            event.getEntity().posY,
                            event.getEntity().posZ,
                            new ItemStack(BityardItems.WITHER_BONE, numBones)
                    )
            );
        }
    }
}
