package cz.cvut.fel.pjv.engine.model.net.packets;


import cz.cvut.fel.pjv.engine.model.net.GameClient;
import cz.cvut.fel.pjv.engine.model.net.GameServer;

/**
 * Package which is used to process
 * layers moving while multiplayer.
 */
public class Packet02Move extends Packet{

    private String username;
    private int x,y;

    public Packet02Move(byte[] data) {
        super(02);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
    }

    public Packet02Move(String username, int x, int y) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
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
        return ("02" + this.username + "," + this.x + "," + this.y).getBytes();
    }

    public String getUsername(){
        return username;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}
