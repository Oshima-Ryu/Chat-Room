package protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import protocol.Packet;

import static protocol.command.Command.JOIN_GROUP_REQUEST;

@Data
@NoArgsConstructor
public class JoinGroupRequestPacket extends Packet {
    String groupId;

    public JoinGroupRequestPacket(String groupId){
        this.groupId = groupId;
    }
    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
