package com.nano.msc.serial;

import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nano
 * <p>
 * 串口仪器数据采集服务器
 **/
@Slf4j
@Component
public class SerialDeviceDataCollectionServer {

    public void start(InetSocketAddress socketAddress) {
        // new一个主线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // new一个工作线程组
        EventLoopGroup workGroup = new NioEventLoopGroup(10);
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SerialDeviceDataCollectionServerInitializer())
                .localAddress(socketAddress)
                // 设置队列大小
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(2048 * 1024))
                // 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        // 绑定端口,开始接收进来的连接
        try {
            ChannelFuture future = bootstrap.bind(socketAddress).sync();
            log.info("服务器启动开始监听端口: {}", socketAddress.getPort());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭主线程组
            bossGroup.shutdownGracefully();
            // 关闭工作线程组
            workGroup.shutdownGracefully();
        }
    }
}
