package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import model.BeachObject;
import model.Box;
import model.HealthBar;
import model.HoldingType;
import model.Model;
import model.Player;
import model.Scenery;
import model.Shoreline;
import model.TutorialWave;
import model.Wave;

public class View extends JFrame {
	public static int viewHeight = 650;
	public static int viewWidth = 1200;

	public boolean resetBOs = false; 

	Point playerDest = (new Point(200, 200));

	String playerDir = "";

	private JPanel jP = new jpanel();
	private Menu menu;
	exitJframe eJf = new exitJframe();

	private HealthPanel healthBar;
	Dimension frameDimensions = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());

	private Integer shoreLineTop;

	private BufferedImage[] scenery = new BufferedImage[2];

	private int totalShoreDecrement = 0;

	public ArrayList<button> gameObjBtns = new ArrayList<button>();
	
	// save/load vars
	public SaveButton sb;
	public LoadButton lb;
	public ExitButton eb;

	//tutorial vars
	button oTBtn = new button();
	button cTBtn = new button();
	private button erosionTWave1 = new button();
	private button erosionTWave2 = new button();
	private button buildingTWave = new button();
	private boolean tWave1Added = false;
	private boolean tWave2Added = false;
	private boolean tWave3Added = false;
	
	/**
	 * @author Eaviles
	 * Purpose: regularly updates View. This timer became necessary after
	 * integrating the tutorial.
	 */

	public Timer screenTimer = new Timer(30, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			viewHeight = getContentPane().getHeight();
			viewWidth = getContentPane().getWidth();
			getJPanel().setSize(getContentPane().getSize());
			//System.out.println(resetBOs);
			updateViewObjs();
			repaint();
		}
	});

	/**
	 * @author Eaviles
	 * Purpose: reset View's button array after tutorial, so user can start with a fresh game.
	 */
	public void resetGameObjBtnsArray() {
		this.gameObjBtns.clear();
		this.jP.removeAll();
		this.initGameObjs();
		System.out.println(gameObjBtns.size() + " game obj btns in array\n\n\n");
	}

	/*
	 * View Constructor
	 */
	/**
	 * @Constructor
	 * @Purpose initialize view to the proper state
	 */
	public View() {
		menu = new Menu();
		initView();
	}

	/**
	 * @author Eaviles 
	 * @Purpose: initialize wave to be played in second animation
	 *         of the tutorial
	 */
	public void initTWave(TutorialWave t) {
		
		if(t.getAnimationNumber() == 1){
			if(!tWave1Added){
				erosionTWave1.setMargin(new Insets(0, 0, 0, 0));
				erosionTWave1.setBorder(BorderFactory.createEmptyBorder());
				erosionTWave1.setContentAreaFilled(false);

				erosionTWave1.setSize(new Dimension(Wave.waveWidth, Wave.waveHeight));
				erosionTWave1.setType(HoldingType.TUTORIAL_WAVE);
				erosionTWave1.setLocation(0, 0);
				erosionTWave1.setBounds(0, 0, Wave.waveWidth, Wave.waveHeight);
				jP.add(erosionTWave1);
				tWave1Added = true;
				System.out.println("View added 1st wave");
			} 
			else if(!tWave2Added){
				erosionTWave2.setMargin(new Insets(0, 0, 0, 0));
				erosionTWave2.setBorder(BorderFactory.createEmptyBorder());
				erosionTWave2.setContentAreaFilled(false);

				erosionTWave2.setSize(new Dimension(Wave.waveWidth, Wave.waveHeight));
				erosionTWave2.setType(HoldingType.TUTORIAL_WAVE);
				erosionTWave2.setLocation(0, 0);
				erosionTWave2.setBounds(0, 0, Wave.waveWidth, Wave.waveHeight);
				jP.add(erosionTWave2);
				tWave2Added = true;
				System.out.println("View added 2nd wave");
			}
			else{
				System.out.println("error in View.initTWave: booleans incorrect.");
			}
		}
		else if(t.getAnimationNumber() == 2){
			buildingTWave.setMargin(new Insets(0, 0, 0, 0));
			buildingTWave.setBorder(BorderFactory.createEmptyBorder());
			buildingTWave.setContentAreaFilled(false);

			buildingTWave.setSize(new Dimension(Wave.waveWidth, Wave.waveHeight));
			buildingTWave.setType(HoldingType.TUTORIAL_WAVE);
			buildingTWave.setLocation(0, 0);
			buildingTWave.setBounds(0, 0, Wave.waveWidth, Wave.waveHeight);
			jP.add(buildingTWave);
			tWave3Added = true;
		}		
		else{
			System.out.println("error in View.initTWave: TWave animation numbers incorrect");
		}
	}

	/**
	 * @author Eaviles
	 * @param n: dictates which part of the tutorial sequence to execute.
	 * Purpose: display relevant messages/animations during tutorial.
	 */
	public void playTutorialSequence(int n) {
		// screenTimer.start();

		switch (n) {
		case 1:
			JOptionPane.showMessageDialog(null, "Welcome to Estuary Quest!");
			break;
		case 2:
			JOptionPane.showMessageDialog(null,
					"Oh no, the estuary is being eroded away by BIG waves! Let's protect it!");
			JOptionPane.showMessageDialog(null, "Click on an oyster or a piece of concrete to pick it up!");
			initPickupTutorial();
			break;
		case 3:
			JOptionPane.showMessageDialog(null, "Great Job! Now place it in a box to start building protection for the Estuary...");
			break;
		case 4:
			JOptionPane.showMessageDialog(null,
					"You've started building a Gabion! Let's see what happens when a wave hits it...");
			break;
		case 5:
			JOptionPane.showMessageDialog(null,
					"You've started building a Sea Wall! Let's see what happens when a wave hits it...");
			break;
		case 6:
			JOptionPane.showMessageDialog(null,
					"The Estuary didn't erode as much! But we lost an oyster from our Gabion. Use lots of oysters to make it stronger!");
			break;
		case 7:
			JOptionPane.showMessageDialog(null,
					"The Estuary didn't erode as much! But we lost an piece of our Sea Wall. Use lots of concrete to make it stronger!");
			break;
		case 8:
			JOptionPane.showMessageDialog(null,
					"Ready to play?" + "\n~ Tip: different materials will protect the estuary better ~");
			break;
		}
	}

	/*
	 * General functions
	 */

	public ArrayList<button> getGameObjBtns() {
		return gameObjBtns;
	}

	public void setGameObjBtns(ArrayList<button> gameObjBtns) {
		this.gameObjBtns = gameObjBtns;
	}

	/**
	 * @author Eaviles 
	 * @Purpose: look at each GameObject in Model's array and
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
				gameObjBtns.get(i).setVisible(true);
				gameObjBtns.get(i).setLocation(Model.getGameObjs().get(i).getPosition());
				gameObjBtns.get(i).setIcon(Model.getGameObjs().get(i).getObjIcon());
			}
			// WAVE
			else if (Model.getGameObjs().get(i) instanceof Wave) {
	
				if (!(Model.getGameObjs().get(i) instanceof TutorialWave)) {
					gameObjBtns.get(i).setLocation(Model.getGameObjs().get(i).getPosition());
				} 
				else {
					
					if (!tWave1Added || !tWave2Added || !tWave3Added) {
						initTWave((TutorialWave)Model.getGameObjs().get(i));
					}
					
					switch(  ( (TutorialWave) Model.getGameObjs().get(i) ).getAnimationNumber()  ){
					case 1:
						erosionTWave1.setLocation(Model.getGameObjs().get(i).getPosition());
						erosionTWave1.setIcon((Icon) ((Wave) (Model.getGameObjs().get(i))).getObjIcon());
						break;
					case 2:
						erosionTWave2.setLocation(Model.getGameObjs().get(i).getPosition());
						erosionTWave2.setIcon((Icon) ((Wave) (Model.getGameObjs().get(i))).getObjIcon());
						break;
					case 3:
						buildingTWave.setLocation(Model.getGameObjs().get(i).getPosition());
						buildingTWave.setIcon((Icon) ((Wave) (Model.getGameObjs().get(i))).getObjIcon());
						break;
					}				
				}
			}
			// BEACHOBJ
			else if (Model.getGameObjs().get(i) instanceof BeachObject) {

				if (Model.getGameObjs().get(i).getObjIcon() == null) {
					gameObjBtns.get(i).setVisible(false);
				}
				
				if(this.resetBOs){
					gameObjBtns.get(i).setVisible(true);	
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

	/**
	 * @param m message to display when the game ends
	 */
	public void gameEnd(String m) {
		JOptionPane.showMessageDialog(null, m);		
	}

	public void exitWindow() {
		eJf.setBounds(500, 250, 300, 300);
		eJf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		eJf.getContentPane().setLayout(null);
		eJf.setVisible(true);
		initSaveBtn();
	}

	/**
	 * @param damage decrement shoreLineTop by this
	 * @Purpose update where View starts drawing the shoreline at
	 */
	public void updateShoreline(int damage) {
		shoreLineTop -= damage;
	}

