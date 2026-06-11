package ru.mrbedrockpy.bedpackets.netty.util;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class EventLoopFactory {

    public static EventLoopGroup create() {
        return new NioEventLoopGroup();
    }
}
