package client.console;

import io.netty.channel.Channel;
import protocol.request.GroupMessageRequestPacket;

import java.util.Scanner;

public class SendToGroupComandConsole implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入群号和消息，用空格隔开：");
        String groupId = scanner.next();
        String message = scanner.next();
        GroupMessageRequestPacket groupMessageRequestPacket = new GroupMessageRequestPacket();
        groupMessageRequestPacket.setToGroupId(groupId);
        groupMessageRequestPacket.setMessage(message);
        channel.writeAndFlush(groupMessageRequestPacket);
    }
}
