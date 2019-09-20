package break_out.model;

import break_out.Constants;

/**
 * A class that contains the configurations of the Paddle object
 * 
 * @author Mostafa Othman
 * @author Azad Juma
 */
public class Paddle {
	/**
	 * Declaration the position of the paddle
	 */
	private Position paddlePos;
	
	/**
	 * Paddle movement direction (1=right, 0=stop moving and -1=left)
	 */
	private int bewegungsrichtung;
	
	
	/**
	 * Construction of the paddle
	 * The paddle will be in bottom-middle of the window as a default position
	 */
	public Paddle() {
		this.paddlePos=new Position((Constants.SCREEN_WIDTH-Constants.PADDLE_WIDTH)/2, Constants.SCREEN_HEIGHT-Constants.PADDLE_HEIGHT);
	}
	
	/**
	 * Get the current position of the paddle
	 * @return This will return the current paddle position as a Position object
	 */
	public Position getPaddlePos() {
		return this.paddlePos;
	}
	
	/**
	 * To update paddle position.
	 */
	public void updatePosition() {
		this.paddlePos.setX(this.paddlePos.getX()+getBewegungsrichtung()*Constants.DX_MOVEMENT);
		//check if the paddle still inside of the game window 
		if(this.getPaddlePos().getX()>=(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH)) 
			this.getPaddlePos().setX(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH);
		//check if the paddle still inside of the game window  
		if(this.getPaddlePos().getX()<=0) 
			this.getPaddlePos().setX(0);
	}
	
	/**
	 * To return the movement direction of the paddle.
	 * @return bewegungsrichtung the movement direction of the paddle as an Integer value.
	 */
	public int getBewegungsrichtung() {
		return this.bewegungsrichtung;
	}
	
	/**
	 * To set a new movement direction of the paddle.
	 * @param bewegungsrichtung as an Integer value.
	 */
	public void setBewegungsrichtung(int bewegungsrichtung) {
		this.bewegungsrichtung=bewegungsrichtung;
	}
}

