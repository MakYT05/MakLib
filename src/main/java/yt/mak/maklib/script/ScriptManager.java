package yt.mak.maklib.script;

import com.google.gson.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ScriptManager {

    public static void loadScripts(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray events = json.getAsJsonArray("events");

            for (JsonElement eventElement : events) {
                JsonObject event = eventElement.getAsJsonObject();
                String condition = event.get("condition").getAsString();
                JsonArray actions = event.getAsJsonArray("actions");

                if (checkCondition(condition)) { executeActions(actions); }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkCondition(String condition) {
        switch (condition) {
            case "player_at_coordinates":
                return checkPlayerCoordinates(100, 64, 200);
            case "time_passed":
                return true;
            default:
                return false;
        }
    }

    private static boolean checkPlayerCoordinates(int x, int y, int z) {
        ServerLevel level = (ServerLevel) ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD);

        if (level == null) return false;

        List<ServerPlayer> players = level.players();

        for (ServerPlayer player : players) {
            if (player.getX() == x && player.getY() == y && player.getZ() == z) { return true; }
        }
        return false;
    }

    private static void executeActions(JsonArray actions) {
        for (JsonElement actionElement : actions) {
            JsonObject action = actionElement.getAsJsonObject();
            String actionType = action.get("action").getAsString();

            switch (actionType) {
                case "spawn_entity":
                    String entity = action.get("entity").getAsString();

                    int[] coordinates = parseCoordinates(action.get("coordinates"));

                    spawnEntity(entity, coordinates[0], coordinates[1], coordinates[2]);

                    break;
                case "send_message":
                    String message = action.get("message").getAsString();
                    String color = action.get("color").getAsString();

                    sendMessage(message, Integer.parseInt(color));

                    break;
                case "spawn_block":
                    String blockType = action.get("block").getAsString();
                    int[] blockCoordinates = parseCoordinates(action.get("coordinates"));

                    spawnBlock(blockType, blockCoordinates[0], blockCoordinates[1], blockCoordinates[2]);

                    break;
            }
        }
    }

    private static void spawnEntity(String entityName, int x, int y, int z) {
        ResourceLocation entityLocation = new ResourceLocation(entityName);
        EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(entityLocation);
        if (entityType != null) {
            entityType.spawn(level, null, null, new BlockPos(x, y, z), MobCategory.MISC, true, false);
        }

    }

    private static void sendMessage(String message, int color) {
        TextColor textColor = TextColor.fromRgb(color);

        Component messageComponent = new TextComponent(message).setStyle(Style.EMPTY.withColor(textColor));

        ServerLevel level = (ServerLevel) ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD);

        if (level != null) { level.getServer().getPlayerList().broadcastSystemMessage(messageComponent, false); }
    }

    private static void spawnBlock(String blockName, int x, int y, int z) {
        ResourceLocation blockLocation = new ResourceLocation("minecraft", blockName);
        Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);

        if (block != null) {
            ServerLevel level = (ServerLevel) ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD);

            if (level != null) {
                BlockPos position = new BlockPos(x, y, z);

                level.setBlock(position, block.defaultBlockState(), 3);
            }
        }
    }

    private static int[] parseCoordinates(JsonElement coordinatesElement) {
        JsonArray coordinates = coordinatesElement.getAsJsonArray();

        return new int[] {
                coordinates.get(0).getAsInt(),
                coordinates.get(1).getAsInt(),
                coordinates.get(2).getAsInt()
        };
    }

    public static void main(String[] args) { loadScripts("scripts.json"); }
}