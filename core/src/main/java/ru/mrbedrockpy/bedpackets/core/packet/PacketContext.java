package ru.mrbedrockpy.bedpackets.core.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mrbedrockpy.bedpackets.core.connection.Connection;

@Getter
@AllArgsConstructor
public class PacketContext {

    private final Connection connection;
    private final Packet packet;

}
