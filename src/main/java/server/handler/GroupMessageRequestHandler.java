package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.request.GroupMessageRequestPacket;
import protocol.response.GroupMessageResponsePacket;
import util.SessionUtil;

public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {
        String groupId = groupMessageRequestPacket.getToGroupId();
        String message = groupMessageRequestPacket.getMessage();
        String user = SessionUtil.getSession(ctx.channel()).toString();
        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setFromGroupId(groupId);
        groupMessageResponsePacket.setFromUser(user);
        groupMessageResponsePacket.setMessage(message);
        System.out.println("群["+ groupId +"]内[" + user + "]发送群消息");
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(groupMessageResponsePacket);
    }
}
