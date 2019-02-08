package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponseHandler> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponseHandler msg) throws Exception {
        System.out.println("群创建成功，id为[" );
    }
}
