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
import javax.swing.plaf.synth.SynthSeparatorUI;

import controller.status;
import model.*;
import model.Character;
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
	Dimension frameDimensions = new Dimension(viewWidth, viewHeight);

	private Integer shoreLineTop;

	private BufferedImage[] scenery = new BufferedImage[2];

	/*
	 * View Constructor
	 */
	public View() {
		initView();
	}

	/*
	 * General functions
	 */
	public void updateViewObjs() {
		// for each item in the Model array, look at the type,
		// look at the item's current position in Model, and update the View
		// button's position to match

		for (int i = 0; i < Model.getGameObjs().size(); i++) {

			// SHORELINE
			if (Model.getGameObjs().get(i) instanceof Integer) {
				shoreLineTop = (Integer) Model.getGameObjs().get(i);
//				System.out.println("model's shoreline in objarray = "+  (Integer) Model.getGameObjs().get(i));
//				System.out.println("shorelinetop updated. slt = " + shoreLineTop);
			}
			// HEATLH BAR
			else if (Model.getGameObjs().get(i) instanceof HealthBar) {
				healthBar.setHealthHeight(( (HealthBar)( Model.getGameObjs().get(i) ) ).getInsideHeight());
				healthBar.startingY = ( (HealthBar)( Model.getGameObjs().get(i) ) ).getStartingY();
//				System.out.println("health in model's array = "+ ( (HealthBar)( Model.getGameObjs().get(i) ) ).getInsideHeight() );
//				System.out.println("health in view's data = "+ healthBar.getHealthHeight() );
			}
			// PLAYER
			else if (Model.getGameObjs().get(i) instanceof Player) {
				gameObjBtns.get(i).setLocation((Point) (((Player) (Model.getGameObjs().get(i))).getPosition()));
				gameObjBtns.get(i).setIcon((Icon) ((Player) (Model.getGameObjs().get(i))).getObjIcon());
			}
			// WAVE
			else if (Model.getGameObjs().get(i) instanceof Wave) {
				gameObjBtns.get(i).setLocation((Point) (((Wave) (Model.getGameObjs().get(i))).getPosition()));
			}
			// BEACHOBJ
			else if (Model.getGameObjs().get(i) instanceof BeachObject) {

				if (((BeachObject) (Model.getGameObjs().get(i))).getObjIcon() == null) {
					gameObjBtns.get(i).setVisible(false);
				}
			}
			// BOX
			else if (Model.getGameObjs().get(i) instanceof Box) {
				gameObjBtns.get(i).setLocation((Point) (((Box) (Model.getGameObjs().get(i))).getPosition()));
//				System.out.println("updating a box. count = " +  ((Box) (Model.getGameObjs().get(i))).getCount()  );

				gameObjBtns.get(i).setSize(Box.boxDimensions,Box.boxDimensions);

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
		//BorderLayout b = new BorderLayout();
		
		setTitle("Estuary Quest");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		jP = new jpanel();
		//jP.setLayout(b);
		
		

		initGameObjs();// DO NOT MOVE - dependent on lines of code above&below.
						// Thanks!
		getContentPane().add(jP);
		pack();
		setVisible(true);

	}

	public void initGameObjs() {

		for (int i = 0; i < Model.getGameObjs().size(); i++) {
			// SHORELINE
			if (Model.getGameObjs().get(i) instanceof Integer) {
				shoreLineTop = (Integer) Model.getGameObjs().get(i);
//				System.out.println("init shorelinetop = " + shoreLineTop);
			}
			// HEATLH BAR
			else if (Model.getGameObjs().get(i) instanceof HealthBar) {
				int totalHeight = ((HealthBar) (Model.getGameObjs().get(i))).getHeight();
				double startHeight = ((HealthBar) (Model.getGameObjs().get(i))).getInsideHeight();
				int width = ((HealthBar) (Model.getGameObjs().get(i))).getWidth();

				healthBar = new HealthPanel(totalHeight, startHeight, width);

				jP.add(healthBar);

			}
			// SCENERY
			else if (Model.getGameObjs().get(i) instanceof BufferedImage[]) {
				scenery = ((BufferedImage[]) (Model.getGameObjs().get(i)));
			} else {
				button j = new button();
				j.setMargin(new Insets(0, 0, 0, 0));
				j.setBorder(BorderFactory.createEmptyBorder());
				j.setContentAreaFilled(false);

				// PLAYER
				if (Model.getGameObjs().get(i) instanceof Player) {
					j.setSize(new Dimension(Player.playerDimensions, Player.playerDimensions));
					j.setHoldingType(HoldingType.EMPTY);
					j.setLocation((Point) (((Player) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, Player.playerDimensions, Player.playerDimensions);
					j.setIcon((Icon) ((Player) (Model.getGameObjs().get(i))).getObjIcon());

				}
				// WAVE
				else if (Model.getGameObjs().get(i) instanceof Wave) {
					j.setSize(new Dimension(Wave.waveWidth, Wave.waveHeight));
					j.setHoldingType(null);
					j.setLocation((Point) (((Wave) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, Wave.waveWidth, Wave.waveHeight);
					j.setIcon((Icon) ((Wave) (Model.getGameObjs().get(i))).getObjIcon());
				}
				// BEACHOBJ
				else if (Model.getGameObjs().get(i) instanceof BeachObject) {
					j.setSize(new Dimension(BeachObject.beachObjDimensions, BeachObject.beachObjDimensions));
					j.setHoldingType(((BeachObject) Model.getGameObjs().get(i)).getH());
					j.setLocation((Point) (((BeachObject) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, BeachObject.beachObjDimensions,
							BeachObject.beachObjDimensions);
					j.setIcon((Icon) ((BeachObject) (Model.getGameObjs().get(i))).getObjIcon());
				}
				// BOX
				else if (Model.getGameObjs().get(i) instanceof Box) {
					j.setSize(new Dimension(Box.boxDimensions, Box.boxDimensions));
					j.setHoldingType((((Box) ((Model.getGameObjs()).get(i))).getContains()));
					
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
//			g.drawImage(scenery[0], 0, 0, this);
			g.drawImage(scenery[0], 0, 0,viewWidth, viewHeight,this);
			g.drawImage(scenery[1], 0, 0, viewWidth, viewHeight, this);
//			g.drawImage(scenery[1], 0, 0, this);
//		

			// g.setColor(Color.BLUE);
			//// System.out.println("painting. shoreline = " + shoreLine);
			// g.fillRect(shoreLine, 0, viewHeight, viewWidth);
			//
			// g.setColor(Color.yellow);
			// g.fillRect(0, 0, shoreLine, viewHeight);
			g.setColor(Color.red);
			g.drawLine(shoreLineTop, 0, shoreLineTop, viewHeight);
		}
	}

	public class button extends JButton {
		private HoldingType h = HoldingType.EMPTY;

		public HoldingType getHoldingType() {
			return this.h;
		}

		public void setHoldingType(HoldingType h) {
			this.h = h;
		}

		public button() {
			this.setPreferredSize(new Dimension(20, 20));
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
		public double getHealthHeight(){
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
}// end View class