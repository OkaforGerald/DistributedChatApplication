package com.onyeka.chatApp;
import java.io.*;
import java.net.*;
import java.util.*;
public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clients = new ArrayList<>();
    private BufferedReader reader;
    private BufferedWriter writer;
    private Socket sock;
    String userName = null;
    public ClientHandler(Socket s){
        try {
            sock = s;
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            userName = reader.readLine();
            clients.add(this);
            broadcastMessage("[SERVER] "+userName+" has joined the chat!");
        } catch(IOException e){
            removeClient();
            e.printStackTrace();
        }
    }
    public void MakeConnection(){
        try{
            String message;
        while(sock.isConnected()){
                while((message = reader.readLine()) != null) {
                    broadcastMessage(message);
                }
        }
        }catch(Exception e){
            closeEverything();
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String Message) {
        try {
            for (ClientHandler cl : clients) {
                if (!cl.userName.equals(userName)) {
                    cl.writer.write(Message);
                    cl.writer.newLine();
                    cl.writer.flush();
                }
            }
        }catch (IOException e){
            closeEverything();
            e.printStackTrace();
        }
    }

    public void removeClient(){
        clients.remove(this);
        broadcastMessage("[SERVER] "+userName+" has left the chat!");
    }

    public void closeEverything(){
        removeClient();
        try {
            sock.close();
            reader.close();
            writer.close();
        }catch (Exception e){
            closeEverything();
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        MakeConnection();
    }
}
