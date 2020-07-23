package com.itcr.datos.cooktimeserver.GUI;

import com.itcr.datos.cooktimeserver.CookTimeServerApplication;
import com.itcr.datos.cooktimeserver.object.User;
import com.itcr.datos.cooktimeserver.restfull.*;
import org.springframework.boot.SpringApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasicGUI extends JFrame {
    public JLabel userLabel, chefLabel;
    public JTextField userTextField;
    public JTextArea guiTextArea;
    public JButton startServer;
    public boolean temp = false;


    BasicGUI(String[] args){
        this.setTitle("GUI server");
        this.setBounds(250,30,300,530);
        this.setPreferredSize(new Dimension(900,720));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        Container mainContainer = this.getContentPane();
        mainContainer.setBackground(Color.darkGray);
        mainContainer.setLayout(null);


        startServer = new JButton("Start Server");
        startServer.setBackground(Color.BLUE);
        startServer.setForeground(Color.black);
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CookTimeServerApplication.main(args);
                newWindow();
            }
        });


        startServer.setBounds(70,150,150,50);

        mainContainer.add(startServer);
        this.setVisible(true);

    }


    public void newWindow() {
        this.dispose();
        JFrame frame = new JFrame("Server GUI");
        frame.setBounds(250,30,800,530);
        frame.setPreferredSize(new Dimension(900,720));
        frame.setLocationRelativeTo(null);


        Container container = frame.getContentPane();
        container.setBackground(Color.darkGray);
        container.setLayout(null);

        guiTextArea = new JTextArea();
        guiTextArea.setBackground(Color.lightGray);
        guiTextArea.setFont(new Font("SANS_SERIF", Font.BOLD, 14));


        userLabel = new JLabel("Users: " );
        userLabel.setForeground(Color.black);
        userLabel.setFont(new Font("SANS_SERIF", Font.BOLD, 14));

        userTextField = new JTextField();

        chefLabel = new JLabel("Chefs: ");
        chefLabel.setForeground(Color.black);
        chefLabel.setFont(new Font("SANS_SERIF", Font.BOLD, 14));

        JButton verifyUserButton = new JButton("Verify");
        verifyUserButton.setBackground(Color.BLUE);
        verifyUserButton.setForeground(Color.black);
        verifyUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (TreeManagement.binarySearch(userTextField.getText()) != null){
                    String key = userTextField.getText();
                    System.out.println(key);
                    userTextField.setText("");
                    TreeManagement.BinarySearchChefs(key).getData().setVerify(true);
                    ChefTree.saveChef();
                    guiTextArea.append("\n" + UserTree.getUserTree().toString());
                }
                else{
                    guiTextArea.append("User not found");
                    userTextField.setText("");
                }
            }
        });

        JButton deleteUserButton = new JButton("Delete");
        deleteUserButton.setBackground(Color.BLUE);
        deleteUserButton.setForeground(Color.black);
        deleteUserButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TreeManagement.binarySearch(userTextField.getText()) != null){
                String key = userTextField.getText();
                System.out.println(key);
                userTextField.setText("");
                UserTree.getUserTree().delete(key);
                UserTree.saveUser();
                guiTextArea.append("\n" + UserTree.getUserTree().toString());
            }
            else{
                guiTextArea.append("User not found");
                userTextField.setText("");
            }

            }
        });

        JButton printUsers = new JButton("Get Users");
        printUsers.setBackground(Color.BLUE);
        printUsers.setForeground(Color.black);
        printUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiTextArea.append("\n" + UserTree.getUserTree().toString());
            }
        });

        JButton closeServerButton = new JButton("Close Server");
        closeServerButton.setBackground(Color.BLUE);
        closeServerButton.setForeground(Color.black);


        guiTextArea.setBounds(100, 40,550,300);
        userLabel.setBounds(65, 360, 100, 30);
        userTextField.setBounds(120, 360, 330, 30);
        verifyUserButton.setBounds(120, 400, 110, 20);
        deleteUserButton.setBounds(235, 400, 80, 20);
        printUsers.setBounds(320,400,130,20);
        closeServerButton.setBounds(750, 600, 100, 30);

        container.add(closeServerButton);
        container.add(printUsers);
        container.add(deleteUserButton);
        container.add(verifyUserButton);
        container.add(closeServerButton);
        container.add(userLabel);
        container.add(userTextField);
        container.add(guiTextArea);


        frame.setVisible(true);
        JOptionPane.showMessageDialog(null,"Admin Settings");
    }

    public static void main(String[] args) {
        new BasicGUI(args);

    }
 }


