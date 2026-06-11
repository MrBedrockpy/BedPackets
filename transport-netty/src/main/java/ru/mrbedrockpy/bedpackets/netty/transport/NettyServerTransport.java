package ru.mrbedrockpy.bedpackets.netty.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.RequiredArgsConstructor;
import ru.mrbedrockpy.bedpackets.core.packet.PacketManager;
import ru.mrbedrockpy.bedpackets.core.transport.Transport;
import ru.mrbedrockpy.bedpackets.core.util.Dist;
import ru.mrbedrockpy.bedpackets.netty.pipeline.PacketDecoder;
import ru.mrbedrockpy.bedpackets.netty.pipeline.PacketEncoder;
import ru.mrbedrockpy.bedpackets.netty.pipeline.PacketHandler;
import ru.mrbedrockpy.bedpackets.netty.util.EventLoopFactory;

@RequiredArgsConstructor
public class NettyServerTransport implements Transport {

    private final int port;

    private EventLoopGroup boss;
    private EventLoopGroup worker;

    @Override
    public RuntimeException start() {
        if (Dist.get() == null) Dist.set(Dist.SERVER);
        else return new RuntimeException("Dist already initialized!");
        boss = EventLoopFactory.create();
        worker = EventLoopFactory.create();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>() {
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
            bootstrap.bind(port).sync();
        } catch (Exception e) {
            return new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void stop() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }
}
