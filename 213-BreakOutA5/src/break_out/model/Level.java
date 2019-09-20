package break_out.model;

import break_out.Constants;
//import break_out.controller.Controller;
import break_out.controller.JSONReader;
import java.util.Arrays;

//import com.sun.prism.paint.Color;

/**
 * This object contains information about the running game.
 * @author Mostafa Othman
 * @author Azad Juma
 */
public class Level extends Thread {

    /**
     * The game to which the level belongs 
     */
    private Game game;
    
    /**
   	 * The number of the level
   	 */
    private int levelnr;
       
    /**
	 * The score of the level
	 */
    private int score;
    
    /**
     * Declaration Ball 
     */
    private Ball ball;
    
    /**
     * Declaration paddle
     */
    private Paddle paddle;
    
    /**
     * Declaration Stones
     */
    private Stone[] stone;
    
    /**
     * Flag that shows if the ball was started
     */
    private boolean ballWasStarted = true;
        
    /**
     * If this variable is true, then the game will stop 
     */
    private boolean beendet=false;
    
    /**
     * Declaration life
     */
    private int life;
    
    
    /**
     * Construct a new Level:
     * @param game type Game-Object
     * @param levelnr Die Nummer des zu instanziierenden Levels
     * @param score Der bisher erreichte Scorewert
     */
    public Level(Game game, int levelnr, int score) {
    	this.game = game;
    	this.levelnr = levelnr;
    	//this.score = score;
        this.ball=new Ball();
        this.paddle=new Paddle();
        loadLevelData(levelnr);
    }
    
    /**
     * Get ball 
     * @return ball as a Ball-object
     */
    public Ball getBall() {
    	return ball;
    }
    
    /**
     * Get paddle
     * @return paddle as a Paddle-object
     */
    public Paddle getPaddle() {
    	return paddle;
    }
    
    /**
     * Getter for the stones
     * @return stone as Stone object
     */
    public Stone[] getStones(){
    	return stone;
    }
    
    /**
     * Setzt ballWasStarted auf true, d.h. der Ball "startet", 
     * weil so die bedingten Anweisungen in der while-Schleife der run-Methode ausgefuehrt werden.
     */
    public void startBall() {
        ballWasStarted = true;
    }

    /**
     * Setzt ballWasStarted auf false, d.h. der Ball "pausiert", weil so die bedingten Anweisungen in der while-Schleife 
     * der run-Methode nicht ausgefuehrt werden.
     */
    public void stopBall() {
        ballWasStarted = false;
    }
    
    /**
     * This function will return the value of boolean variable ballWasStarted 
     * @return ballWasStarted True, when the ball moving, else false
     */
    public boolean ballWasStarted() {
        return ballWasStarted;
    }
    
    /**
     * Setter for the beendet
     * @param beendet as boolean value
     */
    public void setBeendet(boolean beendet) {
    	this.beendet=beendet;
    }

