package ru.mrbedrockpy.bedpackets.core.packet;

import ru.mrbedrockpy.bedpackets.core.util.FriendlyByteBuf;

public interface Packet {

    String getName();

    void write(FriendlyByteBuf out);

    void handle(PacketContext context);

}
