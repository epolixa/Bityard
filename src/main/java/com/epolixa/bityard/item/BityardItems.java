package com.epolixa.bityard.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BityardItems
{
    public static CreativeTabs TAB_COLLECTIBLES;

    public static ItemInstrument BASS_DRUM;
    public static ItemInstrument BASS_GUITAR;
    public static ItemBase BAT_WING;
    public static ItemInstrument CLICK_STICKS;
    public static ItemFoodBase COOKED_EGG;
    public static ItemCornDog CORN_DOG;
    public static ItemFoodBase CUCUMBER;
    public static ItemCucumberSeeds CUCUMBER_SEEDS;
    public static ItemFoodBase TOMATO;
    public static ItemTomatoSeeds TOMATO_SEEDS;
    //public static ItemGuardianEye GUARDIAN_EYE;
    public static ItemInstrument HARP;
    public static ItemFoodBase LEMORANGE;
    public static ItemFoodBase PINEAPPLE;
    public static ItemFoodBase PICKLE;
    public static ItemFoodBase CHEESE;
    public static ItemFoodBase CHEESE_PIZZA;
    public static ItemFoodBase VEGGIE_PIZZA;
    public static ItemGlowshroomPizza GLOWSHROOM_PIZZA;
    public static ItemFoodBase BURGER;
    public static ItemFoodBase BERRY_PIE;
    public static ItemFoodBase CHICKEN_POT_PIE;
    public static ItemIceCream ICE_CREAM;
    public static ItemIceCreamChocolate ICE_CREAM_CHOCOLATE;
    public static ItemInstrument SNARE_DRUM;
    public static ItemFoodBase ZOMBIE_JERKY;
    //public static ItemBug BUG;
    //public static ItemBugNet BUG_NET;
    public static ItemFish FISH;
    public static ItemFoodBase RAW_FISH;
    public static ItemFoodBase COOKED_FISH;
    public static ItemFoodBase SUSHI;
    public static ItemBityardFishingRod FISHING_ROD;
    public static ItemRing RING;
    public static ItemWitherBone WITHER_BONE;
    public static ItemWitherBonemeal WITHER_BONEMEAL;

    public static void init()
    {
        /* #### CREATIVE TABS #### */
        TAB_COLLECTIBLES = new CreativeTabs("collectibles") {
            @Override
            public ItemStack getTabIconItem() {
                return Items.RECORD_CAT.getDefaultInstance();
            }
        };

        /* #### CREATIVE TAB TWEAKS #### */
        Items.FISHING_ROD.setCreativeTab(null);
        Items.RECORD_13.setCreativeTab(TAB_COLLECTIBLES);
        Items.RECORD_CAT.setCreativeTab(TAB_COLLECTIBLES);
        Items.RECORD_BLOCKS.setCreativeTab(TAB_COLLECTIBLES);
        Items.RECORD_CHIRP.setCreativeTab(TAB_COLLECTIBLES);
        Items.RECORD_FAR.setCreativeTab(TAB_COLLECTIBLES);
        Items.RECORD_MALL.setCreativeTab(TAB_COLLECTIBLES);
        Items.RECORD_MELLOHI.setCreativeTab(TAB_COLLECTIBLES);
        Items.RECORD_STAL.setCreativeTab(TAB_COLLECTIBLES);
        Items.RECORD_STRAD.setCreativeTab(TAB_COLLECTIBLES);
        Items.RECORD_WARD.setCreativeTab(TAB_COLLECTIBLES);
        Items.RECORD_11.setCreativeTab(TAB_COLLECTIBLES);
        Items.RECORD_WAIT.setCreativeTab(TAB_COLLECTIBLES);

        /* #### ITEMS #### */
        BASS_DRUM = register(new ItemInstrument("bass_drum"));
        BASS_GUITAR = register(new ItemInstrument("bass_guitar"));
        BAT_WING = register(new ItemBase("bat_wing").setCreativeTab(CreativeTabs.MATERIALS));
        CLICK_STICKS = register(new ItemInstrument("click_sticks"));
        COOKED_EGG = register(new ItemFoodBase("cooked_egg", 2, 6.3f, false));
        CORN_DOG = register(new ItemCornDog());
        CUCUMBER = register(new ItemFoodBase("cucumber", 3, 3.6f, false));
        CUCUMBER_SEEDS = register(new ItemCucumberSeeds());
        TOMATO = register(new ItemFoodBase("tomato", 2, 2.4f, false));
        TOMATO_SEEDS = register(new ItemTomatoSeeds());
        //GUARDIAN_EYE = register(new ItemGuardianEye());
        HARP = register(new ItemInstrument("harp"));
        LEMORANGE = register(new ItemFoodBase("lemorange", 3, 3.4f, false));
        PINEAPPLE = register(new ItemFoodBase("pineapple", 6, 0.6F, false));
        PICKLE = register(new ItemFoodBase("pickle", 3, 6, false));
        CHEESE = register(new ItemFoodBase("cheese", 3, 3.53f, false));
        CHEESE_PIZZA = register(new ItemFoodBase("cheese_pizza", 4, 4.6f, false));
        VEGGIE_PIZZA = register(new ItemFoodBase("veggie_pizza", 5, 5.2f, false));
        GLOWSHROOM_PIZZA = register(new ItemGlowshroomPizza());
        ICE_CREAM = register(new ItemIceCream());
        ICE_CREAM_CHOCOLATE = register(new ItemIceCreamChocolate());
        BURGER = register(new ItemFoodBase("burger", 13, 20, false));
        BERRY_PIE = register(new ItemFoodBase("berry_pie", 8, 4.8f, false));
        CHICKEN_POT_PIE = register(new ItemFoodBase("chicken_pot_pie", 12, 17.5f, false));
        SNARE_DRUM = register(new ItemInstrument("snare_drum"));
        ZOMBIE_JERKY = register(new ItemFoodBase("zombie_jerky", 2, 12.8f, true));
        //BUG = register(new ItemBug());
        //BUG_NET = register(new ItemBugNet());
        FISH = register(new ItemFish());
        RAW_FISH = register(new ItemFoodBase("raw_fish", 1, 0.1f, false));
        COOKED_FISH = register(new ItemFoodBase("cooked_fish", 3, 0.8f, false));
        SUSHI = register(new ItemFoodBase("sushi", 1, 0.9f, false));
        FISHING_ROD = register(new ItemBityardFishingRod());
        RING = register(new ItemRing());
        WITHER_BONE = register(new ItemWitherBone());
        WITHER_BONEMEAL = register(new ItemWitherBonemeal());
    }

    private static <T extends Item> T register(T item)
    {
        GameRegistry.register(item);

        if (item instanceof ItemModelProvider)
        {
            ((ItemModelProvider)item).registerItemModel(item);
        }

        return item;
    }
}