/*
* Functions required for View initialization
*/

	/**
	 * @author Auzi/Eaviles
	 * @Purpose initialize View's variables
	 */
	public void initView() {

		setTitle("Estuary Quest");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		jP = new jpanel();
		
		initGameObjs();// DO NOT MOVE - dependent on lines of code above&below.

		getContentPane().add(jP);
		pack();
		setVisible(true);
		screenTimer.start();
	}

	/**
	 * @Purpose initialize save button
	 */
	public void initSaveBtn() {

		sb = new SaveButton();
		sb.setBounds(100, 100, 100, 50);
		sb.setText("Save Game");
		eJf.add(sb);
	}

	/**
	 * @Purpose initialize exit button
	 */
	public void initExitBtn() {

		eb = new ExitButton();
		eb.setBounds(1100, 100, 100, 50);
		eb.setText("Exit Game");
		jP.add(eb);
	}

	/**
	 * @Purpose initialize load button
	 */
	public void initLoadBtn() {

		lb = new LoadButton();
		lb.setBounds(1100, 50, 100, 50);
		lb.setText("Load Game");
		jP.add(lb);
	}

	/**
	 * @author Eaviles 
	 * @Purpose: look at each GameObject in Model's array and
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
					j.setType(null);
					j.setLocation((Point) (((Player) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, Player.playerDimensions, Player.playerDimensions);
					j.setIcon((Icon) ((Player) (Model.getGameObjs().get(i))).getObjIcon());

				}
				// WAVE TODO: init wave tuto here
				else if (Model.getGameObjs().get(i) instanceof Wave
						&& !(Model.getGameObjs().get(i) instanceof TutorialWave)) {
					j.setSize(new Dimension(Wave.waveWidth, Wave.waveHeight));
					j.setType(null);
					j.setLocation((Point) (((Wave) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, Wave.waveWidth, Wave.waveHeight);
					j.setIcon((Icon) ((Wave) (Model.getGameObjs().get(i))).getObjIcon());
				}
				// BEACHOBJ
				if (Model.getGameObjs().get(i) instanceof BeachObject) {
					j.setSize(new Dimension(BeachObject.beachObjDimensions, BeachObject.beachObjDimensions));
					j.setType(Model.getGameObjs().get(i).getMyType());
					j.setLocation((Point) (((BeachObject) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, BeachObject.beachObjDimensions,
							BeachObject.beachObjDimensions);
					j.setIcon((Icon) ((BeachObject) (Model.getGameObjs().get(i))).getObjIcon());
				}
				// BOX
				else if (Model.getGameObjs().get(i) instanceof Box) {
					j.setSize(new Dimension(Box.boxDimensions, Box.boxDimensions));
					j.setType(HoldingType.BOX);
					j.setLocation((Point) (((Box) (Model.getGameObjs().get(i))).getPosition()));
					j.setBounds(j.getLocation().x, j.getLocation().y, Box.boxDimensions, Box.boxDimensions);
					j.setIcon((Icon) ((Box) (Model.getGameObjs().get(i))).getObjIcon());
				}

				gameObjBtns.add(j);

				if (!(Model.getGameObjs().get(i) instanceof TutorialWave)) {
					jP.add(gameObjBtns.get(i));
				}
			}
		}
	}

	/**
	 * @author Eaviles
	 * @Purpose create & add to screen the two beachobjects used in the tutorial
	 */
	public void initPickupTutorial() {
		boolean haveAnOyst = false;
		boolean haveAConc = false;
		for (int i = 0; i < Model.getGameObjs().size(); i++) {

			if (Model.getGameObjs().get(i) instanceof BeachObject
					&& Model.getGameObjs().get(i).getMyType() == HoldingType.OYSTER && !haveAnOyst) {

				oTBtn.setMargin(new Insets(0, 0, 0, 0));
				oTBtn.setContentAreaFilled(false);
				oTBtn.setSize(new Dimension(BeachObject.beachObjDimensions, BeachObject.beachObjDimensions));
				oTBtn.setType(HoldingType.TUTORIAL_O);
				oTBtn.setLocation(new Point(200, 200));
				oTBtn.setBounds(oTBtn.getLocation().x, oTBtn.getLocation().y, BeachObject.beachObjDimensions,
						BeachObject.beachObjDimensions);
				oTBtn.setIcon((Icon) ((BeachObject) (Model.getGameObjs().get(i))).getObjIcon());

				Border oystBorder = new LineBorder(Color.green, 4);
				oTBtn.setBorder(oystBorder);
				jP.add(oTBtn);
				haveAnOyst = true;
			}

			if (Model.getGameObjs().get(i) instanceof BeachObject
					&& Model.getGameObjs().get(i).getMyType() == HoldingType.CONCRETE && !haveAConc) {
				cTBtn.setMargin(new Insets(0, 0, 0, 0));
				cTBtn.setContentAreaFilled(false);
				cTBtn.setSize(new Dimension(BeachObject.beachObjDimensions, BeachObject.beachObjDimensions));
				cTBtn.setType(HoldingType.TUTORIAL_C);
				cTBtn.setLocation(new Point(200, 280));
				cTBtn.setBounds(cTBtn.getLocation().x, cTBtn.getLocation().y, BeachObject.beachObjDimensions,
						BeachObject.beachObjDimensions);
				cTBtn.setIcon((Icon) ((BeachObject) (Model.getGameObjs().get(i))).getObjIcon());

				Border oystBorder = new LineBorder(Color.green, 4);
				cTBtn.setBorder(oystBorder);
				jP.add(cTBtn);
				haveAConc = true;
			}
		}
	}

