package com.bradurbiztondo.bradspigs.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;
// tnt throw hopefully
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.entity.TntEntity;
import net.minecraft.world.event.GameEvent;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class BaboyEntity extends PathAwareEntity implements Tameable, JumpingMount {

    // to tame baboy
    private static final float TAME_CHANCE = 0.20F; // 20% chance to tame

    @Nullable
    private UUID ownerUuid;

    private static final TrackedData<Byte> BABOY_FLAGS = DataTracker.registerData(BaboyEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final int TAMED_FLAG = 2;

    // see item held
    private static final TrackedData<ItemStack> BABOY_HELD_ITEM = DataTracker.registerData(BaboyEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);


    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(BABOY_FLAGS, (byte)0);
        builder.add(BABOY_HELD_ITEM, ItemStack.EMPTY);
    }

    public boolean isTame() {
        return (this.dataTracker.get(BABOY_FLAGS) & TAMED_FLAG) != 0;
    }

    public void setTame(boolean tame) {
        byte b = this.dataTracker.get(BABOY_FLAGS);
        if (tame) {
            this.dataTracker.set(BABOY_FLAGS, (byte)(b | TAMED_FLAG));
        } else {
            this.dataTracker.set(BABOY_FLAGS, (byte)(b & ~TAMED_FLAG));
        }
    }

    @Nullable
    @Override
    public UUID getOwnerUuid() {
        return this.ownerUuid;
    }

    public void setOwnerUuid(@Nullable UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {

        ItemStack heldStack = player.getStackInHand(hand);
        if (heldStack.isOf(Items.TNT)) {
            if (!this.getWorld().isClient) {
                ItemStack previous = this.getBaboyHeldItem();
                if (!previous.isEmpty()) {
                    if (!player.getInventory().insertStack(previous.copy())) {
                        player.dropItem(previous, false);
                    }
                }
                this.setBaboyHeldItem(heldStack.copy());
                player.setStackInHand(hand, ItemStack.EMPTY);
            }
            return ActionResult.SUCCESS;
        }

        if (this.hasPassengers() || this.isBaby()) {
            return super.interactMob(player, hand);
        }

        if (this.getWorld().isClient) {
            this.putPlayerOnBack(player);
            return ActionResult.CONSUME;
        }

        this.putPlayerOnBack(player);

        if (!this.isTame()) {
            if (this.random.nextFloat() < TAME_CHANCE) {
                this.bondWithPlayer(player);
            } else {
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
                player.stopRiding();
            }
        }

        return ActionResult.SUCCESS;
    }

    private void putPlayerOnBack(PlayerEntity player) {
        if (!this.getWorld().isClient) {
            player.setYaw(this.getYaw());
            player.setPitch(this.getPitch());
            player.startRiding(this);
        }
    }

    private void bondWithPlayer(PlayerEntity player) {
        this.setOwnerUuid(player.getUuid());
        this.setTame(true);
        // Not triggering TAME_ANIMAL because BaboyEntity isn't an AnimalEntity.
        this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Tame", this.isTame());
        if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }
        if (!this.getBaboyHeldItem().isEmpty()) {
            ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, this.getBaboyHeldItem())
                    .result()
                    .ifPresent(nbtElement -> nbt.put("HeldItem", nbtElement));
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setTame(nbt.getBoolean("Tame"));
        if (nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
        }
        if (nbt.contains("HeldItem")) {
            this.setBaboyHeldItem(
                    ItemStack.CODEC.parse(NbtOps.INSTANCE, nbt.get("HeldItem"))
                            .result()
                            .orElse(ItemStack.EMPTY)
            );
        } else {
            this.setBaboyHeldItem(ItemStack.EMPTY);
        }
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return !this.hasPassengers() && passenger instanceof PlayerEntity;
    }

    // tnt = mainhand item
    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.getBaboyHeldItem();
        }
        return super.getEquippedStack(slot);
    }

    public ItemStack getBaboyHeldItem() {
        return this.dataTracker.get(BABOY_HELD_ITEM);
    }

    public void setBaboyHeldItem(ItemStack stack) {
        this.dataTracker.set(BABOY_HELD_ITEM, stack);
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.isTame() && this.getFirstPassenger() instanceof PlayerEntity player ? player : null;
    }

    @Override
    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);
        Vec2f vec2f = this.getControlledRotation(controllingPlayer);
        this.setRotation(vec2f.y, vec2f.x);
        this.prevYaw = this.bodyYaw = this.headYaw = this.getYaw();

        // Jumping is handled via JumpingMount callbacks.
    }

    protected Vec2f getControlledRotation(LivingEntity controllingPassenger) {
        return new Vec2f(controllingPassenger.getPitch() * 0.5F, controllingPassenger.getYaw());
    }

    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        float f = controllingPlayer.sidewaysSpeed * 0.5F;
        float g = controllingPlayer.forwardSpeed;
        if (g <= 0.0F) {
            g *= 0.25F;
        }
        return new Vec3d(f, 0.0, g);
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
    }

    // how often and how high baboy jumps
    private static final float PASSIVE_JUMP_VELOCITY = 1.00F;
    private static final int PASSIVE_JUMP_COOLDOWN_MIN_TICKS = 80;
    private static final int PASSIVE_JUMP_COOLDOWN_MAX_TICKS = 200; // don't set lower than min ticks or crash
    private static final float PASSIVE_JUMP_CHANCE = 0.10F;

    private static final float RIDER_JUMP_MIN_VELOCITY = 0.30F;
    private static final float RIDER_JUMP_MAX_VELOCITY = 1.00F;

    private int passiveJumpCooldownTicks = 0;
    private boolean usingRiderJump = false;
    private float riderJumpPower = 0.0F;

    // bro has larger capacity until fall damage cooks him
    private static final float EXTRA_SAFE_FALL_DISTANCE = 5.0F;


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
        if (usingRiderJump) {
            float clamped = Math.max(0.0F, Math.min(1.0F, riderJumpPower));
            return RIDER_JUMP_MIN_VELOCITY + (RIDER_JUMP_MAX_VELOCITY - RIDER_JUMP_MIN_VELOCITY) * clamped;
        }
        return PASSIVE_JUMP_VELOCITY;
    }

    // fall damage related to fall distance. handle fall damage called when dude lands. by subtracting extra safe = increase capacity to fall before damage
    // max fall height before damage = 9 blocks, 10 blocks = damage

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        float adjustedDistance = Math.max(0.0F, fallDistance - EXTRA_SAFE_FALL_DISTANCE);
        int damage = this.computeFallDamage(adjustedDistance, damageMultiplier);
        if (damage <= 0) {
            return false;
        }

        this.damage(damageSource, damage);
        if (this.hasPassengers()) {
            for (Entity entity : this.getPassengersDeep()) {
                entity.damage(damageSource, damage);
            }
        }

        this.playBlockFallSound();
        return true;
    }

    @Override
    public void setJumpStrength(int strength) {
        if (strength > 0 && this.isOnGround()) {
            this.riderJumpPower = strength / 100.0F;
            this.usingRiderJump = true;
            this.jump();
            this.usingRiderJump = false;
        }
    }
    @Override
    public boolean canJump() {
        return this.isTame();
    }

    @Override
    public void startJumping(int height) {
    }

    @Override
    public void stopJumping() {
    }

    public BaboyEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);

    }

    public static DefaultAttributeContainer.Builder createBaboyAttributes() {
        return createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    // summon boom boom?!? bruh i hate naming convention like testCaseHere
    public void primeBaboyTnt(@Nullable LivingEntity igniter) {
        if (!this.getWorld().isClient) {
            double spawnX = this.getX();
            double spawnY = this.getY();
            double spawnZ = this.getZ();
            if (igniter != null) {
                var look = igniter.getRotationVec(1.0F);
                spawnX = igniter.getX() + (look.x * 12.0);
                spawnY = igniter.getEyeY() - 2.0;
                spawnZ = igniter.getZ() + (look.z * 12.0);
            }
            TntEntity tntEntity = new TntEntity(
                    this.getWorld(),
                    spawnX,
                    spawnY,
                    spawnZ,
                    igniter
            );
            this.getWorld().spawnEntity(tntEntity);
            this.getWorld().playSound(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(),
                    SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            this.getWorld().emitGameEvent(igniter, GameEvent.PRIME_FUSE, this.getBlockPos());
        }
    }




    @Override
    protected void initGoals() {
        this.goalSelector.add(0,new SwimGoal(this));
        this.goalSelector.add(1,new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(2,new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(3,new LookAroundGoal(this));



    }

}
