package ru.mrbedrockpy.bedpackets.core.util;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;

@Getter
@RequiredArgsConstructor
public class FriendlyByteBuf {

    private final ByteBuf buf;

    public void write(byte num) {
        this.buf.writeByte(num);
    }

    public void write(short num) {
        this.buf.writeShort(num);
    }

    public void write(int num) {
        this.buf.writeInt(num);
    }

    public void write(long num) {
        this.buf.writeLong(num);
    }

    public void write(float num) {
        this.buf.writeFloat(num);
    }

    public void write(double num) {
        this.buf.writeDouble(num);
    }

    public void write(boolean state) {
        this.buf.writeBoolean(state);
    }

    public void write(char sym) {
        this.buf.writeChar(sym);
    }

    public void write(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        writeVarInt(bytes.length);
        this.buf.writeBytes(bytes);
    }

    public void write(byte[] bytes) {
        writeVarInt(bytes.length);
        this.buf.writeBytes(bytes);
    }

    public void writeVarInt(int value) {
        while ((value & 0xFFFFFF80) != 0L) {
            buf.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }
        buf.writeByte(value);
    }

    public byte readByte() {
        return this.buf.readByte();
    }

    public short readShort() {
        return this.buf.readShort();
    }

    public int readInt() {
        return this.buf.readInt();
    }

    public long readLong() {
        return this.buf.readLong();
    }

    public float readFloat() {
        return this.buf.readFloat();
    }

    public double readDouble() {
        return this.buf.readDouble();
    }

    public boolean readBoolean() {
        return this.buf.readBoolean();
    }

    public char readChar() {
        return this.buf.readChar();
    }

    public String readString() {
        int length = readVarInt();
        byte[] bytes = new byte[length];
        this.buf.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public byte[] readBytes() {
        int length = readVarInt();
        byte[] bytes = new byte[length];
        this.buf.readBytes(bytes);
        return bytes;
    }

    public int readVarInt() {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = buf.readByte();
            int value = (read & 0x7F);
            result |= (value << (7 * numRead));
            if (++numRead > 5) throw new RuntimeException("VarInt too big");
        } while ((read & 0x80) != 0);
        return result;
    }
}