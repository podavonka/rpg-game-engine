package cz.cvut.fel.pjv.engine.model.net;

import cz.cvut.fel.pjv.engine.model.game.state.GameState;
import cz.cvut.fel.pjv.engine.model.net.packets.Packet;
import cz.cvut.fel.pjv.engine.model.net.packets.Packet00Login;
import cz.cvut.fel.pjv.engine.model.net.packets.Packet01Disconnect;
import cz.cvut.fel.pjv.engine.model.net.packets.Packet02Move;
import cz.cvut.fel.pjv.engine.model.object.TargetZone;
import cz.cvut.fel.pjv.engine.model.object.entity.PlayerMP;
import cz.cvut.fel.pjv.engine.model.utils.Position;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Server class which is used in multiplayer.
 */
public class GameServer extends Thread{

    private DatagramSocket socket;
    private GameState game;
    private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();

    public GameServer(GameState game) {
        this.game = game;
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
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

//            String message = new String(packet.getData());
//            System.out.println("CLIENT ["+packet.getAddress().getHostAddress() +":"+packet.getPort() +"] > " + message);
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
                System.out.println("["+address.getHostAddress()+":"+port+"]"+((Packet00Login)packet).getUsername()+" has connected...");
                TargetZone targetZone= new TargetZone();
                PlayerMP player = new PlayerMP(game, game.getSpriteStorage(), targetZone,((Packet00Login)packet).getUsername(), address, port);
                addConnection(player, (Packet00Login) packet);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                System.out.println("["+address.getHostAddress()+":"+port+"]"+((Packet01Disconnect)packet).getUsername()+" has left...");
                removeConnection((Packet01Disconnect) packet);
                break;
            case MOVE:
                packet = new Packet02Move(data);
//                System.out.println(((Packet02Move)packet).getUsername() + " has moved to " + ((Packet02Move)packet).getX() + "," + ((Packet02Move)packet).getY());
                this.handleMove(((Packet02Move)packet));
                break;
        }
    }

    /**
     * Add new client.
     *
     * @param player Player of the new client.
     */
    public void addConnection(PlayerMP player, Packet00Login packet) {
        boolean allreadyConnected = false;
        for(PlayerMP p: this.connectedPlayers) {
            if (player.getUsername().equalsIgnoreCase(p.getUsername())){
                if (p.ipAddress == null){
                    p.ipAddress = player.ipAddress;
                }
                if (p.port == -1) {
                    p.port = player.port;
                }
                allreadyConnected =true;
            }else {
                sendData(packet.getData(), p.ipAddress, p.port);

                packet = new Packet00Login(p.getUsername());
                sendData(packet.getData(), player.ipAddress, player.port);

            }

        }
        if (!allreadyConnected){
            this.connectedPlayers.add(player);
        }
    }

    /**
     * Remove client.
     */
    public void removeConnection(Packet01Disconnect packet) {
        this.connectedPlayers.remove(getPlayerMPindex(packet.getUsername()));
        packet.sendData(this);
    }

    /**
     * Get player in multiplayer.
     *
     * @param username Username of the player.
     */
    public PlayerMP getPlayerMP(String username){
        for (PlayerMP player:this.connectedPlayers){
            if(player.getUsername().equals(username)){
                return player;
            }
        }
        return null;
    }

    /**
     * Get player index in multiplayer.
     *
     * @param username Username of the player.
     */
    public int getPlayerMPindex(String username){
        int index = 0;
        for (PlayerMP player:this.connectedPlayers){
            if(player.getUsername().equals(username)){
                break;
            }
            index++;
        }
        return index;
    }

    /**
     * Send data to clients.
     */
    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sending data to every client.
     *
     * @param data Data to be sent.
     */
    public void sendDataToAllClients(byte[] data) {
        for (PlayerMP p: connectedPlayers) {
            sendData(data, p.ipAddress, p.port);
        }
    }

    private void handleMove(Packet02Move packet) {
        if (getPlayerMP(packet.getUsername()) != null){
            int index = getPlayerMPindex(packet.getUsername());
            this.connectedPlayers.get(index).setPosition(new Position(packet.getX(), packet.getY()));
            packet.sendData(this);
        }
    }
}
