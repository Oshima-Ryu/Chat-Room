package client.console;

import io.netty.channel.Channel;
import protocol.request.JoinGroupRequestPacket;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();
        System.out.print("输入要加入的群号：");
        String groupId = scanner.next();
        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
