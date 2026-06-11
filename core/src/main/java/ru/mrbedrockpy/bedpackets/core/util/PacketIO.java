package ru.mrbedrockpy.bedpackets.core.util;

import io.netty.buffer.ByteBuf;
import ru.mrbedrockpy.bedpackets.core.packet.Packet;
import ru.mrbedrockpy.bedpackets.core.packet.PacketManager;

public class PacketIO {

    public static void write(Packet packet, ByteBuf buf) {
        VarInt.write(buf, PacketManager.INSTANCE.getByName(packet.getName()));
        packet.write(new FriendlyByteBuf(buf));
    }

    public static Packet read(ByteBuf buf) {
        return PacketManager.INSTANCE.create(VarInt.read(buf), new FriendlyByteBuf(buf));
    }
}