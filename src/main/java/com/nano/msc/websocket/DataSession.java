package com.nano.msc.websocket;

import javax.websocket.Session;

import lombok.Data;

/**
 * 封装Websocket连接的客户端连接
 *
 * @author: nano
 * @time: 2020/6/3 19:52
 */
@Data
public class DataSession {

	/**
	 * 存放Session
	 */
	private Session session;

	/**
	 * 当前手术场次号
	 */
	private int operationNumber;

	/**
	 * 仪当前展示的仪器号
	 */
	private int currentDeviceCode;

	/**
	 * 浏览器ID
	 */
	private String browserId;

	/**
	 * Session的键
	 */
	private String sessionKey;


	public DataSession(Session session, int operationNumber, int currentDeviceCode, String browserId) {
		this.session = session;
		this.operationNumber = operationNumber;
		this.currentDeviceCode = currentDeviceCode;
		this.browserId = browserId;
		sessionKey = operationNumber + browserId;
	}
}
