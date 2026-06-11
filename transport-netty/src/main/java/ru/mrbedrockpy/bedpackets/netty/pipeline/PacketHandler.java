package ru.mrbedrockpy.bedpackets.netty.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.RequiredArgsConstructor;
import ru.mrbedrockpy.bedpackets.core.packet.Packet;
import ru.mrbedrockpy.bedpackets.core.packet.PacketManager;
import ru.mrbedrockpy.bedpackets.netty.NettyConnection;

@RequiredArgsConstructor
public class PacketHandler extends SimpleChannelInboundHandler<Packet> {

    private final PacketManager manager;

    private static final AttributeKey<NettyConnection> CONNECTION_KEY =
            AttributeKey.valueOf("connection");

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        NettyConnection connection = new NettyConnection(ctx.channel());
        ctx.channel().attr(CONNECTION_KEY).set(connection);
        manager.handleConnect(connection);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        NettyConnection connection = ctx.channel().attr(CONNECTION_KEY).get();
        if (connection != null) manager.handleDisconnect(connection, "Connection closed");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
        NettyConnection connection = ctx.channel().attr(CONNECTION_KEY).get();
        manager.handle(connection, packet);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        NettyConnection connection = ctx.channel().attr(CONNECTION_KEY).get();
        if (connection != null) manager.handleDisconnect(connection, cause.getMessage());
        ctx.close();
    }
}
