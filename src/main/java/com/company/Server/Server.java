package com.company.Server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

public class Server {
    private ServerSocket serverSocket;
    private boolean isStarted;
    private Users users = new Users();
    Hangars hangars = new Hangars();
    ObjectMapper objectMapper = new ObjectMapper();
    private int port = 3345;

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        isStarted = true;
    }

    public void accept() throws IOException {
        while (true) {
            if (isStarted) {
                Socket socket = serverSocket.accept();
                new MonoServer(socket).start();
            }
        }
    }

    public void sendToAllUsers(String message) throws IOException {
        System.out.println("В отправке");
        for (Map.Entry<Integer, Connection> user : users.getUsers().entrySet()) {
                user.getValue().send(message);
        }
    }

    class MonoServer extends Thread {
        private final Socket socket;
        private Connection connection;
        private boolean isConnected = false;
        int numberOfHangar;

        MonoServer(Socket socket) {
            this.socket = socket;
            isConnected = true;
        }

        private void sendRequest(Connection connection) throws IOException {
            while (isConnected) {
                String string = connection.receive();
                hangars.addOrUpdate(numberOfHangar, string);
                System.out.println(string);
                sendToAllUsers(getHangars());
            }
        }

        @Override
        public void run() {
            try {
                connection = new Connection(socket);
                System.out.println("В потоке");
                numberOfHangar = users.addNewUser(connection);
                sendRequest(connection);
            }
            catch (SocketException socketException){
                try {
                    int han = users.deleteUser(connection);
                    hangars.removeHangar(han);
                    isConnected = false;
                    System.out.println("Клиент отключился");
                    sendToAllUsers(getHangars());
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            catch (RuntimeException | IOException ex){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public Users getUsers() {
        return users;
    }

    public String getHangars() throws JsonProcessingException {
        return objectMapper.writeValueAsString(hangars);
    }
}
