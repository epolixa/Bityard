package com.epolixa.bityard.item;

import com.epolixa.bityard.Bityard;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemGuardianEye extends ItemBase implements ItemModelProvider
{
    private EntityLiving targetedEntity; // entity to shoot
    private boolean isChanneling; // maintaining aim on targeted entity
    private int chargeTime;    // current charge time
    private int maxChargeTime; // time in ticks until reached full power
    private double maxDamage; // damage to inflict at full power
    private double targetRange; // max distance around player to target entities x2
    private double targetAimAssist; // max acceptable aim vector difference

    public ItemGuardianEye()
    {
        super("guardian_eye");
        this.setMaxStackSize(1);
        this.setMaxDamage(384);
        this.setCreativeTab(CreativeTabs.COMBAT);

        targetedEntity = null;
        isChanneling = false;
        chargeTime = 0;
        maxChargeTime = 60;
        maxDamage = 8.0D;
        targetRange = 28.0D;
        targetAimAssist = 0.3D;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    public float getAttackAnimationScale(float p_175477_1_)
    {
        return ((float)this.chargeTime + p_175477_1_) / (float)this.maxChargeTime;
    }

    public EntityLiving findTarget(EntityPlayer player)
    {
        Bityard.log(this.getClass().getName(), "enter findTarget");

        EntityLiving ret = null;

        Vec3d vecPlayerLook = player.getLook(1).normalize(); // where player is looking
        Vec3d vecToEntityClosest = vecPlayerLook.addVector(targetAimAssist, targetAimAssist, targetAimAssist); // worst acceptable aim vector

        AxisAlignedBB bb = player.getEntityBoundingBox().addCoord(targetRange, targetRange, targetRange).offset(-targetRange/2, -targetRange/2, -targetRange/2);
        for (EntityLiving entity : player.world.getEntitiesWithinAABB(EntityLiving.class, bb))
        {
            Vec3d vecToEntity = new Vec3d(entity.posX - player.posX, entity.posY - player.posY, entity.posZ - player.posZ).normalize(); // aim towards this entity
            if (entity.getHealth() > 0 &&
                    vecPlayerLook.subtract(vecToEntity).lengthVector() < vecPlayerLook.subtract(vecToEntityClosest).lengthVector()) // if nearer than current nearest
            {
                vecToEntityClosest = vecToEntity; // set new nearest aim to beat
                ret = entity; // set entity to return
            }
        }

        if (ret != null)
        {
            ret = player.canEntityBeSeen(ret) ? ret : null;
        }

        Bityard.log(this.getClass().getName(), "exit findTarget");
        return ret;
    }

    public EntityLiving getTargetedEntity()
    {
        return targetedEntity;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        Bityard.log(this.getClass().getName(), "enter onItemRightClick");
        ItemStack itemStackIn = playerIn.getHeldItem(handIn);

        if (playerIn.getCooldownTracker().hasCooldown(this))
        {
            Bityard.log(this.getClass().getName(), "has cooldown");
            Bityard.log(this.getClass().getName(), "exit onItemRightClick FAIL");
            return new ActionResult(EnumActionResult.FAIL, itemStackIn);
        }
        else
        {
            Bityard.log(this.getClass().getName(), "does not have cooldown");
            ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, handIn, true);
            if (ret != null) return ret;
            else
            {
                targetedEntity = findTarget(playerIn);
                if (targetedEntity != null)
                {
                    playerIn.setActiveHand(handIn);
                    playerIn.world.playSound(playerIn,
                            playerIn.getPosition(),
                            SoundEvents.ENTITY_GUARDIAN_ATTACK,
                            SoundCategory.PLAYERS,
                            1, 1);
                    Bityard.log(this.getClass().getName(), "exit onItemRightClick SUCCESS, target is not null");
                    return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
                }
                else
                {
                    Bityard.log(this.getClass().getName(), "exit onItemRightClick PASS, target is null");
                    return new ActionResult(EnumActionResult.PASS, itemStackIn);
                }
            }
        }
    }

    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
        Bityard.log(this.getClass().getName(), "enter onUsingTick");

        if (player instanceof EntityPlayer)
        {
            if (chargeTime % 10 == 0) // every half a second (10 ticks) validate target
            {
                Bityard.log(this.getClass().getName(), "checking target");
                EntityLiving newTarget = findTarget((EntityPlayer) player);
                if (newTarget == null)
                {
                    targetedEntity = newTarget;
                    chargeTime = 0;
                    player.stopActiveHand();
                    Bityard.log(this.getClass().getName(), "target lost");
                }
                else if (targetedEntity == null)
                {
                    targetedEntity = newTarget;
                    Bityard.log(this.getClass().getName(), "target found");
                }
                else if (newTarget.getUniqueID() != targetedEntity.getUniqueID())
                {
                    targetedEntity = newTarget;
                    chargeTime = 0;
                    player.stopActiveHand();
                    Bityard.log(this.getClass().getName(), "target changed");
                }
            }
            if (targetedEntity != null) {
                Bityard.log(this.getClass().getName(), "charging");
                chargeTime++;
                // draw beam
            }
        }

        Bityard.log(this.getClass().getName(), "exit onUsingTick");
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        Bityard.log(this.getClass().getName(), "enter onPlayerStoppedUsing");

        if (entityLiving instanceof EntityPlayer && targetedEntity != null)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, (EntityPlayer)entityLiving, i, true);
            if (i < 0) return;

            float f = (i > maxChargeTime) ? (float)maxDamage : i / (float)maxDamage; // calculated damage for charge time
            if (f > 1.0D) {
                targetedEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) entityLiving).setMagicDamage(), f);
                entityplayer.getCooldownTracker().setCooldown(this, maxChargeTime);
                Bityard.log(this.getClass().getName(), "damaged target");
            }

            targetedEntity = null;
            chargeTime = 0;
        }

        Bityard.log(this.getClass().getName(), "exit onPlayerStoppedUsing");
    }
}
