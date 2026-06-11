package ru.mrbedrockpy.bedpackets.core.packet;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import ru.mrbedrockpy.bedpackets.core.connection.Connection;
import ru.mrbedrockpy.bedpackets.core.util.FriendlyByteBuf;

public class PacketManager {

    public static final PacketManager INSTANCE = new PacketManager();

    private final Map<String, Integer> ids = new HashMap<>();
    private final Map<String, Function<FriendlyByteBuf, Packet>> factories = new HashMap<>();

    private Consumer<Connection> connectListener;
    private BiConsumer<Connection, String> disconnectListener;

    private int nextId = 0;

    public void register(String name, Function<FriendlyByteBuf, Packet> factory) {
        this.ids.put(name, nextId++);
        this.factories.put(name, factory);
    }

    public Packet create(int id, FriendlyByteBuf buf) {
        String name = this.getNameById(id);
        if (name == null) throw new IllegalStateException("Unknown packet id: " + id);
        Function<FriendlyByteBuf, Packet> factory = this.factories.get(name);
        if (factory == null) throw new IllegalStateException("Unknown packet id: " + id);
        return factory.apply(buf);
    }

    public void handle(Connection connection, Packet packet) {
        packet.handle(new PacketContext(connection, packet));
    }

    public int getByName(String name) {
        Integer id = this.ids.get(name);
        if (id == null) throw new IllegalArgumentException("Unknown packet name: " + name);
        return id;
    }

    public String getNameById(int id) {
        for (Map.Entry<String, Integer> entry : this.ids.entrySet())
            if (entry.getValue() == id) return entry.getKey();
        return null;
    }

    public void handleConnect(Connection connection) {
        if (connectListener != null) connectListener.accept(connection);
    }

    public void handleDisconnect(Connection connection, String reason) {
        if (disconnectListener != null) disconnectListener.accept(connection, reason);
    }

    public void onConnect(Consumer<Connection> listener) {
        this.connectListener = listener;
    }

    public void onDisconnect(BiConsumer<Connection, String> listener) {
        this.disconnectListener = listener;
    }
}
