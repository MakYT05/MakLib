package yt.mak.maklib.utils;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yt.mak.maklib.annotations.AutoRegister;
import yt.mak.maklib.blocks.MyCustomBlock;
import yt.mak.maklib.entity.MyCustomEntity;
import yt.mak.maklib.items.MyCustomItem;

public class AutoRegisterManager {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "maklib");
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "maklib");
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "maklib");

    public static void initialize(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        ENTITIES.register(bus);

        scanAndRegister();
    }

    private static void scanAndRegister() {
        registerAnnotatedClass(MyCustomBlock.class);
        registerAnnotatedClass(MyCustomItem.class);
        registerAnnotatedClass(MyCustomEntity.class);
    }

    private static void registerAnnotatedClass(Class<?> clazz) {
        AutoRegister annotation = clazz.getAnnotation(AutoRegister.class);

        if (annotation == null) return;

        try {
            switch (annotation.type()) {
                case BLOCK -> {
                    Block block = (Block) clazz.getDeclaredConstructor().newInstance();
                    registerBlock(block, clazz.getSimpleName().toLowerCase());
                }
                case ITEM -> {
                    Item item = (Item) clazz.getDeclaredConstructor().newInstance();
                    registerItem(item, clazz.getSimpleName().toLowerCase());
                }
                case ENTITY -> {
                    @SuppressWarnings("unchecked")
                    EntityType.Builder<?> builder = (EntityType.Builder<?>) clazz.getDeclaredField("TYPE").get(null);
                    registerEntity(builder, clazz.getSimpleName().toLowerCase());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerBlock(Block block, String name) {
        RegistryObject<Block> registeredBlock = BLOCKS.register(name, () -> block);
        ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
    }

    private static void registerItem(Item item, String name) {
        ITEMS.register(name, () -> item);
    }

    private static <T extends net.minecraft.world.entity.Entity> void registerEntity(EntityType.Builder<T> builder, String registryName) {
        ENTITIES.register(registryName, () -> builder.build(null));
    }
}