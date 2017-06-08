package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class ItemRing extends ItemBase implements ItemModelProvider
{
    List<Block> illegalBlocks;
    List<Biome> illegalBiomes;
    List<Potion> illegalEffects;

    public ItemRing()
    {
        super("ring");
        setCreativeTab(BityardItems.TAB_COLLECTIBLES);
        setMaxStackSize(1);

        illegalBlocks = Arrays.asList(
                Blocks.AIR,
                Blocks.COMMAND_BLOCK,
                Blocks.CHAIN_COMMAND_BLOCK,
                Blocks.REPEATING_COMMAND_BLOCK,
                Blocks.STRUCTURE_BLOCK,
                Blocks.STRUCTURE_VOID,
                Blocks.BARRIER
        );
        illegalBiomes = Arrays.asList(
                Biomes.VOID
        );
        illegalEffects = Arrays.asList(
                Potion.REGISTRY.getObject(new ResourceLocation("minecraft:instant_health")),
                Potion.REGISTRY.getObject(new ResourceLocation("minecraft:instant_damage")),
                Potion.REGISTRY.getObject(new ResourceLocation("minecraft:health_boost"))
        );
    }

    @Override
    public void registerItemModel(Item item)
    {
        Bityard.proxy.registerItemRenderer(this, 0, "ring");
    }

    /*@Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        String name = net.minecraft.util.text.translation.I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();

        if (stack.hasTagCompound())
        {
            name = name + " of ";
            NBTTagCompound tag = stack.getTagCompound();

            // Criteria
            if (tag.hasKey("Block")) // Block
            {
                Block block = Block.REGISTRY.getObject(new ResourceLocation(tag.getString("Block")));
                if (block != null)
                {
                    name = name + block.getLocalizedName() + "y ";
                }
            }
            else if (tag.hasKey("Biome"))
            {
                Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(tag.getString("Biome")));
                if (biome != null)
                {
                    name = name + biome.getBiomeName().split(" ")[0] + " ";
                }
            }

            // Effect
            if (tag.hasKey("Effect"))
            {
                Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(tag.getString("Effect")));
                if (potion != null)
                {
                    name = name + I18n.format(potion.getName());
                }
            }
        }

        return name;
    }*/

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound tag = stack.getTagCompound();

            // Criteria
            if (tag.hasKey("Block")) // Block
            {
                Block block = Block.REGISTRY.getObject(new ResourceLocation(tag.getString("Block")));
                if (block != null)
                {
                    tooltip.add(TextFormatting.DARK_PURPLE + "While touching " + block.getLocalizedName() + ":");
                }
                else
                {
                    tooltip.add(TextFormatting.DARK_PURPLE + "Passive:");
                }
            }
            else if (tag.hasKey("Biome")) // Biome
            {
                Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(tag.getString("Biome")));
                if (biome != null)
                {
                    tooltip.add(TextFormatting.DARK_PURPLE + "While in " + biome.getBiomeName() + ":");
                }
                else
                {
                    tooltip.add(TextFormatting.DARK_PURPLE + "Passive:");
                }
            }
            else
            {
                tooltip.add(TextFormatting.DARK_PURPLE + "Passive:");
            }

            // Effect
            if (tag.hasKey("Effect"))
            {
                Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(tag.getString("Effect")));
                if (potion != null)
                {
                    tooltip.add(" " + TextFormatting.BLUE + I18n.format(potion.getName()));
                }
                else
                {
                    tooltip.add(" " + TextFormatting.BLUE + "Effect");
                }
            }
            else
            {
                tooltip.add(" " + TextFormatting.BLUE + "Effect");
            }
        }
    }

    public ItemStack randomizeEffects(ItemStack stack)
    {
        NBTTagCompound tag = new NBTTagCompound();

        // Criteria
        switch(itemRand.nextInt(2))
        {
            case 0: // Block
                List<Block> allBlocks = GameData.getBlockRegistry().getValues();
                Block block = allBlocks.get(itemRand.nextInt(allBlocks.size()));
                while (illegalBlocks.contains(block) || !block.getDefaultState().isOpaqueCube())
                {
                    block = allBlocks.get(itemRand.nextInt(allBlocks.size()));
                }
                tag.setString("Block", block.getRegistryName().toString());
                break;
            case 1: // Biome
                List<Biome> allBiomes = GameData.getBiomeRegistry().getValues();
                Biome biome = allBiomes.get(itemRand.nextInt(allBiomes.size()));
                while (illegalBiomes.contains(biome))
                {
                    biome = allBiomes.get(itemRand.nextInt(allBiomes.size()));
                }
                tag.setString("Biome", biome.getRegistryName().toString());
                break;
        }

        // Effect
        List<Potion> allEffects = GameData.getPotionRegistry().getValues();
        Potion potion = allEffects.get(itemRand.nextInt(allEffects.size()));
        while (illegalEffects.contains(potion))
        {
            potion = allEffects.get(itemRand.nextInt(allEffects.size()));
        }
        tag.setString("Effect", potion.getRegistryName().toString());

        stack.setTagCompound(tag);
        return stack;
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        // use to apply effects
        if (!stack.hasTagCompound())
        {
            stack = randomizeEffects(stack);
        }
        else if (entityIn instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean applyEffect = false;

            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("Block")) // Block
            {
                Block block = Block.REGISTRY.getObject(new ResourceLocation(tag.getString("Block")));
                if (block != null)
                {
                    for (int i = -1; i < 1; i++)
                    {
                        for (int j = -1; j < 2; j++)
                        {
                            for (int k = -1; k < 1; k++)
                            {
                                if (block == worldIn.getBlockState(player.getPosition().add(i,j,k)).getBlock())
                                {
                                    applyEffect = true;
                                    break;
                                }
                            }
                            if (applyEffect) break;
                        }
                        if (applyEffect) break;
                    }
                }
            }
            else if (tag.hasKey("Biome"))
            {
                Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(tag.getString("Biome")));
                if (biome != null)
                {
                    if (biome == worldIn.getBiome(player.getPosition()))
                    {
                        applyEffect = true;
                    }
                }
            }

            if (applyEffect && tag.getString("Effect") != null)
            {
                Potion potion = Potion.REGISTRY.getObject(new ResourceLocation(tag.getString("Effect")));
                if (potion != null)
                {
                    if (player.getActivePotionEffect(potion) == null)
                    {
                        player.addPotionEffect(new PotionEffect(potion, 219, 0));
                    }
                    else if (player.getActivePotionEffect(potion).getDuration() <= 21)
                    {
                        player.addPotionEffect(new PotionEffect(potion, 219, 0));
                    }
                }
            }
        }
    }
}
