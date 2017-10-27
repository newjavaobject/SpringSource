package com.czy.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * SpringSource:com.czy.netty.bio.TimerServerBIO.java
 * @auth : chenzhiyu
 * @since : 2017年10月27日 上午8:30:18
 * 说明：BIO模式的server
 */
public class TimerServerBIO {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8080);
			Socket socket = null;
			while(true) {
				socket = serverSocket.accept(); 
				new Thread(new TimerServerHandler(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(serverSocket != null) {
				try {
					serverSocket.close();
					serverSocket = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class TimerServerHandler implements Runnable {
	Socket socket = null;
	public TimerServerHandler(Socket socket) {
		this.socket = socket;
	}
	public void run() {
		try {
			socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
