package org.example.netty.group_chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.example.netty.group_chat.logger.Debug;

import java.util.concurrent.TimeUnit;

public class IMIdleStateHandler extends IdleStateHandler {
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

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        Debug.warn("心跳检测到连接假死");
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
