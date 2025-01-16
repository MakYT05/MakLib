package yt.mak.maklib.content;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import yt.mak.maklib.annotations.AutoRegister;

@AutoRegister(name = "example_entity", type = AutoRegister.Type.ENTITY)
public class ExampleEntity extends LivingEntity implements EntityType.EntityFactory<ExampleEntity> {
    public ExampleEntity(EntityType<? extends LivingEntity> type, Level world) { super(type, world); }

    @Override
    public Iterable<ItemStack> getArmorSlots() { return null; }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot p_21127_) { return null; }

    @Override
    public void setItemSlot(EquipmentSlot p_21036_, ItemStack p_21037_) {}

    @Override
    public HumanoidArm getMainArm() { return null; }

    @Override
    public ExampleEntity create(EntityType<ExampleEntity> type, Level world) { return new ExampleEntity(type, world); }
}