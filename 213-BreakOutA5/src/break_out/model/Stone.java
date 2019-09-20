package break_out.model;

import break_out.Constants;

/**
 * A class that contains the configurations of the Stone object
 * @author Mostafa Othman
 * @author Azad Juma
 *
 */
public class Stone {
	/**
	 * Declaration the Width of the Stone.
	 */
	private double stoneWidth;
	
	/**
	 * Declaration the Height of the Stone.
	 */
	private double stoneHeight;
	
	/**
	 * Declaration the Color of the Stone.
	 */
	private int[] stoneColor= {0, 102, 255};
	
	/**
	 * Declaration the Position of the Stone.
	 */
	private Position position;
	
	/**
	 * stone type
	 */
	private int stoneType=1;
	
	
	/**
	 * Constructor the Stone 
	 * @param pos Position of the Stone as a Position Object
	 */
	public Stone(Position pos) {
		this.position=pos;
		this.stoneWidth=Constants.SCREEN_WIDTH/Constants.SQUARES_X-4;
		this.stoneHeight=Constants.SCREEN_HEIGHT/Constants.SQUARES_Y-4;
	}
	
	/**
	 * Getter for the stone Position.
	 * @return position as Position object.
	 */
	public Position getPosition() {
		return this.position;
	}
	
	/**
	 * Getter for stone's width.
	 * @return stoneWidth as double value.
	 */
	public double getWidth() {
		return this.stoneWidth;
	}
	
	/**
	 * Getter for the stone's height.
	 * @return stoneHeight as double value.
	 */
	public double getHeight() {
		return this.stoneHeight;
	}
	
	/**
	 * Setter for the stone's color.
	 * @param color as an integer array.
	 */
	public void setColor(int[] color) {
		this.stoneColor=color;
	}
	
	/**
	 * Getter for the stone's color.
	 * @return stoneColor as an integer array.
	 */
	public int[] getColor() {
		return this.stoneColor;
	}
	
	/**
	 * Setter for the stone type
	 * @param stoneType as an integer value
	 */
	public void setStoneType(int stoneType) {
		this.stoneType=stoneType;
	}
	
	/**
	 * Getter for the stone type
	 * @return stoneType as an integer value
	 */
	public int getStoneType() {
		return this.stoneType;
	}
}
