package client.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.request.ListGroupMembersRequestPacket;
import protocol.response.ListGroupMembersResponsePacket;
import session.Session;
import util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket listGroupMembersResponsePacket) throws Exception {
        List<Session> sessionList = listGroupMembersResponsePacket.getSessionList();
        List<String> nameList = new ArrayList<>();
        for (Session session : sessionList){
            nameList.add(session.getUserName());
        }
        String groupId = listGroupMembersResponsePacket.getGroupId();
        System.out.println("["+ groupId +"]群内有：" + nameList);
    }
}
