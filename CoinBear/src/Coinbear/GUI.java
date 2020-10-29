package Coinbear;

import java.awt.*;
import javax.swing.*;


public class GUI {

    public GUI() throws InterruptedException {

        int i = 1;


        if(i == 10){
        JFrame frame = new JFrame();
       JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
        
        JPanel MainPanel = new JPanel(new GridLayout(3, 2));

        frame.add(MainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("CoinBear");        
		frame.pack();
        frame.setVisible(true);
        
        MainPanel.add(new JLabel("Current Difficulty: " + CoinBear.difficulty));
        MainPanel.add(new JLabel("Current Hash: " + CoinBear.blockchain));
        MainPanel.add(new JLabel("Current Wallet A Balance: " + CoinBear.walletA));
        MainPanel.add(new JLabel("Current Wallet B Balance: " + CoinBear.walletB));
        
        i = i + 1;

        Thread.sleep(1000);
        }
	}

}
