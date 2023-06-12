package org.example.netty.group_chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.example.netty.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty.group_chat.engine.utils.KDateUtil;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.engine.ClientProtocolMgr;
import org.example.netty.group_chat.engine.IRequestHandler;
import org.example.netty.group_chat.logger.Debug;

public class GroupChatServerDistributorHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        GlobalSessionMgr.Instance.addChannel(channel);
        Debug.info(channel.remoteAddress() + "连接成功...");
    }

    //表示channel 处于活动状态, 提示 xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Debug.info(ctx.channel().remoteAddress() + " 上线了~");
    }

    //表示channel 处于不活动状态, 提示 xx离线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //  LFH 如何设置不活跃状态的时间戳？  通过IdleSateHandler(检测心跳handler)来进行设置

        Debug.info(GlobalSessionMgr.Instance.getName(channel) + " 离线了...");

        //  当玩家离线后，将会话信息删除
        GlobalSessionMgr.Instance.unbind(channel);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet requestPack) throws Exception {
        int requestId = requestPack.getRequestId();
        long now = KDateUtil.Instance.now();

        ChatClientRequestHandlerBase handler = ClientProtocolMgr.Instance.createRequestById(requestId);
        if(handler == null){
            Debug.err("找不到requestId对应的handler");
            return;
        }

        try{
            //  对协议进行处理，应该有一个返回数据
            handler.process(ctx, requestPack, now);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        Debug.info(GlobalSessionMgr.Instance.getName(channel) + " 断开连接...");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //  LFH 作用是什么，关闭后，仍然可以从其中获取到channel的信息
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;

        switch (event.state()){
            case READER_IDLE:
                break;
            case WRITER_IDLE:
            case ALL_IDLE:
                Debug.warn(GlobalSessionMgr.Instance.getName(ctx.channel()) + " ----超时----");
                ctx.close();
                break;
        }

    }


}
