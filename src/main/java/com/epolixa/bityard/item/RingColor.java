package com.epolixa.bityard.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.fixes.PotionItems;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class RingColor implements IItemColor
{
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemstack(ItemStack stack, int tintIndex)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound tag = stack.getTagCompound();

            if (tintIndex == 0) // Criteria base color
            {
                if (tag.hasKey("Block")) // Block
                {
                    Block block = Block.REGISTRY.getObject(new ResourceLocation(tag.getString("Block")));
                    if (block != null)
                    {
                        return block.getDefaultState().getMapColor().colorValue;
                    }
                    else
                    {
                        return -1;
                    }
                }
                else if (tag.hasKey("Biome")) // Biome
                {
                    Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(tag.getString("Biome")));
                    if (biome != null)
                    {
                        return biome.topBlock.getMapColor().colorValue;
                    }
                    else
                    {
                        return -1;
                    }
                }
                else
                {
                    return -1;
                }
            }
            else // Effect accent color
            {
                if (tag.getString("Effect") != null)
                {
                    Potion potion = Potion.getPotionFromResourceLocation(tag.getString("Effect"));
                    if (potion != null)
                    {
                        return potion.getLiquidColor();
                    }
                    else
                    {
                        return -1;
                    }
                }
                else
                {
                    return -1;
                }
            }
        }
        else
        {
            return -1;
        }
    }
}
