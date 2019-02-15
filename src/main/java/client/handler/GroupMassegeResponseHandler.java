package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.response.GroupMessageResponsePacket;

public class GroupMassegeResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket groupMessageResponsePacket) throws Exception {
        System.out.println("收到群["+ groupMessageResponsePacket.getFromGroupId() +"]内[" + groupMessageResponsePacket.getFromUser() + "]发来的消息：" + groupMessageResponsePacket.getMessage());
    }
}
