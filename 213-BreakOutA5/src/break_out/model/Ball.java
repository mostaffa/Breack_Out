
package break_out.model;

import break_out.Constants;
import java.awt.Rectangle;
//import javax.swing.JOptionPane;
/**
 * A class that contains the configurations of the ball position and ball movement direction
 * @author Mostafa Othman
 * @author Azad Juma
 */
public class Ball {
	
	/**
	 * Declaration a current ball position
	 */
	private Position pos;
	
	/**
	 * Declaration a current ball position
	 */
	private Vector2D direction ;
	
	/**
	 * Declaration the index of the shocked stone (stone id).
	 */
	private int shockedStoneIndex;
	
	/**
	 * if the paddle missed the ball
	 */
	private boolean failed;
	
	
	/**
	 * Constructor the ball position and movement direction
	 * @param pos as Position object
	 * @param direction as Vector2D object
	 */
	public Ball(Position pos,Vector2D direction) {
		this.pos=pos;
		this.direction=direction;
	}
	
	/**
	 * Constructor the ball, setting the start position in bottom-center of the window
	 * and sitting a static direction
	 */
	public Ball() {
		this.pos=new Position((Constants.SCREEN_WIDTH-Constants.BALL_DIAMETER)/2, Constants.SCREEN_HEIGHT-Constants.BALL_DIAMETER-Constants.PADDLE_HEIGHT);
		this.direction= new Vector2D(0,-5);
		direction.rescale();
	}
	
	/**
	 * Get a current position of the ball
	 * @return This will return the position as a Position object
	 */
	public Position getPosition() {
		return this.pos;
	}
	
	/**
	 * To set a new position of the ball
	 * @param pos as a Position object
	 */
	public void setPosition(Position pos) {
		this.pos=pos;
	}
	
	/**
	 * Get a current direction of the movement of the ball
	 * @return This will return the direction as a Vector2D object
	 */
	public Vector2D getDirection() {
		return this.direction;
	}
	
	/**
	 * To set a new direction of the movement of the ball
	 * @param direction as a Vector2D object
	 */
	public void setDirection(Vector2D direction) {
		this.direction=direction;
	}
	
	/**
	 * Set a new position of the ball (X-position and y-position)
	 */
	public void updatePosition() {
		this.getPosition().setX(this.getPosition().getX()+this.getDirection().getDx());
		this.getPosition().setY(this.getPosition().getY()+this.getDirection().getDy());
	}
	
	/**
	 * Getter for the index of the shocked stone (stone id).
	 * @return shockedStoneIndex as an integer value.
	 */
	public int getShockedStoneIndex() {
		return this.shockedStoneIndex;
	}
	
	/**
	 * check the ball position, whether it reached to the limits of the screen's border and flips the 
	 * the movement if so.
	 */
	public void reactOnBorder() {
		// to check ball position whether it reached to the limits of the screen's wide
		if(this.getPosition().getX()<0 )
			this.getDirection().setDx(-this.getDirection().getDx());
		if(this.getPosition().getX()>Constants.SCREEN_WIDTH-Constants.BALL_DIAMETER) {
			this.getDirection().setDx(-this.getDirection().getDx());
		}
    	// to check the position of the ball whether it reached to the limits of the screen's height
    	if(this.getPosition().getY()<0 )
    		this.getDirection().setDy(-this.getDirection().getDy());
    	if(this.getPosition().getY()>Constants.SCREEN_HEIGHT-Constants.BALL_DIAMETER) {
    		//then the paddle missed the ball
    		this.failed=true;
    		//this.getDirection().setDy(-this.getDirection().getDy());
    	}
	}
	
	/**
	 * To check whithen the ball hits the baddle
	 * @param p the paddle as a Paddle-Object
	 * @return this will be true if the ball hits the paddle and false if not.
	 */
	public boolean  hitsPaddle(Paddle p) {
		if(this.getPosition().getX()+Constants.BALL_DIAMETER>= p.getPaddlePos().getX() && this.getPosition().getX()<= p.getPaddlePos().getX()+Constants.PADDLE_WIDTH && this.getPosition().getY()+Constants.BALL_DIAMETER>=p.getPaddlePos().getY() ) 
				return true;
		return false;
	}
	
	/**
	 * To change the ball direction whin the ball hits the paddle
	 * @param paddle as a Paddle-Object
	 */
	public void reflectOnPaddle(Paddle paddle) {
		if(hitsPaddle(paddle)) {
			this.direction=new Vector2D(new Position(paddle.getPaddlePos().getX()+Constants.PADDLE_WIDTH/2-Constants.BALL_DIAMETER/2,Constants.SCREEN_HEIGHT+10),this.getPosition());
			this.direction.rescale();
		}
	}
	
	/**
	 * boolean function to check whether the ball hits the stone or not, and if so, the shockedStones array will be filled with the shocked stones.
	 * @param stone as a Stones array.
	 * @return true if the ball at least shock one stone, and false if not.
	 */
	public boolean hitsStone(Stone[] stone) {
		boolean result=false;
		//define a rectangle, which contains the ball, using Rectangle class.
		Rectangle ballAsRectangle=new Rectangle((int)this.getPosition().getX(),(int)this.getPosition().getY(),(int)Constants.BALL_DIAMETER,(int)Constants.BALL_DIAMETER);
		for(int i=0;i<stone.length;i++) {
			if(stone[i]!=null) {
				//make the stone as a rectangle object, using Rectangle class.
			    Rectangle stoneAsRectangle=new Rectangle((int)stone[i].getPosition().getX(),(int)stone[i].getPosition().getY(),(int)stone[i].getWidth(),(int)stone[i].getHeight());
			    //check, whether the stone as a rectangle, intersect the ball as a rectangle.
			    if(stoneAsRectangle.intersects(ballAsRectangle)) {
			    	//set the shocked stone index.
			    	this.shockedStoneIndex=i;
			    	//Logic for reflexing the ball after shocking the stone.
			    	//we used here the center of the rectangle which contains the ball, this is the same center of the ball.
			    	if(ballAsRectangle.getCenterX()>=stoneAsRectangle.getX() && ballAsRectangle.getCenterX()<=stoneAsRectangle.getX()+stoneAsRectangle.getWidth())
			    		this.getDirection().setDy(-this.getDirection().getDy());
			    	else if(ballAsRectangle.getCenterY()>=stoneAsRectangle.getY() && ballAsRectangle.getCenterY()<=stoneAsRectangle.getY()+stoneAsRectangle.getHeight())
			    		this.getDirection().setDx(-this.getDirection().getDx());
			    	else {
			    		//When the ball hits the angle of the stone.
			    		this.getDirection().setDx(-this.getDirection().getDx());
			    		this.getDirection().setDy(-this.getDirection().getDy());
			    	}
			      //JOptionPane.showMessageDialog(null, "Yes", "Hits Stone : " , JOptionPane.INFORMATION_MESSAGE);
			        result= true;
			        break;
			     }
		     }
	     }
		return result;
      }
	
	/**
	 * Getter for the failed variable
	 * @return failed as boolean value
	 */
	public boolean getFailed() {
		return this.failed;
	}
}
