package com.czy.netty.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

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
        BufferedReader reader = null;
        PrintWriter pw = null;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);
            String body = null;
            while (true) {
                body = reader.readLine();
                if (body == null) break;
                System.out.println("接收到消息：" + body);
                pw.println(new Date());
            }
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
		    if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (pw != null)
                pw.close();
		    if (socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            socket = null;
        }
    }
}
