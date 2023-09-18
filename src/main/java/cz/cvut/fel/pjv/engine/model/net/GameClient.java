package cz.cvut.fel.pjv.engine.model.net;


import cz.cvut.fel.pjv.engine.model.game.state.GameState;
import cz.cvut.fel.pjv.engine.model.net.packets.Packet;
import cz.cvut.fel.pjv.engine.model.net.packets.Packet00Login;
import cz.cvut.fel.pjv.engine.model.net.packets.Packet01Disconnect;
import cz.cvut.fel.pjv.engine.model.net.packets.Packet02Move;
import cz.cvut.fel.pjv.engine.model.object.TargetZone;
import cz.cvut.fel.pjv.engine.model.object.entity.PlayerMP;

import java.io.IOException;
import java.net.*;

/**
 * Client class which is used in multiplayer.
 */
public class GameClient extends Thread{

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private GameState game;

    public GameClient(GameState game, String ipAddress) {
        this.game = game;
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());

        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0,2));
        Packet packet = null;
        switch (type){
            default:
            case INVALID: break;
            case LOGIN:
                packet = new Packet00Login(data);
                System.out.println("["+address.getHostAddress()+":"+port+"]"+((Packet00Login)packet).getUsername()+" has joined the game...");
                TargetZone targetZone= new TargetZone();
                PlayerMP player = new PlayerMP(game, game.getSpriteStorage(), targetZone,((Packet00Login)packet).getUsername(), address, port);
                game.getGameObjects().add(player);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("["+address.getHostAddress()+":"+port+"]"+((Packet01Disconnect)packet).getUsername()+" has left the world...");
                game.removePlayerMP(((Packet01Disconnect) packet).getUsername());
                break;
            case MOVE:
                packet = new Packet02Move(data);
                handleMove((Packet02Move)packet);
        }
    }



    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMove(Packet02Move packet) {
        this.game.movePlayer(packet.getUsername(), packet.getX(), packet.getY());
    }
}
