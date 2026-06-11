package ru.mrbedrockpy.bedpackets.core.transport;

public interface Transport {
    RuntimeException start();
    void stop();
}
