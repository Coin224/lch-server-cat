package com.lch.server;

import com.lch.thread.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // 启动服务器
    public void startServer() {
        try {
            System.out.println("服务器启动。。。");
            ServerSocket server = new ServerSocket(9999);
            while (true) {
                // 等待某一个浏览器来连接
                Socket socket = server.accept();
                // 启动线程 让线程去做事
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
