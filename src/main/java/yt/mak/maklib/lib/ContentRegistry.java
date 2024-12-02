package yt.mak.maklib.lib;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import yt.mak.maklib.MakLib;
import yt.mak.maklib.init.ModContent;

public class ContentRegistry {

    // Регистры для каждого типа контента
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MakLib.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MakLib.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, MakLib.MODID);

    // Мапы для хранения объектов, которые нужно зарегистрировать
    private static final Map<String, Item> items = new HashMap<>();
    private static final Map<String, Block> blocks = new HashMap<>();
    private static final Map<String, EntityType<?>> entities = new HashMap<>();

    // Статический блок для автоматической регистрации
    static {
        registerAnnotatedContent();
    }

    // Метод для регистрации аннотированных полей
    public static void registerAnnotatedContent() {
        try {
            // Получаем все поля класса ModContent
            Field[] fields = ModContent.class.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Annotation.NameIt.class)) {
                    // Регистрация предмета
                    String itemName = field.getAnnotation(Annotation.NameIt.class).value();
                    // Добавляем предмет в список для регистрации
                    items.put(itemName, (Item) field.get(null));
                } else if (field.isAnnotationPresent(Annotation.NameBl.class)) {
                    // Регистрация блока
                    String blockName = field.getAnnotation(Annotation.NameBl.class).value();
                    // Добавляем блок в список для регистрации
                    blocks.put(blockName, (Block) field.get(null));
                } else if (field.isAnnotationPresent(Annotation.NameEn.class)) {
                    // Регистрация сущности
                    String entityName = field.getAnnotation(Annotation.NameEn.class).value();
                    // Добавляем сущность в список для регистрации
                    entities.put(entityName, (EntityType<?>) field.get(null));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void register(IEventBus eventBus) {
        // Регистрация предметов
        for (Map.Entry<String, Item> entry : items.entrySet()) {
            String name = entry.getKey();
            Item item = entry.getValue();
            ITEMS.register(name, () -> item);
        }

        // Регистрация блоков
        for (Map.Entry<String, Block> entry : blocks.entrySet()) {
            String name = entry.getKey();
            Block block = entry.getValue();
            BLOCKS.register(name, () -> block);
        }

        // Регистрация сущностей
        for (Map.Entry<String, EntityType<?>> entry : entities.entrySet()) {
            String name = entry.getKey();
            EntityType<?> entity = entry.getValue();
            ENTITIES.register(name, () -> entity);
        }

        // Регистрация в eventBus
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
        ENTITIES.register(eventBus);
    }
}