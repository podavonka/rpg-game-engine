package cz.cvut.fel.pjv.engine.model.net.packets;

import cz.cvut.fel.pjv.engine.model.net.GameClient;
import cz.cvut.fel.pjv.engine.model.net.GameServer;

/**
 * Package which is used to login
 * into the game in multiplayer.
 */
public class Packet00Login extends Packet{

    private String username;

    public Packet00Login(byte[] data) {
        super(00);
        this.username = readData(data);
    }

    public Packet00Login(String username) {
        super(00);
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
        return ("00" + this.username).getBytes();
    }

    public String getUsername(){
        return username;
    }
}
