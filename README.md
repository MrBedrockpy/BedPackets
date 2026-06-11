## BedPackets

BedPackets is a lightweight, flexible packet-based networking library for Java, designed to simplify client-server communication.

It provides a minimal API for defining, serializing, and handling packets, along with a transport layer based on Netty.

----

### Features

* Simple packet abstraction
* Automatic serialization/deserialization
* No reflection
* Transport layer based on Netty
* Event-driven connection handling
* Client/Server side detection

----

### Installation

Add the compiled JAR to your project:

#### Gradle:

```gradle
dependencies {
    implementation files("libs/bedpackets.jar")
}
```

#### Maven:

```xml
<dependency>
    <groupId>your.group</groupId>
    <artifactId>bedpackets</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/libs/bedpackets.jar</systemPath>
</dependency>
```

----

### Usage (full code in example module)

#### Packet definition

```java
public class TestPacket implements Packet {
    
    private final int value;
    
    public TestPacket(int value) {
        this.value = value;
    }
    
    public TestPacket(FriendlyByteBuf buf) {
        this.value = buf.readInt();
    }
    
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.write(value);
    }
    
    @Override
    public String getName() {
        return "test";
    }
    
    @Override
    public void handle(PacketContext context) {
        System.out.println("Received: " + value);
    }
}
```

----

#### Server-side setup

```java
// register all packets here
PacketManager.INSTANCE.register("test", TestPacket::new);

// connection lifecycle handlers (connect / disconnect)
PacketManager.INSTANCE.onConnect(connection -> {
    System.out.println("Client connected");
    // sending packet to client
    connection.send(new TestPacket(123));
});
PacketManager.INSTANCE.onDisconnect((connection, reason) -> {
    System.out.println("Disconnected: " + reason);
});

// server initialization
NettyServerTransport server = new NettyServerTransport(25565);
RuntimeException error = server.start();
if (error != null) throw error;
```

----

#### Client-side setup

```java
// register all packets here (must match server)
PacketManager.INSTANCE.register("test", TestPacket::new);

// client connection initialization
NettyClientTransport client = new NettyClientTransport("localhost", 25565);
RuntimeException error = client.start();
if (error != null) throw error;
```