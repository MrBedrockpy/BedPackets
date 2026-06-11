package ru.mrbedrockpy.bedpackets.example;

import lombok.Getter;
import ru.mrbedrockpy.bedpackets.core.packet.Packet;
import ru.mrbedrockpy.bedpackets.core.packet.PacketContext;
import ru.mrbedrockpy.bedpackets.core.util.Dist;
import ru.mrbedrockpy.bedpackets.core.util.FriendlyByteBuf;

@Getter
public class TestPacket implements Packet {

    private final int value;

    public TestPacket(int value) {
        this.value = value;
    }

    public TestPacket(FriendlyByteBuf buf) {
        this.value = buf.readInt();
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.write(this.value);
    }

    @Override
    public void handle(PacketContext context) {
        System.out.printf("Packet received on %s: %s", Dist.get().name().toLowerCase(), this.value);
    }
}