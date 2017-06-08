package com.epolixa.bityard.event;

import com.epolixa.bityard.item.BityardItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.ThreadLocalRandom;

public class BatDropsHandler
{
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(LivingDropsEvent event)
    {
        if (event.getEntity() instanceof EntityBat)
        {
            event.getDrops().add(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ,
                                                new ItemStack(BityardItems.BAT_WING, ThreadLocalRandom.current().nextInt(0, 3))));
        }
    }
}
