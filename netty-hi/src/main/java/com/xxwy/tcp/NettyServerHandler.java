package com.xxwy.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 1.自定义handler,需要继承netty,规定某个HandlerAdapter(规范)
 * @author wy
 * @date 2020/3/4 16:34
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

  /**
   * 读取client数据
   * @param ctx 上下文对象，含有管道pipLine
   * @param msg 就是客户端发送的数据 默认Object
   * @throws Exception
   */
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf byteBuf = (ByteBuf) msg;
    System.out.println("client message: " + byteBuf.toString(CharsetUtil.UTF_8));
    System.out.println("client address: " + ctx.channel().remoteAddress());
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    //write + flush
    //一般，对发送的数据进行编码
    ctx.writeAndFlush(Unpooled.copiedBuffer("hello client",CharsetUtil.UTF_8));
  }

  /**
   * 异常处理，关闭通道
   * @param ctx
   * @param cause
   * @throws Exception
   */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}
