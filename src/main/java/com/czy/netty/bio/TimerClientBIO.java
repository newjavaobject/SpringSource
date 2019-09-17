package com.czy.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Administrator on 2019/9/11 0011.
 */
public class TimerClientBIO {
    public static void main(String[] args) {
        BufferedReader reader = null;
        PrintWriter pw = null;
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("哈哈哈哈！");

            System.out.println(reader.readLine());

            socket.close();
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
        }
    }
}
