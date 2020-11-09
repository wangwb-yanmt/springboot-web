package com.wangwb.web.common.component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

/**
 * WebSocket服务端组件
 */
@ServerEndpoint(value="/WebSocketServer/{userId}")
@Component
public class WebSocketServer {

	static Log log=LogFactory.getLog(WebSocketServer.class);
	
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	
	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
//	private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
	//新：使用map对象，便于根据userId来获取对应的WebSocket
    private static ConcurrentHashMap<String,WebSocketServer> webSocketList = new ConcurrentHashMap<>();
	
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    //接收userId
    private String userId = "";
    
    
    /**
     * @category 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
    	this.session = session;
//    	webSocketSet.add(this);
    	webSocketList.put(userId,this);
    	addOnlineCount();
    	log.info("有新窗口开始监听:"+userId+",当前在线人数为" + getOnlineCount());
    	this.userId=userId;
    	try {
       	  	sendMessage("连接成功（服务端）");
    	} catch (IOException e) {
    		log.error("websocket IO异常");
    	}
    	
    }
    
    /**
     * @category 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
    	if(webSocketList.get(this.userId)!=null){
    		webSocketList.remove(this.userId);
            //webSocketSet.remove(this);  //从set中删除
    		subOnlineCount();          //在线数减1
            log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }
    
    /**
     * @category 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	log.info("收到来自窗口"+userId+"的信息:"+message);
    	try {
			sendMessage("客户端发送的消息是"+message);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * @category 发生错误后调用的方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    
    /**
     * @category 服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    
    /**
     * @category 服务器主动推送
     */
    /*public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
    	log.info("推送消息到窗口"+userId+"，推送内容:"+message);
        for (WebSocketServer item : webSocketSet) {
            try {
            	//这里可以设定只推送给这个sid的，为null则全部推送
            	if(sid==null) {
            		item.sendMessage(message);
            	}else if(item.sid.equals(sid)){
            		item.sendMessage(message);
            	}
            } catch (IOException e) {
                continue;
            }
        }
    }*/
    
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    
    public static synchronized void addOnlineCount() {
    	WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
    	WebSocketServer.onlineCount--;
    }
	
}
