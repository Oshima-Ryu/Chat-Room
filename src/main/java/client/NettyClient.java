package client;

import client.console.ConsoleCommandManager;
import client.console.LoginConsoleCommand;
import client.handler.*;
import codec.PacketDecoder;
import codec.PacketEncoder;
import codec.Spliter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import util.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch){
//                        ch.pipeline().addLast(new ClientHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        ch.pipeline().addLast(new GroupMassegeResponseHandler());
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()){
                System.out.println(new Date() + ":连接成功！");
                Channel channel = ((ChannelFuture)future).channel();
                startConsoleThread(channel);
            } else if (retry == 0){
                System.err.println("重试次数已用完，放弃连接！");
            }else{
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.err.println(new Date() + ":连接失败，第" + order +"次重连。。。");
                bootstrap.config().group().schedule(()->connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel){
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner sc = new Scanner(System.in);

        new Thread(()->{
            while(!Thread.interrupted()){
                if(!SessionUtil.hasLogin(channel)){
                    loginConsoleCommand.exec(sc, channel);
                }else{
                    consoleCommandManager.exec(sc, channel);
                }
            }
        }).start();

//        Scanner sc = new Scanner(System.in);
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        new Thread(()->{
//            while(!Thread.interrupted()){
//                if(!SessionUtil.hasLogin(channel)){
//                    System.out.println("输入用户名登录：");
//                    String userName = sc.nextLine();
//                    loginRequestPacket.setUserName(userName);
//                    loginRequestPacket.setPassword("pwd");
//                    channel.writeAndFlush(loginRequestPacket);
//                    waitForLoginResponse();
//                }else{
//                    String toUserId = sc.next();
//                    String message = sc.next();
//                    channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
//                }
//            }
//        }).start();
    }

    private static void waitForLoginResponse(){
        try{
            Thread.sleep(5000);
        }catch (InterruptedException ignored){}
    }
}
