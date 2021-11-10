package com.zhanyingwl.www;

import java.util.Scanner;

public class InputTargetThread extends Thread {
    static String targetHost = Runner.targetHost;

    @Override
    public void run() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            targetHost = scanner.next();
            if (targetHost.equals("none")) {
                System.out.println("[-] 战鹰网络 - DDoS 控制端：已停止攻击！（若之前有攻击，则可能会有缓冲！）");
            } else {
                System.out.println("[+] 战鹰网络 - DDoS 控制端：您输入的目标IP - " + targetHost);
            }
        }
    }
}