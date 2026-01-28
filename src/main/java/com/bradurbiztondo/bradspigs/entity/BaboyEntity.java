package com.bradurbiztondo.bradspigs.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.player.PlayerEntity;

public class BaboyEntity extends PathAwareEntity {

    // how often and how high baboy jumps
    private static final float PASSIVE_JUMP_VELOCITY = 1.00F;
    private static final int PASSIVE_JUMP_COOLDOWN_MIN_TICKS = 80;
    private static final int PASSIVE_JUMP_COOLDOWN_MAX_TICKS = 200;
    private static final float PASSIVE_JUMP_CHANCE = 0.10F;

    private int passiveJumpCooldownTicks = 0;


    @Override
    protected void mobTick() {
        super.mobTick();

        if(this.getWorld().isClient) {
            return;
        }

        if (passiveJumpCooldownTicks > 0) {
            passiveJumpCooldownTicks--;
            return;
        }

        if (this.isOnGround() && !this.isTouchingWater() && this.random.nextFloat() < PASSIVE_JUMP_CHANCE) {
            this.jump();
            passiveJumpCooldownTicks = PASSIVE_JUMP_COOLDOWN_MIN_TICKS
                    + this.random.nextInt(PASSIVE_JUMP_COOLDOWN_MAX_TICKS - PASSIVE_JUMP_COOLDOWN_MIN_TICKS + 1);
        }
    }

    @Override
    protected float getJumpVelocity() {
        return PASSIVE_JUMP_VELOCITY;
    }

    public BaboyEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);

    }

    public static DefaultAttributeContainer.Builder createBaboyAttributes() {
        return createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0,new SwimGoal(this));
        this.goalSelector.add(1,new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(2,new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(3,new LookAroundGoal(this));

    }

}
