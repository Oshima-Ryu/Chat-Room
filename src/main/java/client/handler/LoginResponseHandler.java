package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.request.LoginRequestPacket;
import protocol.response.LoginResponsePacket;
import session.Session;
import util.LoginUtil;
import util.SessionUtil;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

//    public void channelActive(ChannelHandlerContext ctx){
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
////        loginRequestPacket.setUserId(UUID.randomUUID().toString());
//        loginRequestPacket.setUsername("www");
//        loginRequestPacket.setPassword("pwd");
//        ctx.channel().writeAndFlush(loginRequestPacket);
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUserName();

        if (loginResponsePacket.isSuccess()){
            System.out.println(new Date() + "[" + userName + "]登陆成功，userId为：" + userId);
            SessionUtil.bindSession(new Session(userId, userName) ,ctx.channel());
        } else {
            System.out.println(new Date() + ":客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    public void channelInactive(ChannelHandlerContext ctx){
        System.out.println("客户端连接被关闭");
    }

}
