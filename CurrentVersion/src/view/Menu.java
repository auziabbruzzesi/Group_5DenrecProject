package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JFrame {
	
	JButton newGame = new JButton();
	JButton saveGame = new JButton();
	JButton loadGame = new JButton();
	JButton quit = new JButton();
	buttonPanel b = new buttonPanel();
	

	public Menu(){

		setPreferredSize(new Dimension(300,300));
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - 300) / 2);
	    int y = (int) ((dimension.getHeight() - 300) / 2);
	    pack();
	    setLocation(x, y);
		add(b);
		
		setVisible(true);
		
	}
	class buttonPanel extends JPanel{
		public buttonPanel(){
			newGame.setText("New Game!");
			//newGame.setBounds(300,75, 0, 0);
			saveGame.setText("Save Game");
			loadGame.setText("LoadGame");
			quit.setText("quit");
			
			add(newGame);
			add(saveGame);
			add(loadGame);
			add(quit);
			
			this.setLayout(new GridLayout(4,1));
			
			this.setVisible(true);
		}
		@Override
		public void paintComponents(Graphics g){
			
		}
	}

}
