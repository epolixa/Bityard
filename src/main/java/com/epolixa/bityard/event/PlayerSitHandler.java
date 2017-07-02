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
import net.minecraft.world.WorldServer;
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
            Bityard.log("player pressed sit key");
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();

            if (FMLClientHandler.instance().getClient().inGameHasFocus && player != null) {
                Bityard.log("sending sit key message to server");
                NetworkHandler.INSTANCE.sendToServer(new MessageSit());
            }
        }
    }

    public static boolean canSitOn(World world, BlockPos pos)
    {
        Bityard.log("checking if block can be sat on");
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        return state.getCollisionBoundingBox(world, pos) != null &&
               block != Blocks.WATER && block != Blocks.FLOWING_WATER &&
               block != Blocks.LAVA && block != Blocks.FLOWING_LAVA;
    }

    public static void playerSit(EntityPlayer player)
    {
        Bityard.log("attempting sit action");
        if (player != null)
        {
            Bityard.log("player is not null");
            World world = player.world;
            if (player.isRiding())
            {
                Bityard.log("player is already riding something");
                if (player.getRidingEntity() instanceof Seat)
                {
                    Bityard.log("player is already sitting so dismount seat");
                    player.dismountRidingEntity();
                }
            }
            else if (canSitOn(world, player.getPosition().add(0,-1,0)))
            {
                Bityard.log("player is trying to sit");
                if (!world.isRemote)
                {
                    WorldServer worldServer = (WorldServer)world;
                    Bityard.log("spawning seat entity");
                    Seat seat = new Seat(worldServer, player.getPosition());
                    worldServer.spawnEntity(seat);
                    Bityard.log("setting player to start riding seat");
                    player.startRiding(seat);
                }
            }
        }
    }

    public static class Seat extends Entity {

        public Seat(World world, BlockPos pos)
        {
            this(world);

            Bityard.log("creating seat, set position");
            this.setPosition(pos.getX(), pos.getY(), pos.getZ());
        }

        public Seat(World world)
        {
            super(world);

            Bityard.log("creating seat, set size");
            this.setSize(0F, 0F);
        }

        @Override
        public void onUpdate()
        {
            Bityard.log("entity update");
            super.onUpdate();

            Bityard.log("check passengers");
            List<Entity> passengers = getPassengers();
            if(passengers.isEmpty())
            {
                Bityard.log("no passengers, die");
                setDead();
                return;
            }
            for (Entity passenger : passengers)
            {
                if (passenger.isSneaking())
                {
                    Bityard.log("passenger dismounting, die");
                    setDead();
                    return;
                }
            }
        }

        @Override
        public void updatePassenger(Entity passenger)
        {
            Bityard.log("update passenger");
            if (this.isPassenger(passenger))
            {
                Bityard.log("has passenger");
                EntityPlayer player = (EntityPlayer) passenger;
                Bityard.log("set passenger position");
                player.setPosition(this.posX, this.posY - 0.6, this.posZ);
                if (player.isSprinting())
                {
                    Bityard.log("player is sprinting, stop that");
                    player.setSprinting(false);
                }
            }
        }

        @Override protected void entityInit() { }
        @Override public void readEntityFromNBT(NBTTagCompound nbttagcompound) { }
        @Override public void writeEntityToNBT(NBTTagCompound nbttagcompound) { }
    }
}
