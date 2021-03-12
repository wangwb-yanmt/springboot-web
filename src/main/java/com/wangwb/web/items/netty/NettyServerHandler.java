package com.wangwb.web.items.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 	netty服务端处理器（业务）
 * @author wangwb
 *
 */

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	
	Logger log = LoggerFactory.getLogger(NettyServerHandler.class);
	
	/**
	 *	客户端连接会触发
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("Channel active......");
	}
	
	/**
	 *	客户端发消息会触发
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("服务器收到消息啦："+msg.toString());
        ctx.write("你也好哦");
        ctx.flush();
	}
	
	/**
	 * 	发生异常触发
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//打印异常
		cause.printStackTrace();
        ctx.close();
	}

}
