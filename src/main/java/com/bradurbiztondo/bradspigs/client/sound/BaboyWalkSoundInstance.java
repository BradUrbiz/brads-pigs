package com.bradurbiztondo.bradspigs.client.sound;

import com.bradurbiztondo.bradspigs.entity.BaboyEntity;
import com.bradurbiztondo.bradspigs.registry.ModSounds;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class BaboyWalkSoundInstance extends EntityTrackingSoundInstance {
    private static final double MIN_MOVE_SPEED_SQ = 1.0E-4;
    private final BaboyEntity baboy;

    public BaboyWalkSoundInstance(BaboyEntity baboy) {
        super(ModSounds.BABOY_WALK, SoundCategory.NEUTRAL, 0.4F, 1.0F, baboy, Random.create().nextLong());
        this.baboy = baboy;
        this.repeat = true;
        this.repeatDelay = 0;
    }

    public static boolean isBaboyMoving(BaboyEntity baboy) {
        Vec3d velocity = baboy.getVelocity();
        return velocity.horizontalLengthSquared() > MIN_MOVE_SPEED_SQ;
    }

    public void stop() {
        this.setDone();
    }

    @Override
    public void tick() {
        super.tick();
        if (baboy.isRemoved() || baboy.isSilent() || !isBaboyMoving(baboy)) {
            this.setDone();
        }
    }
}
