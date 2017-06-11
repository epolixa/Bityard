package com.epolixa.bityard.event;

import com.epolixa.bityard.entity.EntityBityardFishHook;
import com.google.common.base.Function;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.internal.FMLMessage;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FishHookSpawnHandler
{
    @SideOnly(Side.CLIENT)
    public static void init() {
        EntityRegistry.EntityRegistration customFishHookRegistration = EntityRegistry.instance().lookupModSpawn(EntityBityardFishHook.class, false);
        Function<FMLMessage.EntitySpawnMessage, Entity> fishHookSpawnHandler = new Function<FMLMessage.EntitySpawnMessage, Entity>() {

            @Override
            public Entity apply(FMLMessage.EntitySpawnMessage input) {
                int anglerId = 0;
                double posX = 0;
                double posY = 0;
                double posZ = 0;
                try {
                    anglerId = ReflectionHelper.findField(FMLMessage.EntitySpawnMessage.class, "throwerId").getInt(input);
                    posX = ReflectionHelper.findField(FMLMessage.EntitySpawnMessage.class, "rawX").getDouble(input);
                    posY = ReflectionHelper.findField(FMLMessage.EntitySpawnMessage.class, "rawY").getDouble(input);
                    posZ = ReflectionHelper.findField(FMLMessage.EntitySpawnMessage.class, "rawZ").getDouble(input);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                WorldClient world = FMLClientHandler.instance().getWorldClient();

                Entity angler = world.getEntityByID(anglerId);
                if (!(angler instanceof EntityPlayer)) {
                    return null;
                }

                Entity entity = new EntityBityardFishHook(world, (EntityPlayer) angler, posX, posY, posZ);

                return entity;
            }
        };

        customFishHookRegistration.setCustomSpawning(fishHookSpawnHandler, false);
    }
}
