import auth.AuthService;
import auth.AuthServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatServer {

    private static final Pattern AUTH_PATTERN = Pattern.compile("^/auth (\\w+) (\\w+)$");
    //private static final Pattern RENAME_PATTERN = Pattern.compile("^/rename (\\w+) (\\w+) (\\w+)$");
    private boolean authorizeUser =false;
    private AuthService authService = new AuthServiceImpl();

    private Map<String, ClientHandler> clientHandlerMap = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start(7977);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started!");


            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream inp = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("New client connected!");

                TimerTask task = new TimerTask() {
                    public void run() {
                        if(!authorizeUser){

                            try {
                                System.out.println("Client disconnected%n");
                                socket.close();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 120000);

                try {
                    String authMessage = inp.readUTF();
                    Matcher matcher = AUTH_PATTERN.matcher(authMessage);
                    //Matcher matchRename = RENAME_PATTERN.matcher(authMessage);
                    if (matcher.matches()) {
                        String username = matcher.group(1);
                        String password = matcher.group(2);
                        if (authService.authUser(username, password)) {
                            authorizeUser=true;
                            clientHandlerMap.put(username, new ClientHandler(username, socket, this));
                            out.writeUTF("/auth successful");
                            out.flush();
                            broadcastUserConnected(username);

                            System.out.printf("Authorization for user %s successful%n", username);
                        } else {
                            System.out.printf("Authorization for user %s failed%n", username);
                            out.writeUTF("/auth fails");
                            out.flush();
                            socket.close();
                        }
                    } else {
                        System.out.printf("Incorrect authorization message %s%n", authMessage);
                        out.writeUTF("/auth fails");
                        out.flush();
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String userTo, String userFrom, String msg) throws IOException {
        ClientHandler userToClientHandler = clientHandlerMap.get(userTo);
        if (userToClientHandler != null) {
            userToClientHandler.sendMessage(userFrom, msg);
        } else {
            System.out.printf("User %s not found. Message from %s is lost.%n", userTo, userFrom);
        }
    }

    public List<String> getUserList() {

        return new ArrayList<>(clientHandlerMap.keySet());
    }

    public void unsubscribeClient(ClientHandler clientHandler) throws IOException {
        clientHandlerMap.remove(clientHandler.getUsername());
        broadcastUserDisconnected(clientHandler.getUsername());
    }

    public  void broadcastUserConnected(String username) throws IOException {
        // TODO сообщать о том, что конкретный пользователь подключился
        for (String s: getUserList()) {

            clientHandlerMap.get(s).sendUserList("in", username);

            if (!username.equals(s)){
               clientHandlerMap.get(username).sendUserList("in", s);
            }

        }
    }

    public  void renameUserInList (String username)throws IOException {
        for (String s: getUserList()) {
            clientHandlerMap.get(s).sendUserList("in", username);
        }
    }


    public  void broadcastUserDisconnected(String username) throws IOException {
        // TODO сообщать о том, что конкретный пользователь отключился
        for (String s: getUserList()) {
            System.out.println("Удалён "+username);
            clientHandlerMap.get(s).sendUserList("out", username);

            /*if (!username.equals(s)){
                System.out.println("Удалён "+username);
                clientHandlerMap.get(s).sendUserList("out", username);
            }*/
        }
    }


    public  void renameUser (String username, String newName) throws IOException {
        ClientHandler userClientHandler = clientHandlerMap.get(username);
        userClientHandler.setUsername(newName);
        System.out.println("В ClientHandler имя "+userClientHandler.getUsername());
        broadcastUserDisconnected(username);
        clientHandlerMap.put(newName, userClientHandler);

        for (String s: getUserList()) {
            System.out.println("В мапе "+s);
        }

        clientHandlerMap.remove(username);

        renameUserInList(newName);

        for (String s: getUserList()) {
            System.out.println("В мапе "+s);
        }
    }



}
