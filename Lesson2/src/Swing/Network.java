package Swing;


import HistoryUser.HistoryUser;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Network implements Closeable {

    private static final String AUTH_PATTERN = "/auth %s %s";
    private static final String RENAME_PATTERN = "/rename %s %s";
    private static final String MESSAGE_SEND_PATTERN = "/w %s %s";
    private static final String USER_LIST_PATTERN = "/userlist";
    private static final Pattern MESSAGE_PATTERN = Pattern.compile("^/w (\\w+) (.+)", Pattern.MULTILINE);

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private final MessageSender messageSender;
    private final Thread receiver;
    private String username;
    private String newUsername;
    private final String hostName;
    private final int port;
    private HistoryUser historyUser;

    public Network(String hostName, int port, MessageSender messageSender) {
        this.hostName = hostName;
        this.port = port;
        this.messageSender = messageSender;

        this.receiver = createReceiverThread();

    }

    private Thread createReceiverThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        String text = in.readUTF();

                        System.out.println("New message " + text);
                        Matcher matcher = MESSAGE_PATTERN.matcher(text);
                        if (matcher.matches()) {
                            Message msg = new Message(matcher.group(1), getUsername(),
                                      matcher.group(2));
                            messageSender.submitMessage(msg);
                            historyUser.writeHistory(text);
                        } else if (text.startsWith(USER_LIST_PATTERN)) {
                            // TODO обновить список подключенных пользователей
                            String [] arrMsg = text.split("\\s", 3);
                            String userAct = arrMsg[1];
                            String name = arrMsg[2];
                            if(userAct.equals("in")){
                                messageSender.addUser(name);
                            }else if (userAct.equals("out")) {
                                messageSender.removeUser(name);
                            }else{
                                System.out.println("Ошибка данных");
                            }
                        }else if (text.startsWith("/rename successful")){
                            username = newUsername;
                            System.out.println("response: "+text);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.printf("Network connection is closed for user %s%n", getUsername());
            }
        });
    }

    public void sendMessageToUser(Message message) throws IOException {
        sendMessage(String.format(MESSAGE_SEND_PATTERN, message.getUserTo(), message.getText()));
        historyUser.writeHistory(String.format(MESSAGE_SEND_PATTERN, getUsername(), message.getText()));
    }

    private void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void authorize(String username, String password) throws IOException {
        socket = new Socket(hostName, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        out.writeUTF(String.format(AUTH_PATTERN, username, password));
        String response = in.readUTF();
        if (response.equals("/auth successful")) {
            this.username = username;

            this.historyUser = new HistoryUser(username);
            historyUser.createFile();
            printHistory();
            receiver.start();
        } else {
            throw new AuthException();
        }
    }


    private void printHistory () {
        try {
            List<String> listHistory = historyUser.readHistory();
            if (!listHistory.isEmpty()){
                Collections.reverse(listHistory);
                for (String text: listHistory) {
                    System.out.println("text from file: "+text);
                    Matcher matcher = MESSAGE_PATTERN.matcher(text);
                    if (matcher.matches()) {
                        Message msg = new Message(matcher.group(1), getUsername(),
                                matcher.group(2));
                        messageSender.submitMessage(msg);
                    }
                }

                //receiver.start();
            }else{
                System.out.println("file is empty");
                //receiver.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void renameUser (String newName, String password) throws IOException{
        out.writeUTF(String.format(RENAME_PATTERN, newName, password));
        this.newUsername=newName;

    }

    public String getUsername() {
        return username;
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        receiver.interrupt();
        try {
            receiver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}