    /**
     * This function contains the thread logic. 
     */
    public void run() {	
    	game.notifyObservers();
    	game.getLevel().stopBall();
    	// Endlosschleife fuer den Ablauf
    	while (!beendet) {
    		// the ball will still moving while the ballWasStarted is true 
	        if (ballWasStarted) {
	        	//check the ball position, whether it reached to the limits of the screen's border
	        	ball.reactOnBorder();
	        	// Set a new position of the ball (X-position and y-position)
	        	ball.updatePosition();
	        	//change the ball direction when the ball hits the paddle
	        	ball.reflectOnPaddle(paddle);
	        	//update paddle position when the player move it to right or left
	        	paddle.updatePosition();
	        	//calling back the checkLife function 
	        	checkLife();
	        	//To update the stones and the score after the ball hit one or more stones, see updateStonesAndScore() function below.
	        	updateStonesAndScore();
	        	// des Spielfeldes vorgenommen wird.
	            game.notifyObservers();    
	        }
	        // Der Thread pausiert kurz
	        try {
	            Thread.sleep(4);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
    	}   
    }
    
    /**
    * Zugriff auf die der Levelnummer zugeordnete JSON-Datei
    * @param levelnr Die Nummer X fuer die LevelX.json Datei
    */
    private void loadLevelData(int levelnr) {
    	//To load the map file, which supposed to be in "res" folder and as a json file type.
    	//the name of the map's file should be like Level1.json, Level2.json, Level3.json and so on
    	//levelnr will determine which file should be loaded.
    	JSONReader Stones=new JSONReader("res/Level"+levelnr+".json");
    	//set the life number with value which is from map file
    	this.life=Stones.getLifeCounter();
    	//Integer y variable will be used, to determine the length of the stones array.
    	//Integer z variable will be used, to referring to the stone's place in stone array.
    	int y=0;
    	int z=0;
    	for(int i=0;i<Stones.getStones2DArray().length;i++) {
    		for(int j=0;j<Stones.getStones2DArray()[0].length;j++) {
    			if(Stones.getStones2DArray()[i][j]!=0) {
    				y++;
    			}
    		}
    	}
    	//set the length for the stone array.
    	this.stone=new Stone[y];
    	for(int i=0;i<Stones.getStones2DArray().length;i++) {
    		for(int j=0;j<Stones.getStones2DArray()[i].length;j++) {
    			//normal stone, after 2 hits the stone will be destoyed
    			if(Stones.getStones2DArray()[i][j]==1) {
    				//fill the stone array with a stones, which are as Stone object, and having a specified Position.
    				this.stone[z]=new Stone(new Position(j*(Constants.SCREEN_WIDTH/Constants.SQUARES_X)+2,i*(Constants.SCREEN_HEIGHT/Constants.SQUARES_Y)+2));
    				z++;
 			    }
    			// hard stone, hard stone, after 3 hits the stone will be destoyed
    			if(Stones.getStones2DArray()[i][j]==2) {
    				//fill the stone array with a stones, which are as Stone object, and having a specified Position.
    				this.stone[z]=new Stone(new Position(j*(Constants.SCREEN_WIDTH/Constants.SQUARES_X)+2,i*(Constants.SCREEN_HEIGHT/Constants.SQUARES_Y)+2));
    				//change the stone type after the first hits
    				this.stone[z].setStoneType(2);
    				int[] color= {200,200,100};
    				//change the stone color after the first hits
    				this.stone[z].setColor(color);
    				z++;
 			    }
    		}
    	}
    }
    
    /**
     * To update the stones and the score after the ball hit one or more stones.
     */
    public void updateStonesAndScore() {
    	//The stone's color after the first shock.
    	int[] shockedColor= {0,204,0};
    	int[] dafaultColor= {0, 102, 255};
    	// this variable will call back the hitsStone function, and if this variable is true, then we can use the getShockedStoneIndex() function to return the shocked stone index.
    	boolean hitStone=ball.hitsStone(stone);
    	if(hitStone) {
    		    //to check, whether the stone shocked twice, the first shock will only change the stone's color without eliminate. 
    		    if(Arrays.equals(this.stone[ball.getShockedStoneIndex()].getColor(), shockedColor)){
    		    	//Eliminate the shocked stone.
    		    	this.stone[ball.getShockedStoneIndex()]=null;
    		    	//Increase the level score.
    		    	this.score+=5;
    		    	//Increase the total score.
    		    	this.game.setScore(this.game.getScore()+5);
    		    }else {
    		    	if(Arrays.equals(this.stone[ball.getShockedStoneIndex()].getColor(), dafaultColor)) {
    		    		//change the schocked stone color
    		    		this.stone[ball.getShockedStoneIndex()].setColor(shockedColor);
    		    	}else {
    		    		//change the schocked stone color to default color
    		    		this.stone[ball.getShockedStoneIndex()].setColor(dafaultColor);
    		    		//increase the ball speed 
    		    		this.ball.setDirection(new Vector2D(this.ball.getDirection().getDx()*1.5,this.ball.getDirection().getDy()*1.5));
    		    	}
    		    }
    		    //after the hits, check if the all the stones destroyed 
             	if(noMoreStones()) {
             		//check if the next level is more than Maxlevel
             		if(levelnr+1 <= this.game.getMaxLevel()) {
             			//stop the ball
             			stopBall();
             			//rest the level score
                 		this.score=0;
                 		//create a new level
                	   	this.game.createLevel(this.levelnr+1, this.game.getScore());
             		}else {
             			//end the game
             			setBeendet(true);
             			//redirect to start screen
            			game.getController().toStartScreen();
             		}
        		}
    	}	
    }
    
    /**
     * Getter for the level's score.
     * @return score as an integer value.
     */
    public int getLevelScore() {
    	return this.score;
    }
    
    /**
     * Getter for the life 
     * @return life as an integer value
     */
    public int getLife() {
    	return this.life;
    }
    
    /**
     * Setter for life
     * @param life as an integer value
     */
    public void setLife(int life) {
    	this.life=life;
    }
    
    /**
     * when the paddle miss the ball
     * this function will reduce the life number if the life number is more than one
     * and rest the paddle position, the ball position and stop the game.
     * And will redirect to start screen if the life number less than one
     */
    private void checkLife() {
    	//check if the paddle miss the ball
    	if(this.ball.getFailed()) {
    		//check the life number
    		if(this.life==1) {
    			//end the game
    			setBeendet(true);
    			//redirect to start screen
    			game.getController().toStartScreen();
    		}else {
    			//stop the ball movement
    			this.ballWasStarted=false;
    			//rest the ball
    			this.ball=new Ball();
    			//rest the paddle
    			this.paddle=new Paddle();
    			//reduce the life number one
    			this.life--;
    		}
    	}
    }
    
    /**
     * Check if all the stones destroyed
     * @return true if all the stones destroyed, and false if not
     */
    private boolean noMoreStones() {
    	for(int i=0;i<this.stone.length;i++) {
	    	if(this.stone[i]!=null) {
	    		return false;
		    }
	    }
    	return true;
    }
    
    /**
     * Getter for the level number
     * @return levelnr as an integer value
     */
    public int getLevelNumber() {
    	return this.levelnr;
    }
}
    


	
