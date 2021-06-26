package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame implements ActionListener {

    JPanel p1;
    JTextField chatField;
    JButton sendButton;

    static JTextArea chatArea;

    static ServerSocket serverSocket;
    static Socket socket;

    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;

    Server() {

        FrameBasic(); //Setting Frame Basic
        TitleBarOfWhatsapp();
        addingItemsToTitleBar();
//        getContentPane().setBackground(Color.WHITE); //Change the colour of the entire pane or frmae
        addingChatAreaAndFieldAndSendButton();

    }

    private void FrameBasic() {
        setSize(350, 550);
        setLocation(300, 150);
        setTitle("Whatsapp");
        setIconImage(new ImageIcon("src/icons/whatsapp.jpg").getImage());
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); //The minimize maximaize and close window will be removed
    }

    private void TitleBarOfWhatsapp() {
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 350, 60);
        add(p1);
    }

    private void addingItemsToTitleBar() {

        ImageIcon backIcon = new ImageIcon("src/icons/3.png");
        Image backIconResize = backIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon backIconResizeAndConvertedImageIcon = new ImageIcon(backIconResize);
        JLabel backIconLabel = new JLabel(backIconResizeAndConvertedImageIcon);
        backIconLabel.setBounds(5, 10, 35, 35);
        p1.add(backIconLabel);
        backIconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon dpIcon = new ImageIcon("src/icons/ameen.png");
        Image dpIconResize = dpIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon dpIconResizeAndConvertedImageIcon = new ImageIcon(dpIconResize);
        JLabel dpIconLabel = new JLabel(dpIconResizeAndConvertedImageIcon);
        dpIconLabel.setBounds(40, 2, 60, 50);
        p1.add(dpIconLabel);

        JLabel displayName = new JLabel("Mohamed Ameen");
        displayName.setFont(new Font("SAN_SERIF", Font.BOLD, 15));
        displayName.setForeground(Color.WHITE);
        displayName.setBounds(110, 13, 150, 16);
        p1.add(displayName);

        JLabel currentStatus = new JLabel("Active Now");
        currentStatus.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        currentStatus.setForeground(Color.WHITE);
        currentStatus.setBounds(110, 32, 100, 16);
        p1.add(currentStatus);


        ImageIcon optionsIcon = new ImageIcon("src/icons/3icon.png");
        Image optionsIconResize = optionsIcon.getImage().getScaledInstance(10, 22, Image.SCALE_DEFAULT);
        ImageIcon optionsIconResizeAndConvertedImageIcon = new ImageIcon(optionsIconResize);
        JLabel optionsIconLabel = new JLabel(optionsIconResizeAndConvertedImageIcon);
        optionsIconLabel.setBounds(330, 12, 18, 30);
        p1.add(optionsIconLabel);

    }

    private void addingChatAreaAndFieldAndSendButton() {
        chatField = new JTextField();
        chatField.setBounds(5, 515, 265, 30);
        chatField.setFont(new Font("any name no effect", Font.PLAIN, 15));
        add(chatField);

        sendButton = new JButton("Send");
        sendButton.setBounds(275, 515, 70, 30);
        sendButton.setBackground(new Color(7, 94, 84));
        sendButton.setForeground(Color.WHITE);
        add(sendButton);

        chatArea = new JTextArea();
        chatArea.setBounds(5, 65, 340, 440);
        chatArea.setFont(new Font("", Font.PLAIN, 15));
//        chatArea.setBackground(Color.BLACK);  //For dark Mode
        chatArea.setEditable(false); //make non editable
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        add(chatArea);
        sendButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = chatField.getText();
        chatField.setText("");
//        chatArea.setForeground(Color.WHITE); //For dark mode
        chatArea.append("\t" + message + "\n");
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().setVisible(true);

        String messageInput = "";

        try {
            serverSocket = new ServerSocket(6001);
            socket = serverSocket.accept();

            dataInputStream = new DataInputStream(socket.getInputStream());  //from the client
            dataOutputStream = new DataOutputStream(socket.getOutputStream());  //to the client

            while (true) {
                messageInput = dataInputStream.readUTF();
                chatArea.setText(chatArea.getText() + "\n" + messageInput);
            }

//            serverSocket.close();
//            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
