package break_out.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
//import java.util.Random;
import break_out.Constants;

//import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 * The field represents the board of the game. All components are on the board
 * 
 * @author Mostafa Othman
 * @author Azad Juma
 * 
 */
public class Field extends JPanel {

	/**
	 * Automatic generated serial version UID
	 */
	private static final long serialVersionUID = 2434478741721823327L;

	/**
	 * The connected view object
	 */
	private View view;

	/**
	 * The background color
	 */
	private Color background;

	/**
	 * The constructor needs a view
	 * 
	 * @param view The view of this board
	 */
	public Field(View view) {
		super();

		this.view = view;
		this.background = new Color(177, 92, 107);

		setFocusable(true);

		// Load settings
		initialize();
	}

	/**
	 * Initializes the settings for the board
	 */
	private void initialize() {
		// creates a layout
		setLayout(new MigLayout("", "0[grow, fill]0", "0[grow, fill]0"));
	}

	/**
	 * Change the background color
	 * @param color The new color
	 */
	public void changeBackground(Color color) {
		background = color;
		repaint();
	}
	
	/**
	 * Die Methode wird zum Zeichnen / Neuzeichnen des Spielfeldes aufgerufen, dazu werden z. B. Hintergrundfarbe, 
	 * Ballfarbe usw.angegeben und die einzelnen Methoden zum Zeichnen wie drawBall(g2) aufgerufen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		double w = Constants.SCREEN_WIDTH;
		double h = Constants.SCREEN_HEIGHT;
		// Die Abmessungen des Spielfeldes bestimmen
		setPreferredSize(new Dimension((int) w, (int) h));
		setMaximumSize(new Dimension((int) w, (int) h));
		setMinimumSize(new Dimension((int) w, (int) h));
		// Die Schaerfe der Zeichnung festlegen
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// Die Hintergrundfarbe setzen
		g2.setColor(background);
		g2.fillRect(0, 0, getWidth(), getHeight());
		// Die Ballfarbe setzen
		g2.setColor(new Color(200, 200, 200));
		// Die Methode zum Ballzeichnen aufrufen
		drawBall(g2);
		// Die Methode zum Paddlezeichnen aufrufen
		drawPaddle(g2);
		// Die Methode zum net lines aufrufen
		drawRaster(g2);
		//Die Methode zum Stones aufrufen
		drawStones(g2);
		//Die Methode zum Score aufrufen
		drawScore(g2);
		//Die Methode zum leben aufrufen
		drawLife(g2);
	}

	/**
	 * Zeichnet den Ball, greift dabei ueber das ihm bekannte view-Objekt auf das zugehoerige Game-Objekt und 
	 * darueber auf das Level-Objekt zu, um dortige Methoden zu nutzen.
	 * @param g2 as Graphics2D object.
	 */
	private void drawBall(Graphics2D g2) {
		g2.fillOval((int) view.getGame().getLevel().getBall().getPosition().getX(),
				(int) view.getGame().getLevel().getBall().getPosition().getY(),
				(int) (Constants.BALL_DIAMETER),
				(int) (Constants.BALL_DIAMETER));
	}
	
	/**
	 * Drawing a paddle as a rectangle with width and height that specified in Constants-object
	 * @param g2 as Graphics2D object
	 */
	private void drawPaddle(Graphics2D g2) {
		g2.fillRoundRect((int) view.getGame().getLevel().getPaddle().getPaddlePos().getX(),
				(int) view.getGame().getLevel().getPaddle().getPaddlePos().getY(),
				(int) (Constants.PADDLE_WIDTH),
				(int) (Constants.PADDLE_HEIGHT),10,10);
	}
	
	/**
	 * Drawing a lines on window as a net
	 * @param g2 as a Graphics2D-Object
	 */
	private void drawRaster(Graphics2D g2) {
		//Horizantal lines 
		int hLines=(int)Constants.SCREEN_HEIGHT/Constants.SQUARES_Y;
		int vLines=(int)Constants.SCREEN_WIDTH/Constants.SQUARES_X;
		for(int i=0;i<=Constants.SQUARES_Y;i++) {
				g2.drawLine(0,i*hLines,(int)Constants.SCREEN_WIDTH,i*hLines);
		}
		//Vertical Lines
		for(int i=0;i<=Constants.SQUARES_X;i++) {
				g2.drawLine(i*vLines,0,i*vLines,(int)Constants.SCREEN_HEIGHT);
		}
	}
	
	/**
	 * Drawing the Stones
	 * @param g2 as a Graphics2D-Object
	 */
	private void drawStones(Graphics2D g2) {
		//Foreach stone in the Livel 
		for(int i=0;i<view.getGame().getLevel().getStones().length;i++) {
			if(view.getGame().getLevel().getStones()[i]!=null) {
				//Get the stones's Color as an array 
				int[] color=view.getGame().getLevel().getStones()[i].getColor();
				//Set the color for the stones.
				g2.setColor(new Color(color[0],color[1],color[2]));
				g2.fillRect((int) view.getGame().getLevel().getStones()[i].getPosition().getX(),
						(int) view.getGame().getLevel().getStones()[i].getPosition().getY(),
						(int) view.getGame().getLevel().getStones()[i].getWidth(),
						(int) view.getGame().getLevel().getStones()[i].getHeight());
			}
		}
	}
	
	/**
	 * Drawing The Score
	 * @param g2 as Graphics2D object
	 */
	private void drawScore(Graphics2D g2) {
		//Set the font's color.
		g2.setColor(new Color(0,255,0));
		//Initialize the font style.
		int style = Font.BOLD | Font.TYPE1_FONT;
		//Initialize the font.
		Font f = new Font("Verdana", style, 25);
		g2.setFont(f);
		//The Total Score in All Levels
		g2.drawString("Total Score : "+view.getGame().getScore(), 9,25);
		//The Current Score In The Current Level
		g2.drawString("Level Score : "+view.getGame().getLevel().getLevelScore(), 5,50);
	}
	
	/**
	 * Drawing the life
	 * @param g2 
	 */
	private void drawLife(Graphics2D g2) {
		//Set the font's color.
		g2.setColor(new Color(0,255,0));
		//Initialize the font style.
		int style = Font.BOLD | Font.TYPE1_FONT;
		//Initialize the font.
		Font f = new Font("Verdana", style, 25);
		g2.setFont(f);
		g2.drawString("Life : "+view.getGame().getLevel().getLife(), (int)Constants.SCREEN_WIDTH-100,25);
	}
}
