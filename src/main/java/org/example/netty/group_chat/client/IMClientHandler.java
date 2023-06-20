package org.example.netty.group_chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.netty.group_chat.engine.utils.KDateUtil;
import org.example.netty.group_chat.bean.Packet;
import org.example.netty.group_chat.engine.ClientProtocolID;
import org.example.netty.group_chat.engine.ClientProtocolMgr;
import org.example.netty.group_chat.engine.IResponseHandler;
import org.example.netty.group_chat.logger.Debug;

public class IMClientHandler extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        int protocolId = packet.getRequestId();
        long now = KDateUtil.Instance.now();

        ClientProtocolID clientProtocolID = ClientProtocolID.toEnum(protocolId);
        if(clientProtocolID == null){
            Debug.err("找不到对应协议id，id:" + protocolId);
            return;
        }
//        if(clientProtocolID.getProtocolType() == ClientProtocolID.REQUEST_TYPE){
//            IRequestHandler handler = ClientProtocolMgr.Instance.createRequestById(protocolId);
//            if(handler == null){
//                Debug.err("找不到protocolId对应的handler,protocolId: " + protocolId);
//            }
//            try {
//                Packet responsePack = handler.onProcess(ctx, packet, now);
//                //  LFH 暂时不进行回复
//            }catch (Exception e){
//                Debug.err("响应出错,protocolId:" + protocolId,e);
//            }
//        }
//        else {
//            IResponseHandler handler = ClientProtocolMgr.Instance.createResponseById(protocolId);
//            if(handler == null){
//                Debug.err("找不到protocolId对应的handler,protocolId: " + protocolId);
//                return;
//            }
//            try {
//                handler.onProcess(ctx,packet, now,GroupChatClient.mainFrame);
//            }catch (Exception e){
//                Debug.err("响应出错,protocolId:" + protocolId,e);
//            }
//        }

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
