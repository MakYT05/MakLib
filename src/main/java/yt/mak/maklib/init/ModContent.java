package yt.mak.maklib.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import yt.mak.maklib.Block.AstralBlock;
import yt.mak.maklib.Entity.AstralEntity;
import yt.mak.maklib.lib.Annotation.*;

public class ModContent {
    @NameIt("astralcoin")  // Название предмета
    public static final Item ASTRALCOIN = new Item(new Item.Properties());

    @NameBl("astral_block")  // Название блока
    public static final Block ASTRAL_BLOCK = new AstralBlock();

    @NameEn("astral_entity")  // Название сущности
    public static final EntityType<AstralEntity> ASTRAL_ENTITY = EntityType.Builder.of(AstralEntity::new, MobCategory.MISC)
            .sized(2F, 2F)
            .setTrackingRange(80)
            .setUpdateInterval(1)
            .build("astral_entity");;
}