package ru.mrbedrockpy.bedpackets.example;

import ru.mrbedrockpy.bedpackets.core.packet.PacketManager;
import ru.mrbedrockpy.bedpackets.netty.transport.NettyServerTransport;

public class ExampleServer {

    public static void main(String[] args) {
        PacketManager.INSTANCE.register("test", TestPacket::new);

        PacketManager.INSTANCE.onConnect(connection -> {
            System.out.println("Player connected");
            connection.send(new TestPacket(321));
        });
        PacketManager.INSTANCE.onDisconnect((connection, reason) ->
                System.out.println("Disconnected: " + reason));

        NettyServerTransport transport = new NettyServerTransport(25565);
        RuntimeException e = transport.start();
        if (e != null) throw e;
    }
}
