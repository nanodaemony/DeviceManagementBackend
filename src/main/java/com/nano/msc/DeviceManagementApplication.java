package com.nano.msc;

import com.nano.msc.serial.NettyServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.net.InetSocketAddress;

import lombok.extern.slf4j.Slf4j;

/**
 * 主函数
 * @author nano
 */
@SpringBootApplication
@CrossOrigin
@ComponentScan("com.nano.msc")
@Slf4j
@EnableAsync
public class DeviceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceManagementApplication.class, args);
		log.info("\n\nStart HTTP server successful...");
		// 启动Netty服务端
		NettyServer nettyServer = new NettyServer();
		nettyServer.start(new InetSocketAddress("0.0.0.0", 10087));

	}


}
