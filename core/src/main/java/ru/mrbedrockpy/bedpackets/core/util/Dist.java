package ru.mrbedrockpy.bedpackets.core.util;

public enum Dist {

    SERVER, CLIENT

    ;

    private static Dist instance;

    public static Dist get() {
        return instance;
    }
    public static void set(Dist instance) {
        Dist.instance = instance;
    }
}
