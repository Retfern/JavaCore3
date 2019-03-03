import auth.AuthService;
import auth.AuthServiceImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClientHandler {

    private static final Pattern MESSAGE_PATTERN = Pattern.compile("^/w (\\w+) (.+)", Pattern.MULTILINE);
    private static final String MESSAGE_SEND_PATTERN = "/w %s %s";
    private static final String USER_LIST_PATTERN = "/userlist %s %s";
    private static final Pattern RENAME_PATTERN = Pattern.compile("^/rename (\\w+) (\\w+)$");


    private final Thread handleThread;
    private final DataInputStream inp;
    private final DataOutputStream out;
    private final ChatServer server;
    private String username;
    private final Socket socket;
    private AuthService authService = new AuthServiceImpl();

    public ClientHandler(String username, Socket socket, ChatServer server) throws IOException {
        this.username = username;
        this.socket = socket;
        this.server = server;
        this.inp = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        this.handleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        String msg = inp.readUTF();
                        System.out.printf("Message from user %s: %s%n", username, msg);

                        Matcher matcher = MESSAGE_PATTERN.matcher(msg);
                        Matcher matchRename = RENAME_PATTERN.matcher(msg);
                        if (matcher.matches()) {
                            String userTo = matcher.group(1);
                            String message = matcher.group(2);
                            server.sendMessage(userTo, username, message);
                        }else if (matchRename.matches()) {
                            String newName = matchRename.group(1);
                            String password = matchRename.group(2);
                            if (authService.renameUser(username, newName, password)) {
                                server.renameUser(username, newName);
                                out.writeUTF("/rename successful "+newName);
                                out.flush();

                            }

                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    System.out.printf("Client %s disconnected%n", username);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        server.unsubscribeClient(ClientHandler.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        handleThread.start();
    }

    public void sendMessage(String userTo, String msg) throws IOException {
        out.writeUTF(String.format(MESSAGE_SEND_PATTERN, userTo, msg));
        out.flush();
    }
    public void sendUserList( String msg, String userFrom) throws IOException {
        out.writeUTF(String.format(USER_LIST_PATTERN, msg, userFrom));
        out.flush();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String name){
        this.username=name;
    }
}