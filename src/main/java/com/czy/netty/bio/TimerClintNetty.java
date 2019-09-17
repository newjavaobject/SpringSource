package com.czy.netty.bio;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by Administrator on 2019/9/16 0016.
 */
public class TimerClintNetty {

    public static void main(String[] args) {
        try {
            new TimerClintNetty().connect("127.0.0.1", 80);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void connect(String host, int port) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>(){
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new TimerClientNettyHandler());
                    }
                });
        ChannelFuture sync = bootstrap.connect(host, port).sync();//绑定端口，同步等待成功
        sync.channel().closeFuture().sync();//等待服务端监听端口关闭
    }
}

class TimerClientNettyHandler extends ChannelHandlerAdapter {
    private int count = 0;
    byte[] by = null;
    public TimerClientNettyHandler(){
        by = ("query time $order" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = null;
        for (int i=0; i < 10; i++) {
            byteBuf = Unpooled.buffer(by.length);
            byteBuf.writeBytes(by);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        String body = new String(bytes, "UTF-8");*/
        String body = (String) msg;
        System.out.println(body + "，数量：" + ++count);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}