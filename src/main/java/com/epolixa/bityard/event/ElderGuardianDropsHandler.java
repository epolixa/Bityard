package com.epolixa.bityard.event;

import com.epolixa.bityard.item.BityardItems;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.ThreadLocalRandom;

public class ElderGuardianDropsHandler
{
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled = true)
    public void onEvent(LivingDropsEvent event)
    {
        if (event.getEntity() instanceof EntityElderGuardian)
        {
            for (int i = 0; i < event.getDrops().size(); i++)
            {
                Item drop = event.getDrops().get(i).getEntityItem().getItem();
                if (drop == Blocks.SPONGE.getDefaultState().withProperty(PropertyBool.create("wet"), true))
                {
                    event.getDrops().remove(i--);
                }
            }
            /*event.getDrops().add(
                    new EntityItem(
                        event.getEntity().world,
                        event.getEntity().posX,
                        event.getEntity().posY,
                        event.getEntity().posZ,
                        new ItemStack(BityardItems.GUARDIAN_EYE, 1)
                    )
            );*/
        }
    }
}
