package com.nano.msc.serial;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author nano
 *
 * netty服务初始化器
 **/
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 添加编解码
        // socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(2048, Unpooled.wrappedBuffer("@&".getBytes(StandardCharsets.UTF_8))));
        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(2048));
        socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        socketChannel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        socketChannel.pipeline().addLast(new SerialServerHandler());
    }
}
