package com.nano.msc.websocket;

import javax.websocket.Session;

import lombok.Data;

/**
 * 封装WebSocket连接的客户端连接
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
	private int collectionNumber;

	/**
	 * 仪当前展示的仪器号
	 */
	private int deviceCode;

	/**
	 * 浏览器ID
	 */
	private String browserId;

	/**
	 * Session的键
	 */
	private String sessionKey;


	public DataSession(Session session, int collectionNumber, int deviceCode, String browserId) {
		this.session = session;
		this.collectionNumber = collectionNumber;
		this.deviceCode = deviceCode;
		this.browserId = browserId;
		sessionKey = collectionNumber + browserId;
	}
}
