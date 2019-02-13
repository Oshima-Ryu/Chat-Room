package protocol.response;

import lombok.Data;
import protocol.Packet;

import static protocol.command.Command.JOIN_GROUP_RESPONSE;

@Data
public class JoinGroupResponsePacket extends Packet {
    private boolean success;
    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}