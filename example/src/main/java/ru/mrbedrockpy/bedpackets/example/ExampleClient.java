package ru.mrbedrockpy.bedpackets.example;

import ru.mrbedrockpy.bedpackets.core.packet.PacketManager;
import ru.mrbedrockpy.bedpackets.netty.transport.NettyClientTransport;

public class ExampleClient {

    public static void main(String[] args) {
        PacketManager.INSTANCE.register("test", TestPacket::new);

        NettyClientTransport client = new NettyClientTransport("localhost", 25565);
        RuntimeException e = client.start();
        if (e != null) throw e;
        System.out.println("Client started");
    }
}
