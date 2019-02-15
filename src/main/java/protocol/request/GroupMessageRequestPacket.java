package protocol.request;

import lombok.Data;
import protocol.Packet;

import static protocol.command.Command.GROUP_MESSAGE_REQUEST;

@Data
public class GroupMessageRequestPacket extends Packet {
    private String toGroupId;
    private String message;
    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
