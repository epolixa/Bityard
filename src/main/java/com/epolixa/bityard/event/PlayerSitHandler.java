package com.epolixa.bityard.event;

import com.epolixa.bityard.Bityard;
import com.epolixa.bityard.gameplay.BityardKeyBinds;
import com.epolixa.bityard.network.MessageSit;
import com.epolixa.bityard.network.NetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class PlayerSitHandler
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerPressSit(InputEvent.KeyInputEvent event)
    {
        if (BityardKeyBinds.SIT.isPressed())
        {
            Bityard.log("PlayerSitHandler", "player pressed sit key");
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();

            if (FMLClientHandler.instance().getClient().inGameHasFocus && player != null) {
                Bityard.log("PlayerSitHandler", "sending sit key message to server");
                NetworkHandler.INSTANCE.sendToServer(new MessageSit());
            }
        }
    }

    public static boolean canSitOn(Block block)
    {
        Bityard.log("PlayerSitHandler", "checking if block can be sat on");
        return block != Blocks.AIR &&
               block != Blocks.WATER && block != Blocks.FLOWING_WATER &&
               block != Blocks.LAVA && block != Blocks.FLOWING_LAVA;
    }

    public static void playerSit(EntityPlayer player)
    {
        Bityard.log("PlayerSitHandler", "attempting sit action");
        if (player != null)
        {
            Bityard.log("PlayerSitHandler", "player is not null");
            World world = player.world;
            if (player.isRiding())
            {
                Bityard.log("PlayerSitHandler", "player is already riding something");
                if (player.getRidingEntity() instanceof Seat)
                {
                    Bityard.log("PlayerSitHandler", "player is already sitting so dismount seat");
                    player.dismountRidingEntity();
                }
            }
            else if (canSitOn(world.getBlockState(player.getPosition().add(0,-1,0)).getBlock()))
            {
                Bityard.log("PlayerSitHandler", "sit");
                Seat seat = new Seat(player);
                synchronized (world) {
                    Bityard.log("PlayerSitHandler", "spawning seat entity");
                    world.spawnEntity(seat);
                }
            }
        }
    }

    public static class Seat extends Entity {

        public Seat(EntityPlayer player)
        {
            this(player.world);
            Bityard.log("Seat", "create seat, set position");

            this.setPosition(player.posX, player.posY, player.posZ);

            Bityard.log("Seat", "set player riding");
            player.startRiding(this);
        }

        public Seat(World par1World)
        {
            super(par1World);
            Bityard.log("Seat", "create seat, set size");

            this.setSize(0F, 0F);
        }

        @Override
        public void onUpdate()
        {
            Bityard.log("Seat", "entity update");
            super.onUpdate();

            Bityard.log("Seat", "check passengers");
            List<Entity> passengers = getPassengers();
            if(passengers.isEmpty())
            {
                Bityard.log("Seat", "no passengers, die");
                setDead();
            }
        }

        @Override
        public void updatePassenger(Entity passenger)
        {
            Bityard.log("Seat", "update passenger");
            if (this.isPassenger(passenger))
            {
                Bityard.log("Seat", "has passenger");
                EntityPlayer player = (EntityPlayer) passenger;
                Bityard.log("Seat", "set passenger position");
                player.setPosition(this.posX, this.posY - 0.6, this.posZ);
                if (player.isSprinting())
                {
                    Bityard.log("Seat", "player is sprinting, stop that");
                    player.setSprinting(false);
                }
            }
        }

        @Override protected void entityInit() { }
        @Override public void readEntityFromNBT(NBTTagCompound nbttagcompound) { }
        @Override public void writeEntityToNBT(NBTTagCompound nbttagcompound) { }
    }
}
