package org.example.netty.group_chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.netty.group_chat.bean.RequestPacket;
import org.example.netty.group_chat.engine.utils.KDateUtil;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.engine.ClientProtocolID;
import org.example.netty.group_chat.engine.ClientProtocolMgr;
import org.example.netty.group_chat.engine.IResponseHandler;
import org.example.netty.group_chat.logger.Debug;

import java.util.concurrent.TimeUnit;

public class IMClientHandler extends SimpleChannelInboundHandler<Packet> {

    LNettyClient nettyClient;

    public IMClientHandler(LNettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //  心跳
        scheduleSendHeartBeat(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //  断线重连
        Debug.err("运行中断开重连...");
        nettyClient.connect();
    }

    public void scheduleSendHeartBeat(ChannelHandlerContext ctx){
        ctx.executor().schedule(() -> {
            if(ctx.channel().isActive()){
                RequestPacket requestPacket = new RequestPacket();
                requestPacket.setRequestId(ClientProtocolID.Tick_Request.getId());
                ctx.writeAndFlush(requestPacket);
                scheduleSendHeartBeat(ctx);
            }
        },5, TimeUnit.SECONDS);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        int protocolId = packet.getRequestId();
        long now = KDateUtil.Instance.now();

        ClientProtocolID clientProtocolID = ClientProtocolID.toEnum(protocolId);
        if(clientProtocolID == null){
            Debug.err("找不到对应协议id，id:" + protocolId);
            return;
        }

        IResponseHandler handler = ClientProtocolMgr.Instance.createResponseById(protocolId);
        if(handler == null){
            Debug.err("找不到protocolId对应的handler,protocolId: " + protocolId);
            return;
        }
        try {
            handler.onProcess(ctx,packet, now, LNettyClient.mainFrame);
        }catch (Exception e){
            Debug.err("响应出错,protocolId:" + protocolId,e);
        }



    }
}
