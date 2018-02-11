package com.ch.websocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.ch.service.SocketBean;
import com.ch.sysuntils.Message;

import net.sf.json.JSONObject;

/**
 * 建立连接之后--Socket处理器处理事务
 * 
 * @author Goofy
 * @Date 2015年6月11日 下午1:19:50
 */
@Component
public class MyWebSocketHandler implements WebSocketHandler {
	@Autowired
	SocketBean socketBean;
	
	//userSocketSessionMap用来存放websocket信息
	public static final Map<Long, WebSocketSession> userSocketSessionMap;

	static {
		userSocketSessionMap = new HashMap<Long, WebSocketSession>();
	}

	/**
	 * 建立连接后
	 */
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Long userid = (Long) session.getAttributes().get("userid");
//		System.out.println(session);
		if (userSocketSessionMap.get(userid) == null) {
			userSocketSessionMap.put(userid, session);
			
			//调用用户上下线通知方法，0下线，1上线
			socketBean.getusers(Integer.parseInt(userid.toString()),1);
		}
	}

	/**
	 * 消息传输错误处理
	 */
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		Iterator<Entry<Long, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除Socket会话
		while (it.hasNext()) {
			Entry<Long, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				
				userSocketSessionMap.remove(entry.getKey());
				
				//调用用户上下线通知方法，0下线，1上线
				socketBean.getusers(Integer.parseInt(entry.getKey().toString()),0);
				
				System.out.println("消息传输错误-Socket会话已经移除:用户ID" + entry.getKey());
				break;
			}
		}
	}

	/**
	 * 关闭连接后
	 */
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		System.out.println("Websocket-session:" + session.getId() + "已经关闭");
		Iterator<Entry<Long, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除Socket会话
		while (it.hasNext()) {
			Entry<Long, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				
				userSocketSessionMap.remove(entry.getKey());
				
				//调用用户上下线通知方法，0下线，1上线
				socketBean.getusers(Integer.parseInt(entry.getKey().toString()),0);
				
				System.out.println("关闭连接-Socket会话已经移除:用户ID" + entry.getKey());
				break;
			}
		}
	}

	public boolean supportsPartialMessages() {
		return false;
	}

	
	/**
	 * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理(也可以不用这个方法发送消息)
	 * data["uid"]=from;
		data["fromName"]=fromName;
		data["touid"]=touid;
		data["text"]=msg;
		websocket.send(JSON.stringify(data));
	 */
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//		if (message.getPayloadLength() == 0)
//			return;
//		Message msg = (Message) JSONObject.toBean(JSONObject.fromObject(message.getPayload().toString()), Message.class);
//		msg.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//		
//		//调用发送消息方法
//		sendMessageToUser(msg.getTouid(),
//				new TextMessage(JSONObject.fromObject(msg).toString()));
	}

	
	//----------------自己写功能方法------------------
	
	/**
	 * 给所有在线用户发送消息
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sys_message(final TextMessage message) throws IOException {
		Iterator<Entry<Long, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 群发
		while (it.hasNext()) {
			//取出SESSION对象
			final Entry<Long, WebSocketSession> entry = it.next();
			
			//如果发送者和接收者相同，那么不发送通知给自己
//			Message msg=(Message)JSONObject.toBean(JSONObject.fromObject(message.getPayload()), Message.class);
//			if(msg.getUid().equals(entry.getKey())){
//				continue;
//			}
			
			//entry.getValue()可视为session
			if (entry.getValue().isOpen()) {
				//单线程群发
				// entry.getValue().sendMessage(message);
				//多线程群发
				new Thread(new Runnable() {
					public void run() {
						try {
							entry.getValue().sendMessage(message);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}).start();
			}

		}
	}

	/**
	 * 给某个用户发送消息
	 * 
	 * @param userName
	 * @param message
	 * @throws IOException
	 */
	public void sendMessageToUser(Long touid, TextMessage message) throws IOException {
		WebSocketSession session = userSocketSessionMap.get(touid);
		if (session != null && session.isOpen()) {
			session.sendMessage(message);
		}
	}

}
