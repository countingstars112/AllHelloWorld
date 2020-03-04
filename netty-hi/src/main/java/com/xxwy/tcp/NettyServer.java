package com.xxwy.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wy
 * @date 2020/3/4 16:18
 */
public class NettyServer {

  public static void main(String[] args) throws InterruptedException {
    //1.创建Boss NioEventLoopGroup , Worker NioEventLoopGroup
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    //2.创建服务器端的启动对象，配置参数
    ServerBootstrap bootstrap = new ServerBootstrap();
    bootstrap.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        //线程队列得到连接个数
        .option(ChannelOption.SO_BACKLOG, 128)
        //设置保持活动链接状态
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        //给workGroup的EventLoop对应的管道设置handler
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new NettyServerHandler());
          }
        });

    //3.绑定端口并且同步去
    ChannelFuture cf = bootstrap.bind(6666).sync();

    //4.对关闭通道进行监听
    cf.channel().closeFuture().sync();
  }
}
