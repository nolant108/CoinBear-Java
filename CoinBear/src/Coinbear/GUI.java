package Coinbear;

import java.awt.*;
import javax.swing.*;


public class GUI {

    public GUI(){


        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(500,500));
    
        frame.add(panel, BorderLayout.WEST);

        

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("CoinBear: Main");        
		frame.pack();
        frame.setVisible(true);

        panel.add(new JLabel("Current Hash: " + CoinBear.blockchain));
        panel.add(new JLabel("\n"));
        panel.add(new JLabel("Current Difficulty: " + CoinBear.difficulty));
        panel.add(new JLabel("\n"));
        panel.add(new JLabel("Current Wallet A Balance: " + CoinBear.walletA.getBalance()));
        panel.add(new JLabel("\n"));
        panel.add(new JLabel("Current Wallet B Balance: " + CoinBear.walletB.getBalance()));
        panel.add(new JLabel("\n"));
        panel.add(new JLabel("Vaid Blockchain:" + CoinBear.isChainValid()));
        
    
        
	}

}
