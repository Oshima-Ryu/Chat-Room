package protocol.response;

import protocol.Packet;

import static protocol.command.Command.QUIT_GROUP_RESPONSE;

public class QuitGroupResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
