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