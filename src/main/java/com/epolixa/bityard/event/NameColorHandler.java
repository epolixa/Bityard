package com.epolixa.bityard.event;

import com.epolixa.bityard.Bityard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;


public class NameColorHandler
{
    Random random = new Random();

    @SubscribeEvent
    public void onUseItemStart(PlayerInteractEvent event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
            Bityard.log("NameColorHandler", "player right clicked");

            EntityPlayer player = (EntityPlayer)event.getEntity();
            Vec3d look = player.getLook(1).normalize();
            Bityard.log("NameColorHandler", "view angle = x:" + look.xCoord + ", y:" + look.yCoord + ", z:" + look.zCoord);
            if (look.yCoord >= 0.95)
            {
                Bityard.log("NameColorHandler", "player is looking up");
                for (ItemStack stack : OreDictionary.getOres("dye"))
                {
                    if (stack.getItem() == event.getItemStack().getItem())
                    {
                        Bityard.log("NameColorHandler", "player using dye");
                        World world = event.getWorld();
                        stack = event.getItemStack();
                        String teamName = EnumDyeColor.byDyeDamage(stack.getMetadata()).getName();

                        Scoreboard scoreboard = world.getScoreboard();

                        if (scoreboard.getTeam(teamName) == null)
                        {
                            Bityard.log("NameColorHandler", "team " + teamName + " does not exist, creating team");
                            ScorePlayerTeam team = scoreboard.createTeam(teamName);
                            TextFormatting tf = TextFormatting.RESET;
                            switch (teamName)
                            {
                                case "white":
                                    team.setTeamName("White");
                                    tf = TextFormatting.WHITE;
                                    break;
                                case "orange":
                                    team.setTeamName("Orange");
                                    tf = TextFormatting.GOLD;
                                    break;
                                case "magenta":
                                    team.setTeamName("Magenta");
                                    tf = TextFormatting.AQUA;
                                    break;
                                case "light_blue":
                                    team.setTeamName("Light Blue");
                                    tf = TextFormatting.BLUE;
                                    break;
                                case "yellow":
                                    team.setTeamName("Yellow");
                                    tf = TextFormatting.YELLOW;
                                    break;
                                case "lime":
                                    team.setTeamName("Lime");
                                    tf = TextFormatting.GREEN;
                                    break;
                                case "pink":
                                    team.setTeamName("Pink");
                                    tf = TextFormatting.LIGHT_PURPLE;
                                    break;
                                case "gray":
                                    team.setTeamName("Gray");
                                    tf = TextFormatting.DARK_GRAY;
                                    break;
                                case "silver":
                                    team.setTeamName("Silver");
                                    tf = TextFormatting.GRAY;
                                    break;
                                case "cyan":
                                    team.setTeamName("Cyan");
                                    tf = TextFormatting.DARK_AQUA;
                                    break;
                                case "purple":
                                    team.setTeamName("Purple");
                                    tf = TextFormatting.DARK_PURPLE;
                                    break;
                                case "blue":
                                    team.setTeamName("Blue");
                                    tf = TextFormatting.DARK_BLUE;
                                    break;
                                case "brown":
                                    team.setTeamName("Brown");
                                    tf = TextFormatting.DARK_RED;
                                    break;
                                case "green":
                                    team.setTeamName("Green");
                                    tf = TextFormatting.DARK_GREEN;
                                    break;
                                case "red":
                                    team.setTeamName("Red");
                                    tf = TextFormatting.RED;
                                    break;
                                case "black":
                                    team.setTeamName("Black");
                                    tf = TextFormatting.BLACK;
                                    break;
                                default:
                                    break;
                            }
                            team.setChatFormat(tf);
                            team.setNamePrefix(tf.toString());
                            team.setNameSuffix(TextFormatting.RESET.toString());
                        }

                        if (player.getTeam() == null)
                        {
                            Bityard.log("NameColorHandler", "adding " + player.getName() + " to team " + teamName);
                            scoreboard.addPlayerToTeam(player.getName(), teamName);
                            player.swingArm(event.getHand());
                            player.getHeldItem(event.getHand()).setCount(player.getHeldItem(event.getHand()).getCount() - 1);
                            world.playSound(
                                    player,
                                    player.getPosition(),
                                    SoundEvents.BLOCK_SLIME_STEP,
                                    SoundCategory.PLAYERS,
                                    1,
                                    1 - (float)(random.nextInt(3)*0.1)
                            );
                            if (!world.isRemote)
                            {
                                WorldServer worldServer = (WorldServer) world;
                                for (int i = 0; i < 8; i++)
                                {
                                    worldServer.spawnParticle(
                                            EnumParticleTypes.ITEM_CRACK,
                                            player.posX + ((random.nextInt(8) - 4)*0.1),
                                            player.posY + 2.2 + ((random.nextInt(4) - 2)*0.1),
                                            player.posZ + ((random.nextInt(8) - 4)*0.1),
                                            1,
                                            0,
                                            0,
                                            0,
                                            0,
                                            new int[] {351, stack.getMetadata()}
                                    );
                                }
                            }
                        }
                        if (!teamName.equals(player.getTeam().getRegisteredName()))
                        {
                            Bityard.log("NameColorHandler", "removing " + player.getName() + " from team " + teamName);
                            scoreboard.removePlayerFromTeam(player.getName(), (ScorePlayerTeam)player.getTeam());
                            Bityard.log("NameColorHandler", "adding " + player.getName() + " to team " + teamName);
                            scoreboard.addPlayerToTeam(player.getName(), teamName);
                            player.swingArm(event.getHand());
                            player.getHeldItem(event.getHand()).setCount(player.getHeldItem(event.getHand()).getCount() - 1);
                            world.playSound(
                                    player,
                                    player.getPosition(),
                                    SoundEvents.BLOCK_SLIME_STEP,
                                    SoundCategory.PLAYERS,
                                    1,
                                    1 - (float)(random.nextInt(3)*0.1)
                            );
                            if (!world.isRemote)
                            {
                                WorldServer worldServer = (WorldServer) world;
                                for (int i = 0; i < 8; i++)
                                {
                                    worldServer.spawnParticle(
                                            EnumParticleTypes.ITEM_CRACK,
                                            player.posX + ((random.nextInt(8) - 4)*0.1),
                                            player.posY + 2.2 + ((random.nextInt(4) - 2)*0.1),
                                            player.posZ + ((random.nextInt(8) - 4)*0.1),
                                            1,
                                            0,
                                            0,
                                            0,
                                            0,
                                            new int[] {351, stack.getMetadata()}
                                    );
                                }
                            }
                        }

                        return;
                    }
                }
            }
        }
    }
}
