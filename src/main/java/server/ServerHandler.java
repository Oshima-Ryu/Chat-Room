package server;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.Packet;
import protocol.PacketCodeC;
import protocol.request.LoginRequestPacket;
import protocol.request.MessageRequestPacket;
import protocol.response.LoginResponsePacket;
import protocol.response.MessageResponsePacket;
import util.LoginUtil;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf requestByteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);
        System.out.println(packet);

        if (packet instanceof LoginRequestPacket){
            System.out.println(new Date() + ":客户端开始登陆");
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            if (valid(loginRequestPacket)){
//                LoginUtil.markAsLogin(ctx.channel());
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ":登陆成功！");
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号密码验证失败");
                System.out.println(new Date() + ":登录失败！");
            }
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket){
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            System.out.println(new Date() + ":收到客户端消息：" + messageRequestPacket.getMessage());
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
