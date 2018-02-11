
package com.ch.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;


/**
 * Socket建立连接（握手）和断开
 * 
 * @author Goofy
 * @Date 2015年6月11日 下午2:23:09
 */
public class HandShake implements HandshakeInterceptor {
	
	/**
	 * 1.将ServerHttpRequest请求 转成 HttpSession 请求处理
	 * 2.将HttpSession session中的信息取后放入WebSocket Session中使用
	 */
	//握手前的动作，判断是否握手成功或者断开
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		System.out.println("Websocket:用户[ID:" + ((ServletServerHttpRequest) request).getServletRequest().getSession(false).getAttribute("userid") + "]已经建立连接");
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			// 标记用户
			Long userid = (Long) session.getAttribute("userid");
			if(userid!=null){
				//从HttpSession取数据放入WebSocketSession里面
				attributes.put("userid", userid);
				attributes.put("loginname", session.getAttribute("loginname"));
			}else{
				return false;
			}
		}
		return true;
	}

	//握手后的动作，
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
		
	}
}
