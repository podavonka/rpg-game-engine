package cz.cvut.fel.pjv.engine.model.net.packets;


import cz.cvut.fel.pjv.engine.model.net.GameClient;
import cz.cvut.fel.pjv.engine.model.net.GameServer;

/**
 * Package which is used to disconnect
 * from the game in multiplayer.
 */
public class Packet01Disconnect extends Packet{

    private String username;

    public Packet01Disconnect(byte[] data) {
        super(01);
        this.username = readData(data);
    }

    public Packet01Disconnect(String username) {
        super(01);
        this.username = username;
    }

    @Override
    public void sendData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void sendData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("01" + this.username).getBytes();
    }

    public String getUsername(){
        return username;
    }
}
