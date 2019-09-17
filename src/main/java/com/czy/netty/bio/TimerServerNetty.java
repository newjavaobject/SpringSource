package com.czy.netty.bio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Date;

/**
 * Created by zychen on 2019/9/16 0016.
 */
public class TimerServerNetty {
    public static void main(String[] args) {
        try {
            new TimerServerNetty().bind(80);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bind(int port) throws Exception{
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>(){
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //以$为分隔符
                        ByteBuf delimiter = Unpooled.copiedBuffer("$".getBytes());
                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                        /*socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));*/
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new TimerServerNettyHandler());
                    }
                });
        ChannelFuture sync = bootstrap.bind(port).sync();//绑定端口，同步等待成功
        sync.channel().closeFuture().sync();//等待服务端监听端口关闭
    }
}

class TimerServerNettyHandler extends ChannelHandlerAdapter {
    private int count = 0;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        String body = new String(bytes, "UTF-8").substring(0, bytes.length-System.getProperty("line.separator").length());*/
        String body = (String) msg;
        System.out.println(body + "，服务端次数：" + ++count);

        ByteBuf resp = Unpooled.copiedBuffer((String.valueOf(new Date().getTime())+System.getProperty("line.separator")).getBytes());
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
