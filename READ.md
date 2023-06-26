数据序列化与反序列化

编码和解码
    场景：客户端和服务器端的数据交互，需要对网络中数据进行编码（用于在网络层传输）和解码（用来应用层解析数据）

封装的数据结构（客户端和服务器进行数据交互格式）

#   Netty

##  Netty更改事件传播源
### ctx.writeAndFlush()
假设管道链（pipeline）是A->B->C->D->E
当前节点位于C,调用了 ctx.writeAndFlush（）,会从pipeline链中的C节点开始往前寻，直到找到第一个outBound类型的handler

### ctx.channel().writeAndFlush()
当前节点位于C,调用了 ctx.channel().writeAndFlush（）,会从pipeline链中的E节点开始往前寻，直到找到第一个outBound类型的handler



#   小测
    重写类的clone方法，让类继承Cloneable

    ChannelFactory接口和ReflectiveChannelFactory实现类的使用，通过反射创建factory