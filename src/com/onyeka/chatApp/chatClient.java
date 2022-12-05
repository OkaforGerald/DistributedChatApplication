package com.onyeka.chatApp;
import java.io.*;
import java.net.*;
import java.util.*;
public class chatClient {
    private String userName;
    private BufferedReader reader;
    BufferedWriter writer;
    Socket socket;
    public chatClient(String userName){
        try {
            socket = new Socket("127.0.0.1",800);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.userName = userName;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(){
        try {
            writer.write(userName);
            writer.newLine();
            writer.flush();
            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected()){
                String message = scanner.nextLine();
                if(message.toLowerCase(Locale.ROOT).matches("(.*)fuck(.*)") || message.toLowerCase(Locale.ROOT).matches("(.*)bitch(.*)")
                || message.toLowerCase(Locale.ROOT).matches("(.*)shit(.*)") || message.toLowerCase(Locale.ROOT).matches("(.*)gay(.*)")
                || message.toLowerCase(Locale.ROOT).matches("(.*)nigga(.*)")){
                    writer.write("[" + userName.toUpperCase(Locale.ROOT) + "]: " + message.replaceAll("[A-Za-z]", "*"));
                    writer.newLine();
                    writer.flush();
                }else{
                writer.write("[" + userName.toUpperCase(Locale.ROOT) + "]: " + message);
                writer.newLine();
                writer.flush();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void ListenForMessage(){
        new Thread(()->{
            try {
                String message;
                while(socket.isConnected()){
                    while((message = reader.readLine())!= null){
                        System.out.println(message);
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        System.out.print("Input your username: ");
        Scanner sc = new Scanner(System.in);
        String userName = sc.nextLine();
        chatClient cl = new chatClient(userName);
        cl.ListenForMessage();
        cl.sendMessage();
    }
}