/*
 * Inner Classes
 */
	public class exitJframe extends JFrame {
		// comment // test test

	}

	/**
	 * @author Auzi
	 *
	 */
	public class jpanel extends JPanel {

		public jpanel() {
			setLayout(null);
			setLayout(null);
			setPreferredSize(frameDimensions);
		}

		@Override
		protected void paintComponent(Graphics g) {

			g.drawImage(scenery[0], 0, 0, viewWidth, viewHeight, this);
			g.drawImage(scenery[1], 0 + totalShoreDecrement, 0, viewWidth, viewHeight, this);

			g.setColor(Color.red);
			Double a = .206;
			Double b = .45;
			Double c = .625;
		}
	}

	public class SaveButton extends JButton {

	}

	public class LoadButton extends JButton {

	}

	public class ExitButton extends JButton {

	}

	/**
	 * @Class custom jbutton class
	 * @author Auzi
	 *
	 */
	public class button extends JButton {

		private HoldingType type;

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

	/**
	 * @Class HealthPanel handles view's representation of health bar by creating a custom jpanel for it
	 * @author Auzi
	 *
	 */
	public class HealthPanel extends JPanel {
		public int overallHeight;
		public double healthHeight;
		public int width;
		public double startingY = 0;
		private int xPos = 0;
		private int yPos = 0;

		/**
		 * @Constructor
		 * @author Auzi
		 * @param overallHeight set to this
		 * @param healthHeight set to this
		 */

		public HealthPanel(int totalHeight, double startHeight, int w) {
			overallHeight = totalHeight;
			healthHeight = startHeight;
			width = w;
			startingY = 0;
			setBounds(0, 0, width, overallHeight);
		}

		/**
		 * overrided paintComponent method for graphics g
		 */
		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(this.xPos, this.yPos, this.getWidth(), this.getHeight());
			g.setColor(Color.green);
			if (getHealthHeight() <= 100) {
				g.setColor(Color.yellow);
			}
			if (getHealthHeight() < 50) {
				g.setColor(Color.RED);
			}
			g.fillRect(this.xPos, (int) this.startingY, this.getWidth(), (int) healthHeight);

		}

		/**
		 * @param d set healthHeight to this
		 */
		public void setHealthHeight(double d) {
			this.healthHeight = d;
			repaint();
		}

		/**
		 * @return healthHeight
		 */
		public double getHealthHeight() {
			return this.healthHeight;
		}

		/**
		 * @param healthDamage update heatlhheight by this
		 */
		public void damage(int healthDamage) {
			healthHeight = healthHeight + healthDamage;
			repaint();
		}

		/**
		 * @return overallheight
		 */
		public int getOverallHeight() {
			return overallHeight;
		}
	}// end healthPanel class

