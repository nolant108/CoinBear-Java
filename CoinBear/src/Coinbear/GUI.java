package Coinbear;

import java.awt.*;
import java.security.PublicKey;

import javax.swing.*;


public class GUI {



	public GUI() {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(500, 350));
        panel.setLayout(new GridLayout(0, 1));
        panel.setLocation(1000, 1000);
        panel.setSize(500, 350);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("CoinBear: Main");

        
        JTextField ownAddressPublic = new JTextField();
        JTextField ownAddressPrivate = new JTextField();
        JTextField recAddress = new JTextField();
        JTextField amountCBR = new JTextField();
        JButton sendButton = new JButton("Send Coinbear ->");


        panel.add(new JLabel("Current Hash: " + CoinBear.blockchain));
        panel.add(new JLabel("Current Difficulty: " + CoinBear.difficulty));
        panel.add(new JLabel("Vaid Blockchain: " + CoinBear.isChainValid()));
        panel.add(new JLabel("Enter your Public Address: "));
        panel.add(ownAddressPublic);
        panel.add(new JLabel("Enter your Private Address to sign the transaction: "));
        panel.add(ownAddressPrivate);
        panel.add(new JLabel("Enter Reciving Address: "));
        panel.add(recAddress);
        panel.add(new JLabel("Enter Amount of CBR(Up to 18 decimals): "));
        panel.add(amountCBR);
        panel.add(sendButton);

        frame.add(panel, BorderLayout.WEST);
        frame.pack();
        frame.setVisible(true);


        sendButton.addActionListener(e -> {
            frame.dispose();
         });

    }

	


    



}

