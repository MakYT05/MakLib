package yt.mak.maklib.Entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class AstralEntity extends PathfinderMob {
    public AstralEntity(EntityType<? extends PathfinderMob> type, Level world) { super(type, world); }

    @Override
    protected void defineSynchedData() { super.defineSynchedData(); }
}