package ru.mrbedrockpy.bedpackets.netty.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.mrbedrockpy.bedpackets.core.connection.Connection;
import ru.mrbedrockpy.bedpackets.core.packet.PacketManager;
import ru.mrbedrockpy.bedpackets.core.transport.Transport;
import ru.mrbedrockpy.bedpackets.core.util.Dist;
import ru.mrbedrockpy.bedpackets.netty.NettyConnection;
import ru.mrbedrockpy.bedpackets.netty.pipeline.PacketDecoder;
import ru.mrbedrockpy.bedpackets.netty.pipeline.PacketEncoder;
import ru.mrbedrockpy.bedpackets.netty.pipeline.PacketHandler;
import ru.mrbedrockpy.bedpackets.netty.util.EventLoopFactory;

@RequiredArgsConstructor
public class NettyClientTransport implements Transport {

    private final String host;
    private final int port;

    private EventLoopGroup group;
    @Getter private Connection connection;

    @Override
    public RuntimeException start() {
        if (Dist.get() == null) Dist.set(Dist.CLIENT);
        else return new RuntimeException("Dist already initialized!");
        group = EventLoopFactory.create();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(
                                    new LengthFieldBasedFrameDecoder(1_000_000, 0, 4, 0, 4),
                                    new LengthFieldPrepender(4),
                                    new PacketDecoder(),
                                    new PacketEncoder(),
                                    new PacketHandler(PacketManager.INSTANCE)
                            );
                        }
                    });
            Channel channel = bootstrap.connect(host, port).sync().channel();
            this.connection = new NettyConnection(channel);
        } catch (Exception e) {
            return new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void stop() {
        if (connection != null) connection.close();
        if (group != null) group.shutdownGracefully();
    }
}