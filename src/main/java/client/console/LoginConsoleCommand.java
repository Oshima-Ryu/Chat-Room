package client.console;

import io.netty.channel.Channel;
import protocol.request.LoginRequestPacket;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        System.out.println("输入用户名登录：");
        loginRequestPacket.setUserName(scanner.nextLine());
        loginRequestPacket.setPassword("pwd");

        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignore){}
    }
}