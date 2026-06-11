package ru.mrbedrockpy.bedpackets.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;
import ru.mrbedrockpy.bedpackets.core.packet.PacketManager;
import ru.mrbedrockpy.bedpackets.core.util.FriendlyByteBuf;
import ru.mrbedrockpy.bedpackets.core.util.PacketIO;
import ru.mrbedrockpy.bedpackets.core.util.VarInt;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        out.add(PacketIO.read(in));
    }
}