/*
* Setters & getters
*/

	/**
	 * @return jPanel jP
	 */
	public JPanel getJPanel() {
		return this.jP;
	}

	/**
	 * @return menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu set the menu to this
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/**
	 * @return healthbar
	 */
	public HealthPanel getHealthBar() {
		return healthBar;
	}

	/**
	 * @param healthBar set healthBar to this
	 */
	public void setHealthBar(HealthPanel healthBar) {
		this.healthBar = healthBar;
	}

	/**
	 * @param scenery set scenery to this
	 */
	public void setScenery(BufferedImage[] scenery) {
		this.scenery = scenery;
	}

	/**
	 * @return shoreLineTop
	 */
	public int getShoreLineTop() {
		return shoreLineTop;
	}

	/**
	 * @return oTBtn (button representing oyster of tutorial)
	 */
	public button getOTBtn() {
		return oTBtn;
	}

	/**
	 * @return cTBtn (button representing concrete of tutorial)
	 */
	public button getCTBtn() {
		return cTBtn;
	}
	
	/**
	 * @param resetBOs set resetBOs to this (resetBOs = boolean flagged to respawn beachobjects when user has picked up a lot of them)
	 */
	public void setResetBOs(boolean resetBOs) {
		this.resetBOs = resetBOs;
	}

	/**
	 * @return buildingWave (wave of 2nd tutorial animation)
	 */
	public button getBuildingTWave() {
		return buildingTWave;
	}
	/**
	 * @return erosionTWave1, 1st wave of 1st animation of tutorial
	 */
	public button getErosionTWave1() {
		return erosionTWave1;
	}

	/**
	 * @return erosionTWave2, 2nd wave of 1st animation of tutorial
	 */
	public button getErosionTWave2() {
		return erosionTWave2;
	}


}// end View class