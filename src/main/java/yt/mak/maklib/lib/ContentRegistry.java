package yt.mak.maklib.lib;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.maklib.Entity.AstralEntity;

import java.lang.reflect.Field;

public class ContentRegistry {

    // Реестры для предметов, блоков, сущностей, рецептов
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "maklib");
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "maklib");
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "maklib");

    // Регистрируем предметы
    @Annotation.NameIt("astralcoin") // Аннотация для предмета
    public static final RegistryObject<Item> ASTRALCOIN = addItem("astralcoin", new Item(new Item.Properties().rarity(Rarity.EPIC)));

    // Регистрируем блоки
    @Annotation.NameBl("astral_block") // Аннотация для блока
    public static final RegistryObject<Block> ASTRAL_BLOCK = addBlock("astral_block", new Block(BlockBehaviour.Properties.of()));

    // Регистрируем сущности
    @Annotation.NameEn("astral_entity") // Аннотация для сущности
    public static final RegistryObject<EntityType<AstralEntity>> ASTRAL_ENTITY = addEntity("astral_entity", EntityType.Builder.of(AstralEntity::new, MobCategory.MONSTER).sized(0.6F, 1.8F));

    public static RegistryObject<Item> addItem(String name, Item item) { return ITEMS.register(name, () -> item); }

    public static RegistryObject<Block> addBlock(String name, Block block) { return BLOCKS.register(name, () -> block); }

    public static <T extends Entity> RegistryObject<EntityType<T>> addEntity(String name, EntityType.Builder<T> builder) {
        return ENTITIES.register(name, () -> builder.build(name));
    }

    public static void registerAnnotatedContent() {
        // Регистрация предметов
        for (Field field : ContentRegistry.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Annotation.NameIt.class)) {
                try {
                    field.setAccessible(true);

                    RegistryObject<Item> item = (RegistryObject<Item>) field.get(null);
                    String name = field.getAnnotation(Annotation.NameIt.class).value();

                    ITEMS.register(name, () -> item.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Регистрация блоков
        for (Field field : ContentRegistry.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Annotation.NameBl.class)) {
                try {
                    field.setAccessible(true);

                    RegistryObject<Block> block = (RegistryObject<Block>) field.get(null);
                    String name = field.getAnnotation(Annotation.NameBl.class).value();

                    BLOCKS.register(name, () -> block.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Регистрация сущностей
        for (Field field : ContentRegistry.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Annotation.NameEn.class)) {
                try {
                    field.setAccessible(true);

                    RegistryObject<EntityType<?>> entity = (RegistryObject<EntityType<?>>) field.get(null);
                    String name = field.getAnnotation(Annotation.NameEn.class).value();

                    ENTITIES.register(name, () -> entity.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}