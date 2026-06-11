package ru.mrbedrockpy.bedpackets.netty;

import io.netty.channel.Channel;
import ru.mrbedrockpy.bedpackets.core.connection.Connection;
import ru.mrbedrockpy.bedpackets.core.packet.Packet;

public class NettyConnection implements Connection {

    private final Channel channel;

    public NettyConnection(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void send(Packet packet) {
        channel.writeAndFlush(packet);
    }

    @Override
    public void disconnect(String reason) {
        System.out.println("Disconnect: " + reason);
        channel.close();
    }

    @Override
    public void close() {
        channel.close();
    }

    @Override
    public boolean isConnected() {
        return channel.isActive();
    }
}
