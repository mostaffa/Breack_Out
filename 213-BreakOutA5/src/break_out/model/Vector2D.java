package break_out.model;

import break_out.Constants;

/**
 * A class that contains the configurations of the direction of the ball
 * 
 * @author Mostafa Othman
 * @author Azad Juma
 */
public class Vector2D {
	
	/**
     * dx The difference of x-position of the ball
     */
     private double dx;
     
    /**
     * Y The difference of y-position of the ball
     */
     private double dy;
     
     
     /**
      * Constructor of a new direction:
      * @param  dx difference type: double.
      * @param  dy difference type: double.  
      */
     public Vector2D(double dx,double dy) {
	     this.dx=dx;
	     this.dy=dy;
     }
     
     /**
      * Constructor to calculate the vector between tow defferent Positions
      * @param base First ball location as Position-object
      * @param top Second ball location as Position-object
      */
     public Vector2D(Position base,Position top) {
       	 this.dx=top.getX()-base.getX();
    	 this.dy=top.getY()-base.getY();
     }

     /**
      * Get X position difference as a double value
      * @return return the difference of x-Position as a double value
      */
     public double getDx() {
	     return this.dx;
     }
     
     /**
      * Set a new X position difference value
      * @param dx position difference type: double
      */
     public void setDx(double dx) {
	     this.dx=dx;
     }
     
     /**
      * Get Y position difference
      * @return the difference of the y-position as a double value
      */
     public double getDy() {
	     return this.dy;
     }
     
     /**
      * Set a new Y position difference value type: double
      * @param dy position difference type: double
      */
     public void setDy(double dy) {
	     this.dy=dy;
     }
     
     /**
      * Normalize the length of the direction vector
      */
     public void rescale() {
	     double c=Math.sqrt(Math.pow(dx, 2)+Math.pow(dy,2));
	     dx=(dx/c)*Constants.BALL_SPEED;
	     dy=(dy/c)*Constants.BALL_SPEED;
     }
}
