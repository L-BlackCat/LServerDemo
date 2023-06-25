package org.example.netty.group_chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.example.netty.group_chat.engine.entity.Session;
import org.example.netty.group_chat.logger.Debug;

import java.util.concurrent.TimeUnit;

public class IMIdleStateHandler extends IdleStateHandler {
    int readTimes;
    int readIdleTimeSeconds;
    int writerIdleTimeSeconds;
    int allIdleTimeSeconds;
    TimeUnit timeUnit;

    public IMIdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds, TimeUnit timeUnit) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds,timeUnit);
        this.readIdleTimeSeconds = readerIdleTimeSeconds;
        this.writerIdleTimeSeconds = writerIdleTimeSeconds;
        this.allIdleTimeSeconds = allIdleTimeSeconds;
        this.timeUnit = timeUnit;
    }
    public IMIdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        this(readerIdleTimeSeconds,writerIdleTimeSeconds,allIdleTimeSeconds, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        /**
         * 服务器一段时间内没有收到客户端数据，原因如下：
         * 1.连接假死
         *  a.现象
         *      在某一端（服务器或客户端）看来，底层的TCP连接已经断开了，但是应用程序没有捕捉到，因此认为这条连接还存在。
         *  b.原因
         *      1.应用程序出现堵塞，无法进行数据读写
         *      2.客户端和服务端网络相关设备出现故障
         *      3.公网丢包。
         * 2.非假死状态下确实没有发送数据
         *  解决方式：客户端进行定时向服务器进行心跳包发送，服务器进行心跳包响应
         *
         *  解决方式：
         *      心跳检测
         *      断线重连
         *          1.服务器单方面断开连接后，底层tcp连接已经断开了，但是客户端并不知道已经断开，仍然认为这条连接还存在
         *              解决方式：客户端也要需要进行心跳检测
         *
         */
        Channel channel = ctx.channel();
        boolean isKill = false;
        String eventType = "";
        switch (evt.state()){
            case READER_IDLE:
                eventType = "读空闲";
                readTimes++;
                if(readTimes > 3){
                    isKill = true;
                }
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                isKill = true;
                break;
            case ALL_IDLE:
                eventType = "读写空闲";
                isKill = true;
                break;
        }


        Session session = GlobalSessionMgr.Instance.getSession(channel);
        Debug.warn(channel.remoteAddress() + " - " + session + " 超时事件：" + eventType);
        if(isKill){
            ctx.close();
        }
    }

}
