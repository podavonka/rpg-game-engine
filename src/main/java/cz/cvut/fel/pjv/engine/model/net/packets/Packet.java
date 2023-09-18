package cz.cvut.fel.pjv.engine.model.net.packets;


import cz.cvut.fel.pjv.engine.model.net.GameClient;
import cz.cvut.fel.pjv.engine.model.net.GameServer;

/**
 * Abstract class for creating packages that
 * will be sent from server to client or
 * from client to server during multiplayer.
 */
public abstract class Packet {

    public static enum PacketTypes {
        INVALID(-1), LOGIN(00), DISCONNECT(01), MOVE(02);

        private int packetId;
        private PacketTypes (int packetId){
            this.packetId = packetId;
        }

        public int getId() {
            return packetId;
        }
    }

    public byte packetId;

    public Packet(int packetId) {
        this.packetId = (byte) packetId;
    }

    /**
     * Send data to client.
     */
    public abstract void sendData(GameClient client);

    /**
     * Send data to server.
     */
    public abstract void sendData(GameServer server);

    /**
     * Read data from package and deletes the package code.
     *
     * @param data Data to be read.
     * @return data without data code.
     */
    public String readData(byte[] data) {
        String message = new String(data).trim();
        return message.substring(2);
    }

    public abstract byte[] getData();

    /**
     * Looks for package type.
     *
     * @param packetId Id we want to find in enum.
     * @return package type number if it is in enum,
     *         INVALID package type if it is not.
     */
    public static PacketTypes lookupPacket(String packetId) {
        try {
            return lookupPacket(Integer.parseInt(packetId));
        } catch (NumberFormatException e) {
            return PacketTypes.INVALID;
        }
    }

    /**
     * Looks for package type.
     *
     * @param id Id we want to find in enum.
     * @return package type number if it is in enum,
     *         INVALID package type if it is not.
     */
    public static PacketTypes lookupPacket(int id) {
        for (PacketTypes p: PacketTypes.values()){
            if (p.getId() == id){
                return p;
            }
        }
        return PacketTypes.INVALID;
    }
}
