package com.onyeka.chatApp;
import java.net.*;

public class chatServer {
    private ServerSocket ss;
    int portNum = 0;
    public chatServer(int portNum){
        try {
            this.portNum = portNum;
            ss = new ServerSocket(portNum);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void server(){
        while(!ss.isClosed()) {
            try{
                Socket s = ss.accept();
                System.out.println("[SERVER] New client connected!");
                new Thread(new ClientHandler(s)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new chatServer(800).server();
    }
}
