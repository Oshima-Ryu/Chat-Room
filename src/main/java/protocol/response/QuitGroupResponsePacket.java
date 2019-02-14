package protocol.response;

import lombok.Data;
import protocol.Packet;

import static protocol.command.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {
    private boolean success;
    private String groupId;
    private String reason;
    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
