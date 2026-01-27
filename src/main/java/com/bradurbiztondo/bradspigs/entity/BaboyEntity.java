package com.bradurbiztondo.bradspigs.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class BaboyEntity extends PathAwareEntity {
    public BaboyEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
        this.setAiDisabled(true);
    }

    public static DefaultAttributeContainer.Builder createBaboyAttributes() {
        return createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0);
    }

    @Override
    public void tickMovement() {

    }


}