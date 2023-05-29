package org.example.netty.group_chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.example.netty.group_chat.JObject;
import org.example.netty.group_chat.data_pack.ClientResponseData;
import org.example.netty.group_chat.data_pack.Packet;
import org.example.netty.group_chat.engine.ClientRequestMgr;
import org.example.netty.group_chat.engine.IRequestHandler;
import org.example.netty.group_chat.serialization.ChatSerializeType;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GroupChatServerDistributorHandler extends SimpleChannelInboundHandler<Packet> {

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final Map<Channel,String> channelNameMap = new HashMap<>();
    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.add(channel);
        System.out.println(channel.remoteAddress() + "连接成功...");
    }

    //表示channel 处于活动状态, 提示 xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    //表示channel 处于不活动状态, 提示 xx离线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //  LFH 如何设置不活跃状态的时间戳？
        System.out.println(ctx.channel().remoteAddress() + " 离线了~");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet requestPack) throws Exception {
        int requestId = requestPack.getRequestId();
        long now = System.currentTimeMillis() / 1000;

        IRequestHandler handler = ClientRequestMgr.Instance.createById(requestId);
        if(handler == null){
            System.out.println("找不到requestId对应的handler");
            return;
        }

        try{
            //  对协议进行处理，应该有一个返回数据
            handler.onProcess(ctx,requestPack,now);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "断开连接...");
        channelGroup.remove(channel);
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
                System.out.println(ctx.channel().remoteAddress() + " ----超时----");

                ctx.close();
                break;
        }

    }

    public static void main(String[] args) {
        long now = 1685083085;
        Random random = new Random(now);

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 10; j++) {
                System.out.print(random.nextInt(100) + " ");
            }
            System.out.println();
        }

    }
}
