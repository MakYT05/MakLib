package yt.mak.maklib.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import yt.mak.maklib.annotations.AutoRegister;

@AutoRegister(type = AutoRegister.Type.ENTITY)
public class MyCustomEntity extends Entity {
    public static final EntityType.Builder<MyCustomEntity> TYPE = EntityType.Builder.of(MyCustomEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.8f);

    public MyCustomEntity(EntityType<? extends MyCustomEntity> type, Level world) { super(type, world); }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_333664_) {}

    @Override
    public boolean hurtServer(ServerLevel p_365506_, DamageSource p_366233_, float p_368913_) { return false; }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {}
}