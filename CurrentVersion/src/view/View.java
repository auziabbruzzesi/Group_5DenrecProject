//make set-up func in view, then call it in controller

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.synth.SynthSeparatorUI;

import controller.status;
import model.*;
import model.GameObject;
import view.View.HealthPanel;

public class View extends JFrame {
	public static int viewHeight = 650;
	public static int viewWidth = 1200;

	public ArrayList<button> gameObjBtns = new ArrayList<button>();
	Point playerPos = (new Point(0, 0));
	Point playerDest = (new Point(200, 200));

	String playerDir = "";

	JPanel jP = new jpanel();
	private HealthPanel healthBar;
	Dimension frameDimensions = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());

	private Integer shoreLineTop;

	private BufferedImage[] scenery = new BufferedImage[2];

	private int totalShoreDecrement = 0;

	public SaveButton sb;
	public LoadButton lb;
	
	button k = new button();

	Timer screenTimer = new Timer(1, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
			viewHeight = getContentPane().getHeight();
			viewWidth = getContentPane().getWidth();
			updateViewObjs();
			// m.gameDi = v.getContentPane().getSize();

		}
	});

	/*
	 * View Constructor
	 */
	public View() {
		initView();
	}

	public View(String s) {
		if (s == "t") {
			initView();
		}
	}

	public void playTutorialSequence(int n) {
		// screenTimer.start();

		switch(n){
		case 1:
			JOptionPane.showMessageDialog(null, "Welcome to Estuary Quest!");
			JOptionPane.showMessageDialog(null,
					"Oh no, the estuary is being eroded away by BIG waves! Let's protect it!");
			JOptionPane.showMessageDialog(null, "Click on an oyster to pick it up!");
			initPickupTutorial();	
		break;
		case 2:
			JOptionPane.showMessageDialog(null, "Great Job! Now place it in a box to start building a Gabion...");
		break;
		}
		
		
		
		
		
	}
	public void initPickupTutorial(){
		for (int i=0; i< Model.getGameObjs().size(); i++) {

			if (Model.getGameObjs().get(i) instanceof BeachObject && Model.getGameObjs().get(i).getMyType() == HoldingType.OYSTER) {

				k.setMargin(new Insets(0, 0, 0, 0));
				k.setContentAreaFilled(false);
				k.setSize(new Dimension(BeachObject.beachObjDimensions, BeachObject.beachObjDimensions));
				k.setType(Model.getGameObjs().get(i).getMyType());
//				k.setType( HoldingType.OYSTER );
				k.setLocation(new Point(200, 200));
				k.setBounds(k.getLocation().x, k.getLocation().y, BeachObject.beachObjDimensions,
						BeachObject.beachObjDimensions);
				k.setIcon((Icon) ((BeachObject) (Model.getGameObjs().get(i))).getObjIcon());

				Border oystBorder = new LineBorder(Color.green, 4);
				k.setBorder(oystBorder);
				jP.add(k);
				break;
			}
		}	
	}

	/*
	 * General functions
	 */

	/**
	 * @author Eaviles Purpose: look at each GameObject in Model's array and
	 *         update corresponding buttons/images/panels
	 */
	public void updateViewObjs() {
		// for each item in the Model array, look at the type,
		// look at the item's current position in Model, and update the View
		// button's position to match

		for (int i = 0; i < Model.getGameObjs().size(); i++) {

			// SHORELINE
			if (Model.getGameObjs().get(i) instanceof Shoreline) {
				shoreLineTop = Model.getGameObjs().get(i).getShoreTop().x;
				this.totalShoreDecrement = Model.getGameObjs().get(i).getTotalDecrement();
			}
			// HEATLH BAR
			else if (Model.getGameObjs().get(i) instanceof HealthBar) {
				healthBar.setHealthHeight(Model.getGameObjs().get(i).getInsideHeight());
				healthBar.startingY = Model.getGameObjs().get(i).getStartingY();
			}
			// PLAYER
			else if (Model.getGameObjs().get(i) instanceof Player) {
				gameObjBtns.get(i).setLocation(Model.getGameObjs().get(i).getPosition());
				gameObjBtns.get(i).setIcon(Model.getGameObjs().get(i).getObjIcon());
			}
			// WAVE
			else if (Model.getGameObjs().get(i) instanceof Wave) {
				gameObjBtns.get(i).setLocation(Model.getGameObjs().get(i).getPosition());
			}
			// BEACHOBJ
			else if (Model.getGameObjs().get(i) instanceof BeachObject) {

				if (Model.getGameObjs().get(i).getObjIcon() == null) {
					gameObjBtns.get(i).setVisible(false);
				}
			}
			// BOX
			else if (Model.getGameObjs().get(i) instanceof Box) {
				gameObjBtns.get(i).setLocation((Model.getGameObjs().get(i)).getPosition());
				gameObjBtns.get(i).setIcon(((Model.getGameObjs().get(i))).getObjIcon());
				gameObjBtns.get(i).setSize(Box.boxDimensions, Box.boxDimensions);
			}
		}
	}

	public void gameEnd(String m) {
		JOptionPane.showMessageDialog(null, m);
		System.exit(0);

	}

	public void updateShoreline(int damage) {
		shoreLineTop -= damage;
		// repaint();
	}

	/*
	 * Functions required for View initialization
	 */

	public void initView() {
		// BorderLayout b = new BorderLayout();

		setTitle("Estuary Quest");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		jP = new jpanel();
		// jP.setLayout(b);

		initSaveBtn();

		initLoadBtn();
		initGameObjs();// DO NOT MOVE - dependent on lines of code above&below.

		// Thanks!
		getContentPane().add(jP);
		pack();
		setVisible(true);
		screenTimer.start();
		// System.out.println("\n\nView's array of buttons
		// contains:\n"+this.gameObjBtns+"\n\n");

	}

	public void initSaveBtn() {

		sb = new SaveButton();
		sb.setBounds(1100, 0, 100, 50);
		sb.setText("Save Game");
		jP.add(sb);
	}

	public void initLoadBtn() {

		lb = new LoadButton();
		lb.setBounds(1100, 50, 100, 50);
		lb.setText("Load Game");
		jP.add(lb);
	}

	/**
	 * @author Eaviles Purpose: look at each GameObject in Model's array and
	 *         initialize corresponding buttons/images/panels
	 */
	public void initGameObjs() {

		for (int i = 0; i < Model.getGameObjs().size(); i++) {
			// SHORELINE
			if (Model.getGameObjs().get(i) instanceof Shoreline) {
				shoreLineTop = Model.getGameObjs().get(i).getShoreTop().x;
			}
			// HEATLH BAR
			if (Model.getGameObjs().get(i) instanceof HealthBar) {
				int totalHeight = Model.getGameObjs().get(i).getHeight();
				double startHeight = Model.getGameObjs().get(i).getInsideHeight();
				int width = Model.getGameObjs().get(i).getWidth();

				healthBar = new HealthPanel(totalHeight, startHeight, width);

				jP.add(healthBar);

			}
			// SCENERY
			else if (Model.getGameObjs().get(i) instanceof Scenery) {
				scenery = Model.getGameObjs().get(i).getScenery();
			} else {
				button j = new button();
				j.setMargin(new Insets(0, 0, 0, 0));
				j.setBorder(BorderFactory.createEmptyBorder());
				j.setContentAreaFilled(false);

				// PLAYER
				if (Model.getGameObjs().get(i) instanceof Player) {
					j.setSize(new Dimension(Player.playerDimensions, Player.playerDimensions));
//					j.setHoldingType(HoldingType.EMPTY);
					j.setType(null);
					j.setLocation((Point) (((Player) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, Player.playerDimensions, Player.playerDimensions);
					j.setIcon((Icon) ((Player) (Model.getGameObjs().get(i))).getObjIcon());

				}
				// WAVE
				else if (Model.getGameObjs().get(i) instanceof Wave) {
					j.setSize(new Dimension(Wave.waveWidth, Wave.waveHeight));
//					j.setHoldingType(null);
					j.setType(null);
					j.setLocation((Point) (((Wave) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, Wave.waveWidth, Wave.waveHeight);
					j.setIcon((Icon) ((Wave) (Model.getGameObjs().get(i))).getObjIcon());
				}
				// BEACHOBJ
				if (Model.getGameObjs().get(i) instanceof BeachObject) {
					j.setSize(new Dimension(BeachObject.beachObjDimensions, BeachObject.beachObjDimensions));
//					j.setHoldingType(((BeachObject) Model.getGameObjs().get(i)).getHT());
					j.setType( Model.getGameObjs().get(i).getMyType() );
					j.setLocation((Point) (((BeachObject) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, BeachObject.beachObjDimensions,
							BeachObject.beachObjDimensions);
					j.setIcon((Icon) ((BeachObject) (Model.getGameObjs().get(i))).getObjIcon());
					// System.out.println("button width "+j.getWidth()+" button
					// height = "+j.getHeight());
					// System.out.println("instanceof =
					// "+Model.getGameObjs().get(i).getClass());
				}
				// BOX
				else if (Model.getGameObjs().get(i) instanceof Box) {
					j.setSize(new Dimension(Box.boxDimensions, Box.boxDimensions));
//					j.setHoldingType((((Box) ((Model.getGameObjs()).get(i))).getContains()));
					j.setType(HoldingType.BOX);
					j.setLocation((Point) (((Box) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, Box.boxDimensions, Box.boxDimensions);
					j.setIcon((Icon) ((Box) (Model.getGameObjs().get(i))).getObjIcon());
				}

				gameObjBtns.add(j);
				jP.add(gameObjBtns.get(i));
			}
		}
	}

	/*
	 * Inner Classes
	 */
	public class jpanel extends JPanel {

		public jpanel() {
			setLayout(null);
			setPreferredSize(frameDimensions);
		}

		@Override
		protected void paintComponent(Graphics g) {
			// g.drawImage(scenery[0], 0, 0, this);
			// draws scenery image starting at 0,0, up to width/height
			// need to change x variable to equal shoreline's coordinate
			g.drawImage(scenery[0], 0, 0, viewWidth, viewHeight, this);
			g.drawImage(scenery[1], 0 + totalShoreDecrement, 0, viewWidth, viewHeight, this);
			// g.drawImage(scenery[1], 0, 0, this);
			//

			// g.setColor(Color.BLUE);
			//// System.out.println("painting. shoreline = " + shoreLine);
			// g.fillRect(shoreLine, 0, viewHeight, viewWidth);
			//
			// g.setColor(Color.yellow);
			// g.fillRect(0, 0, shoreLine, viewHeight);

			g.setColor(Color.red);
			Double a = .206;
			Double b = .45;
			Double c = .625;
			// System.out.println("viewWidth = " + viewWidth);
			// System.out.println("viewHeight = " + viewHeight);
			// System.out.println("first line coord: (" +
			// ((Double)((b)*viewWidth)).intValue() + ", " +
			// ((Double)((a)*viewHeight)).intValue() + ")" );
			// System.out.println("second line coord: (" +
			// ((Double)((c)*viewWidth)).intValue() + ", "+ (viewHeight) + ")");
			g.drawLine(((Double) ((b) * viewWidth)).intValue(), ((Double) ((a) * viewHeight)).intValue(),
					((Double) ((c) * viewWidth)).intValue(), (viewHeight));

		}
	}

	public class SaveButton extends JButton {

	}

	public class LoadButton extends JButton {

	}

	public class button extends JButton {
//		private HoldingType h = HoldingType.EMPTY;
		private HoldingType type;

//		public HoldingType getHoldingType() {
//			return this.h;
//		}
//
//		public void setHoldingType(HoldingType h) {
//			this.h = h;
//		}

		public button() {
			this.setPreferredSize(new Dimension(20, 20));
		}

		public HoldingType getType() {
			return type;
		}

		public void setType(HoldingType type) {
			this.type = type;
		}
	}

	public class HealthPanel extends JPanel {
		public int overallHeight;
		public double healthHeight;
		public int width;
		public double startingY = 0;
		private int xPos = 0;
		private int yPos = 0;

		/**
		 * Constructor TODO: update this
		 * 
		 * @param overallHeight
		 * @param healthHeight
		 */

		public HealthPanel(int totalHeight, double startHeight, int w) {
			overallHeight = totalHeight;
			healthHeight = startHeight;
			width = w;
			startingY = 0;
			setBounds(0, 0, width, overallHeight);
		}

		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(this.xPos, this.yPos, this.getWidth(), this.getHeight());
			g.setColor(Color.green);
			g.fillRect(this.xPos, (int) this.startingY, this.getWidth(), (int) healthHeight);
		}

		public void setHealthHeight(double d) {
			this.healthHeight = d;
			repaint();
		}

		public double getHealthHeight() {
			return this.healthHeight;
		}

		public void damage(int healthDamage) {
			healthHeight = healthHeight + healthDamage;
			repaint();
		}

		public int getOverallHeight() {
			return overallHeight;
		}
	}// end healthPanel class

	/*
	 * Setters & getters
	 */

	public void setPlayerPos(Point position) {
		this.playerPos = position;
	}

	public void setPlayerPos(double x, double y) {
		this.playerPos.setLocation(x, y);
	}

	public void setPlayerDest(Point destination) {
		this.playerDest = destination;
	}

	public Point getPlayerPos() {
		return playerPos;
	}

	public JPanel getJPanel() {
		return this.jP;
	}

	public HealthPanel getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(HealthPanel healthBar) {
		this.healthBar = healthBar;
	}

	public void setScenery(BufferedImage[] scenery) {
		this.scenery = scenery;
	}

	public int getShoreLineTop() {
		return shoreLineTop;
	}
	
	public button getK(){
		return k;
	}
}// end View class