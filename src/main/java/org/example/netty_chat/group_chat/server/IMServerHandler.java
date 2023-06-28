package org.example.netty_chat.group_chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.netty_chat.group_chat.engine.ChatClientRequestHandlerBase;
import org.example.netty_chat.group_chat.engine.utils.KDateUtil;
import org.example.netty_chat.group_chat.bean.Packet;
import org.example.netty_chat.group_chat.engine.ClientProtocolMgr;
import org.example.netty_chat.group_chat.logger.Debug;

/**
 * IM全名Instant Messaging，中文意思是“即时通讯”
 */
@ChannelHandler.Sharable
public class IMServerHandler extends SimpleChannelInboundHandler<Packet> {

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
        /**
         * Netty服务器启动：
         *  1.一个NioEventLoopGroup用来监听客户端连接
         *  2.创建了核心cpu数目 * 2的NioEventLoopGroup来监听并处理客户端事件
         * 高并发问题：
         *  Netty通过遍历NioEventLoopGroup中的channel来事件处理，channel达到几万或者几十万的量级，
         *  对应业务处理需要花费一些时间的时候，一个channel的时候，会导致阻塞剩下的channel的执行，拖慢执行速度,造成高延迟。
         *
         *  处理方式：
         *      创建线程进行业务的执行
         *          缺点：
         *              会创建大量的线程，线程之前的上下文切换，也会导致性能的下降
         */

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




}
