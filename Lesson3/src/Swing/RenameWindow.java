package Swing;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RenameWindow extends JDialog {

    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnApply;
    private JButton btnCancel;

    private final Network network;

    private boolean connected;

    public RenameWindow(Frame parent, Network network) {
        super(parent, "Rename", true);

        this.network = network;
        this.connected = false;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        lbUsername = new JLabel("New name: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);

        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);

        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);

        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        btnApply = new JButton("Apply");
        btnCancel = new JButton("Cancel");

        JPanel bp = new JPanel();
        bp.add(btnApply);
        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    network.renameUser(tfUsername.getText(), String.valueOf(pfPassword.getPassword()));
                    connected = true;
                } catch (AuthException ex) {
                    JOptionPane.showMessageDialog(RenameWindow.this,
                              "Не верно указан пароль или имя уже существует",
                              "Ошибка",
                              JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(RenameWindow.this,
                              "Ошибка сети",
                              "Ошибка",
                              JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dispose();
            }
        });

        bp.add(btnCancel);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public boolean isConnected() {
        return connected;
    }

}
