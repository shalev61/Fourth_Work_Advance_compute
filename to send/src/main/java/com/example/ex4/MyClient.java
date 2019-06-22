package com.example.ex4;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MyClient {
    private Socket socket = null;
    private PrintWriter writer = null;

    public void connect(String ip, String port) {
        try {
            InetAddress serverAddr = InetAddress.getByName(ip);
            int serverPort = Integer.parseInt(port);

            //socket = new Socket(serverAddr, serverPort);
            socket = new Socket("10.0.2.2", 5402);
            System.out.println("Connected!");
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(final String msg) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (writer != null) {
                    writer.print(msg);
                    writer.flush();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        System.out.println(msg);
    }

    public void stop() {

        try {
            socket.close();
        } catch (Exception e) {

        }

    }
}