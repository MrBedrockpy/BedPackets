package ru.mrbedrockpy.bedpackets.core.connection;

import ru.mrbedrockpy.bedpackets.core.packet.Packet;

public interface Connection {

    void send(Packet packet);

    void disconnect(String reason);

    void close();

    boolean isConnected();

}
