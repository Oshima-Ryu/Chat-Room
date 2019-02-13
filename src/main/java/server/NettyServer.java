package server;

import client.handler.CreateGroupResponseHandler;
import client.handler.LogoutResponseHandler;
import codec.PacketDecoder;
import codec.PacketEncoder;
import codec.Spliter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import server.handler.*;

import java.util.Date;

public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
//                        ch.pipeline().addLast(new ServerHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new CreateGroupRequestHandler());
                        ch.pipeline().addLast(new JoinGroupRequestHandler());
                        ch.pipeline().addLast(new LogoutRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
           if (future.isSuccess()){
               System.out.println(new Date() + ":端口[" + port + "]绑定成功！");
           }else{
               System.out.println("端口[" + port + "]绑定失败！");
           }
        });
    }
}
