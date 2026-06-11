package ru.mrbedrockpy.bedpackets.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.RequiredArgsConstructor;
import ru.mrbedrockpy.bedpackets.core.packet.Packet;
import ru.mrbedrockpy.bedpackets.core.packet.PacketManager;
import ru.mrbedrockpy.bedpackets.core.util.FriendlyByteBuf;
import ru.mrbedrockpy.bedpackets.core.util.PacketIO;
import ru.mrbedrockpy.bedpackets.core.util.VarInt;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        PacketIO.write(packet, out);
    }
}
